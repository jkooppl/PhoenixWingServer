package com.pizza73.webapp.action.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.pizza73.model.BatchRecord;
import com.pizza73.model.DailySales;
import com.pizza73.model.Employee;
import com.pizza73.model.Order;
import com.pizza73.model.Shop;
import com.pizza73.model.comparator.BatchRecordComparator;
import com.pizza73.service.LookupManager;
import com.pizza73.webapp.action.admin.attribute.CommonAttributesInDailySalesReporting;
import com.pizza73.webapp.util.DateParser;
import com.pizza73.webapp.validator.DailySalesValidator;

@Controller
@RequestMapping("/dailySalesForm.html")
@SessionAttributes("dailySales")
public class DailySalesFormController extends CommonAttributesInDailySalesReporting {
    private static final Logger log = Logger.getLogger(DailySales.class);
    
    private class BatchRecordNewComparator implements Serializable,Comparator<BatchRecord>
    {

    	public int compare(BatchRecord br1, BatchRecord br2)
    	{
    		if(br2.isWireless() == true && br1.isWireless() == false)
    			return -1;
    		else if (br2.isWireless() == false && br1.isWireless() == true)
    			return 1;
    		else
    			return br1.getId().compareTo(br2.getId());
    	}
    }

    @Autowired
    private LookupManager lookupManager;
    private static final SimpleDateFormat infoMessageDateFormat = new SimpleDateFormat(
            "MMM/dd/yyyy");

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request)
            throws ParseException
    {
        HttpSession session = request.getSession();
        Shop shop = (Shop) session.getAttribute("DAILYSALES_SHOP");
        Calendar requestDate = DateParser.parseTrimedDate(request.getParameter("date"));
        DailySales dailySales = this.lookupManager.dailySalesForShopAndDate(shop.getId(),
                requestDate);
        if (dailySales == null)
        {
            dailySales = new DailySales();
            dailySales.setShopId(shop.getId());
            dailySales.setSalesDate(requestDate);           	
       }
        Calendar Version3StartDate=Calendar.getInstance();
        Version3StartDate.set(2013, Calendar.FEBRUARY, 23, 0, 0, 0);
        if (dailySales.getSalesDate().after(Version3StartDate)){
        	dailySales.setVersion(3);
        }
        List<BatchRecord> BatchRecords = (List<BatchRecord>) dailySales.getBatchRecords();
        int i = BatchRecords.size();
        if (i > 0)
            Collections.sort(BatchRecords, new BatchRecordNewComparator());
        if (dailySales.isSubmitted() == false)
        {
            for (i = 0; i < shop.getQuantityOfFrontCounterMachines(); i++)
            {
                if ((i + 1) > BatchRecords.size()
                        || BatchRecords.get(i).isWireless() == true)
                    BatchRecords.add(i, new BatchRecord(null, null, null, null, null,
                            null, null, false));
            }
            for (i = shop.getQuantityOfFrontCounterMachines(); i < (shop
                    .getQuantityOfFrontCounterMachines() + shop
                    .getQuantityOfWirelessMachines()); i++)
            {
                if ((i + 1) > BatchRecords.size())
                    BatchRecords.add(i, new BatchRecord(null, null, null, null, null,
                            null, null, true));
            }
        }
        else
        {
            if (dailySales.getNumberofFrontCounterMachines() == 0)
                BatchRecords.add(0, new BatchRecord(null, null, null, null, null, null,
                        null, false));
            if (dailySales.getNumberofWirelessMachines() == 0)
                BatchRecords.add(new BatchRecord(null, null, null, null, null, null,
                        null, true));
        }
        model.addAttribute("dailySales", dailySales);
        return "dailySalesForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("dailySales") DailySales dailySales,
            BindingResult result, SessionStatus status, HttpServletRequest request,
            ModelMap model)
    {
        log.error("dailySales session status " + status.isComplete());
        // create info messages list
        List<String> infoMessages = new ArrayList<String>();
        // cancel action
        if (StringUtils.isNotBlank(request.getParameter("CANCEL_ACTION")))
        {
            status.setComplete();
            return "redirect:dailySales.html";
        }
        // validation
        new DailySalesValidator().validate(dailySales, result);
        if (result.hasErrors())
            return "dailySalesForm";
        dailySales.trimNullBatchRecords();
        // save or submit to office action
        if ("Send To Office".equals((String) request.getParameter("action")))
        {
            processSendToOffice(dailySales, infoMessages);
        }
        else
        {
            processSave(dailySales, infoMessages);
        }
        request.getSession().setAttribute("INFO_MESSAGES", infoMessages);
        status.setComplete();
        return "redirect:dailySales.html";
    }

    private void processSendToOffice(DailySales dailySales, List<String> infoMessages)
    {
        changeModifier(dailySales);
        dailySales.setSubmitted(true);
        lookupManager.save(dailySales);
        infoMessages.add("Dailysales report ("
                + DailySalesFormController.infoMessageDateFormat.format(dailySales
                        .getSalesDate().getTime())
                + ") has been submitted to head office.");
    }

    private void processSave(DailySales dailySales, List<String> infoMessages)
    {
        changeModifier(dailySales);
        lookupManager.save(dailySales);
        infoMessages.add("Dailysales report ("
                + DailySalesFormController.infoMessageDateFormat.format(dailySales
                        .getSalesDate().getTime()) + ") has been saved.");
    }

    private void changeModifier(DailySales dailySales)
    {
        SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = (Employee) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        dailySales.setEmployeeId(employee.getId());
    }

}
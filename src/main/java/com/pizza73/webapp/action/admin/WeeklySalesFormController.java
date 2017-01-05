package com.pizza73.webapp.action.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pizza73.model.DailySales;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.model.WeeklySales;
import com.pizza73.service.LookupManager;
import com.pizza73.webapp.action.admin.attribute.CommonAttributesInWeeklySalesReporting;
import com.pizza73.webapp.util.DateParser;

@Controller
@RequestMapping("/weeklySalesForm")
@SessionAttributes("weeklySales")
public class WeeklySalesFormController extends CommonAttributesInWeeklySalesReporting
{
    @Autowired
    private LookupManager lookupManager;
    private static final SimpleDateFormat infoMessageDateFormat = new SimpleDateFormat("MMM/dd/yyyy");

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("weeklySales") WeeklySales weeklySales, HttpServletRequest request,
        ModelMap model)
    {
        List<String> infoMessages = new ArrayList<String>();
        if (StringUtils.isNotBlank(request.getParameter("CANCEL_ACTION")))
        {
            ;// do nothing
        }
        else
        {
            if ("Send To Office".equals((String) request.getParameter("action")))
            {
                if (weeklySales.isSubmitted() == false)
                {
                    processSendToOffice(weeklySales, infoMessages);
                }
            }
            else
            {
                if (weeklySales.isSubmitted() == false)
                {
                    processSave(weeklySales, infoMessages);
                }
            }
        }
        
        request.getSession().setAttribute("INFO_MESSAGES", infoMessages);
        return "redirect:dailySales.html";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupFrom(HttpServletRequest request, ModelMap model) throws Exception
    {
        HttpSession session = request.getSession();
        Shop shop = (Shop) session.getAttribute("DAILYSALES_SHOP");
        Calendar sundayOfWeek = DateParser.parseTrimedDate(request.getParameter("date"));
        boolean isEndingWeek = false;
        
        if (sundayOfWeek.get(Calendar.YEAR) == 2012)
        {
            if ((sundayOfWeek.get(Calendar.MONTH) == Calendar.JANUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.FEBRUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 19)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MARCH && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 25)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.APRIL && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MAY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 20)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JUNE && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 24)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JULY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.AUGUST && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 19)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.SEPTEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 23)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.OCTOBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 21)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.NOVEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 18)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.DECEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 23))
            {
                isEndingWeek = true;
                model.addAttribute("IS_ENDING_WEEK", isEndingWeek);
            }
        }
        
        if (sundayOfWeek.get(Calendar.YEAR) == 2013)
        {
            if ((sundayOfWeek.get(Calendar.MONTH) == Calendar.JANUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 20)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.FEBRUARY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 17)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MARCH && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 24)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.APRIL && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 21)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.MAY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 19)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JUNE && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 23)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.JULY && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 21)
                ||(sundayOfWeek.get(Calendar.MONTH) == Calendar.AUGUST && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 18)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.SEPTEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.OCTOBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 20)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.NOVEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 17)
                || (sundayOfWeek.get(Calendar.MONTH) == Calendar.DECEMBER && sundayOfWeek.get(Calendar.DAY_OF_MONTH) == 22))
            {
            isEndingWeek = true;
            model.addAttribute("IS_ENDING_WEEK", isEndingWeek);
           }
        }
        
        WeeklySales weeklySales = this.lookupManager.weeklySalesForShopAndWeek(shop.getId(), sundayOfWeek);
        if (weeklySales == null)
        {
            weeklySales = new WeeklySales();
            weeklySales.setShopId(shop.getId());
            weeklySales.setSundayOfSalesWeek(sundayOfWeek);
        }
        
        Calendar Version3StartDate=Calendar.getInstance();
        Version3StartDate.set(2013, Calendar.FEBRUARY, 23, 0, 0, 0);
        if (weeklySales.getSundayOfSalesWeek().after(Version3StartDate)){
        	model.addAttribute("version3", true);
        }
        else 
        	model.addAttribute("version3", false);
        model.addAttribute("weeklySales", weeklySales);
        this.populateData(request, model, weeklySales);
        
        return "weeklySalesForm";
    }

    private void populateData(HttpServletRequest request, ModelMap model, WeeklySales weeklySales)
        throws ParseException
    {
        HttpSession session = request.getSession();
        Shop shop = (Shop) session.getAttribute("DAILYSALES_SHOP");
        Calendar requestDate = DateParser.parseTrimedDate(request.getParameter("date"));
        boolean isForbidden = false;
        
        Calendar sundayOfLastWeek = (Calendar) requestDate.clone();
        sundayOfLastWeek.add(Calendar.DATE, -7);
        
        WeeklySales lastWeeklySales = this.lookupManager.weeklySalesForShopAndWeek(shop.getId(), sundayOfLastWeek);
        if (lastWeeklySales != null && lastWeeklySales.isSubmitted() == true)
        {
            model.addAttribute("LASTWEEKLYSALES", lastWeeklySales);
        }
        else
        {
            isForbidden = true;
        }
        
        Calendar firstSubmittedDailySalesDate = this.lookupManager.earliestDailySalesForShop(shop.getId()).getSalesDate();
        boolean isInFirstWeek = isInSameWeek(firstSubmittedDailySalesDate, requestDate);
        if (isInFirstWeek)
        {
            firstSubmittedDailySalesDate = DateUtils.truncate(firstSubmittedDailySalesDate, Calendar.DATE);
            isForbidden = false;
        }
        
        ArrayList<DailySales> dailySalesReports = new ArrayList<DailySales>();
        ArrayList<Calendar> selectedWeek = new ArrayList<Calendar>();
        DailySales dailySales = null;
        for (int i = 0; i < 7; i++)
        {
            if (i != 0)
                requestDate.add(Calendar.DATE, 1);
            selectedWeek.add((Calendar) requestDate.clone());
            dailySales = this.lookupManager.dailySalesForShopAndDate(shop.getId(), requestDate);
            if (dailySales != null)
            {
                if (dailySales.isSubmitted() == false)
                {
                    if (firstSubmittedDailySalesDate != null && !firstSubmittedDailySalesDate.after(requestDate))
                    {
                        isForbidden = true;
                    }
                }
                dailySalesReports.add(dailySales);
            }
            else
            {
                if (firstSubmittedDailySalesDate != null && !firstSubmittedDailySalesDate.after(requestDate))
                {
                    isForbidden = true;
                }
                dailySalesReports.add(new DailySales());
            }
        }
        
        model.addAttribute("DAILYSALESREPORTS", dailySalesReports);
        model.addAttribute("SELECTEDWEEK", selectedWeek);
        if (!((Boolean) request.getSession().getAttribute("HAS_CHANGE_SHOP_PREVILIAGE")).booleanValue())
        {
            model.addAttribute("ISFORBIDDEN", isForbidden);
        }
        else
        {
            model.addAttribute("ISFORBIDDEN", isForbidden || !weeklySales.isSubmitted());
        }
    }

    private void changeModifier(WeeklySales weeklySales)
    {
        SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        weeklySales.setEmployeeId(employee.getId());
    }

    private void processSendToOffice(WeeklySales weeklySales, List<String> infoMessages)
    {
        changeModifier(weeklySales);
        weeklySales.setSubmitted(true);
        lookupManager.save(weeklySales);
        Calendar SaturdayOfSalesWeek = (Calendar) weeklySales.getSundayOfSalesWeek().clone();
        SaturdayOfSalesWeek.add(Calendar.DATE, 6);
        infoMessages.add("Weeklysales report ("
            + WeeklySalesFormController.infoMessageDateFormat.format(weeklySales.getSundayOfSalesWeek().getTime())
            + " - " + WeeklySalesFormController.infoMessageDateFormat.format(SaturdayOfSalesWeek.getTime())
            + ") has been submitted to head office.");
    }

    private void processSave(WeeklySales weeklySales, List<String> infoMessages)
    {
        changeModifier(weeklySales);
        lookupManager.save(weeklySales);
        Calendar SaturdayOfSalesWeek = (Calendar) weeklySales.getSundayOfSalesWeek().clone();
        SaturdayOfSalesWeek.add(Calendar.DATE, 6);
        infoMessages.add("Weeklysales report ("
            + WeeklySalesFormController.infoMessageDateFormat.format(weeklySales.getSundayOfSalesWeek().getTime())
            + " - " + WeeklySalesFormController.infoMessageDateFormat.format(SaturdayOfSalesWeek.getTime())
            + ") has been saved.");

    }

    private boolean isInSameWeek(Calendar c1, Calendar c2)
    {
        if (c1 == null)
            return false;
        c1 = DateUtils.truncate(c1, Calendar.DATE);
        c1.add(Calendar.DATE, Calendar.SUNDAY - c1.get(Calendar.DAY_OF_WEEK));
        c2 = DateUtils.truncate(c2, Calendar.DATE);
        c2.add(Calendar.DATE, Calendar.SUNDAY - c1.get(Calendar.DAY_OF_WEEK));
        if (c1.compareTo(c2) == 0)
            return true;
        else
            return false;
    }

}

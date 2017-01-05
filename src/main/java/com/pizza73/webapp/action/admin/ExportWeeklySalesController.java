package com.pizza73.webapp.action.admin;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.pizza73.model.DailySales;
import com.pizza73.model.Shop;
import com.pizza73.model.WeeklySales;
import com.pizza73.service.LookupManager;
import com.pizza73.webapp.action.admin.attribute.CommonAttributesInWeeklySalesReporting;
import com.pizza73.webapp.util.DateParser;

@Controller
@RequestMapping("/weeklySalesExport.html")
public class ExportWeeklySalesController extends CommonAttributesInWeeklySalesReporting {
    @Autowired
    private LookupManager lookupManager;

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(HttpServletRequest request, ModelMap model) throws Exception
    {
        Shop shop = (Shop) request.getSession().getAttribute("DAILYSALES_SHOP");
        Calendar requestDate = DateParser.parseTrimedDate(request.getParameter("date"));
        WeeklySales weeklySales = this.lookupManager.weeklySalesForShopAndWeek(shop
                .getId(), requestDate);
        model.addAttribute("WEEKLYSALES", weeklySales);
        ArrayList<DailySales> dailySalesReports = new ArrayList<DailySales>();
        ArrayList<Calendar> selectedWeek = new ArrayList<Calendar>();
        DailySales dailySales = null;
        for (int i = 0; i < 7; i++)
        {
            if (i != 0)
                requestDate.add(Calendar.DATE, 1);
            selectedWeek.add((Calendar) requestDate.clone());
            dailySales = this.lookupManager.dailySalesForShopAndDate(shop.getId(),
                    requestDate);
            if (dailySales != null)
                dailySalesReports.add(dailySales);
            else
                dailySalesReports.add(new DailySales());
        }
        model.addAttribute("DAILYSALESREPORTS", dailySalesReports);
        model.addAttribute("SELECTEDWEEK", selectedWeek);
        return "exportWeeklySalesView";
    }
}

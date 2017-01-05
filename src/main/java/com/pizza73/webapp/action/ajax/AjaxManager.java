package com.pizza73.webapp.action.ajax;

import com.pizza73.model.DailySales;
import com.pizza73.model.Employee;
import com.pizza73.model.Shop;
import com.pizza73.service.EmployeeManager;
import com.pizza73.webapp.model.DailySalesTabsSettings;
import com.pizza73.webapp.util.ABShopPaymentCalculator;
import com.pizza73.webapp.util.BCShopPaymentCalculator;
import com.pizza73.webapp.util.DateParser;
import com.pizza73.webapp.util.SKShopPaymentCalculator;
import com.pizza73.webapp.util.ShopPaymentCalculator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AjaxManager extends com.pizza73.webapp.base.action.ajax.AjaxManager
{
    @Autowired
    private EmployeeManager employeeManager;

    private static final BigDecimal MAX_REG_HOURS = BigDecimal.valueOf(8);
    private static final BigDecimal MAX_WEEK_HOURS = BigDecimal.valueOf(44);
    private static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("EEE MMM/dd");
    private static final SimpleDateFormat urlDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat popupCalendarDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public BigDecimal[] calculateHours(String[] hoursArray, String empId)
    {
        BigDecimal[] totalHours = new BigDecimal[4];
        double[] dailyHours = new double[7];
        BigDecimal totalRegHours = BigDecimal.ZERO;
        BigDecimal tempRegHours = BigDecimal.ZERO;
        BigDecimal totalOTHours = BigDecimal.ZERO;
        BigDecimal totalStatHours = BigDecimal.ZERO;
        ShopPaymentCalculator shopPaymentCalculator = null;

        int daysWithHours = 0;

        Employee emp = (Employee) mgr.get(Employee.class, Integer.valueOf(empId));

        boolean compressedWorkWeek = emp.isCompressedWorkWeek();
        BigDecimal maxRegHours = MAX_REG_HOURS;
        if (compressedWorkWeek)
        {
            maxRegHours = BigDecimal.valueOf(emp.getCompressedHours());
        }

        Integer shopId = emp.getShopId();
        Shop shop = (Shop) mgr.get(Shop.class, shopId);

        BigDecimal maxWeekHours = AjaxManager.MAX_WEEK_HOURS;
        // change to handle 3 province
        // SK
        if (shop.getProvince().equalsIgnoreCase("SK"))
        {
            maxWeekHours = BigDecimal.valueOf(40.0);
            shopPaymentCalculator = new SKShopPaymentCalculator();
        }
        // AB
        else if (shop.getProvince().equalsIgnoreCase("AB"))
        {
            maxWeekHours = BigDecimal.valueOf(44.0);
            shopPaymentCalculator = new ABShopPaymentCalculator();
        }
        // for BC, there is no restriction in regular week hours
        else
        {
            maxWeekHours = maxRegHours.multiply(BigDecimal.valueOf(7));
            maxWeekHours = BigDecimal.valueOf(40.0);
            shopPaymentCalculator = new BCShopPaymentCalculator();
        }
        BigDecimal hours = BigDecimal.ZERO;
        try
        {

            for (int i = 0; i < hoursArray.length - 1; i++)
            {
                hours = BigDecimal.valueOf(Double.valueOf(hoursArray[i]));
                if (i < 7)
                    dailyHours[i] = hours.doubleValue();
                if (hours.compareTo(BigDecimal.ZERO) > 0)
                { // daySwithHours is initialized with value 0 in the beginning
                  // of the function
                    daysWithHours++;
                    // if it has OT hours in this day
                    if (hours.compareTo(maxRegHours) > 0)
                    {
                        totalRegHours = totalRegHours.add(maxRegHours);
                        totalOTHours = totalOTHours.add(hours.subtract(maxRegHours));
                    }
                    // if is doesn't have OT hours in this day
                    else
                    {
                        totalRegHours = totalRegHours.add(hours);
                    }
                    // tempRegHours is used to calculate weekly hours
                    tempRegHours = tempRegHours.add(hours);
                }
            }
            if (daysWithHours > 5)
            {
                if (tempRegHours.compareTo(maxWeekHours) > 0)
                {
                    BigDecimal tempOT = tempRegHours.subtract(maxWeekHours);
                    if (tempOT.compareTo(totalOTHours) > 0)
                    {
                        totalOTHours = tempOT;
                        totalRegHours = maxWeekHours;
                    }
                }
            }
            totalStatHours = BigDecimal.valueOf(Double.valueOf(hoursArray[hoursArray.length - 1]));
        }
        catch (NumberFormatException e)
        {
            // BAD DATA IN HOUR ARRAY.
            totalRegHours = BigDecimal.ZERO;
            totalOTHours = BigDecimal.ZERO;
            totalStatHours = BigDecimal.ZERO;
        }

        BigDecimal totalLabourCost = BigDecimal.valueOf(shopPaymentCalculator.calculate(dailyHours,
            totalStatHours.doubleValue(), maxRegHours.doubleValue(), emp.getPrimaryWage().doubleValue()));
        totalLabourCost = BigDecimal.valueOf(Math.round(totalLabourCost.doubleValue() * 100), 2);
        totalHours[0] = totalRegHours;
        totalHours[1] = totalOTHours;
        totalHours[2] = totalStatHours;
        totalHours[3] = totalLabourCost;

        return totalHours;
    }

    // FIXED when the role is payroll, use employeesForShop
    // when the role is other roles. use hourlyEmployeesForShop
    public String employeesForShop(HttpServletRequest request, String shopId) throws ServletException, IOException
    {
        WebContext wctx = WebContextFactory.get();
        Shop shop = (Shop) this.mgr.get(Shop.class, Integer.valueOf(shopId));
        List<Employee> emps = null;
        Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities();
        for (GrantedAuthority auth : userAuthorities)
        {
            if (auth.getAuthority().equalsIgnoreCase("ROLE_PAYROLL")
                || auth.getAuthority().equalsIgnoreCase("ROLE_SUPREME")
                || auth.getAuthority().equalsIgnoreCase("ROLE_DSM"))
            {
                emps = this.employeeManager.employeesForShop(shop.getId());
                break;
            }
            else if (auth.getAuthority().equalsIgnoreCase("ROLE_SHOP_OWNER")
                || auth.getAuthority().equalsIgnoreCase("ROLE_SHOP_MGR")
                || auth.getAuthority().equalsIgnoreCase("ROLE_SHOP_CRUD")
                || auth.getAuthority().equalsIgnoreCase("ROLE_TIMESHEET"))
                emps = this.employeeManager.hourlyEmployeesForShop(shop.getId());
        }
        if (emps != null)
            Collections.sort(emps);
        request.setAttribute("EMPLOYEES", emps);
        request.setAttribute("SHOP", shop);
        String requestUrl = request.getRequestURL().toString();
        if (requestUrl.indexOf(";jsessionid=") >= 0)
        {
            request.setAttribute("URL_SESSION_ID", ";jsessionid=" + request.getRequestedSessionId());
        }
        return wctx.forwardToString("/WEB-INF/pages/employees.jsp");
    }

    public boolean setShop(String shopId)
    {
        WebContext wctx = WebContextFactory.get();
        Integer id = Integer.valueOf(shopId);
        Shop shop = (Shop) this.mgr.get(Shop.class, id);
        if (this.mgr.earliestSubmittedDailySalesForShop(shop.getId()) != null)
        {
            wctx.getSession().setAttribute("DAILYSALES_SHOP", shop);
            return true;
        }
        else
            return false;
    }

    public boolean hasAvailableReports(String shopId)
    {

        Integer id = Integer.valueOf(shopId);
        Shop shop = (Shop) this.mgr.get(Shop.class, id);
        DailySales earilestRecord = this.mgr.earliestDailySalesForShop(shop.getId());
        Calendar earilestDate = null;
        if (earilestRecord != null)
            earilestDate = DateUtils.truncate(earilestRecord.getSalesDate(), Calendar.DATE);
        Calendar today = DateUtils.truncate(this.mgr.businessDate(), Calendar.DATE);
        return (earilestDate != null && !earilestDate.after(today));
    }

    public Integer[] getShopSettings(Integer shopId)
    {
        Shop shop = (Shop) this.mgr.get(Shop.class, shopId);
        Integer[] settings = new Integer[3];
        settings[0] = shop.getQuantityOfFrontCounterMachines();
        settings[1] = shop.getQuantityOfWirelessMachines();
        settings[2] = shop.getId();
        return settings;
    }

    public boolean setShopSettings(Integer shopId, Integer numberofFrontCounterMachines,
        Integer numberofWirelessMachines)
    {
        Shop shop = (Shop) this.mgr.get(Shop.class, shopId);
        shop.setQuantityOfFrontCounterMachines(numberofFrontCounterMachines);
        shop.setQuantityOfWirelessMachines(numberofWirelessMachines);
        try
        {
            this.mgr.save(shop);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public DailySalesTabsSettings[] setDailySalesTabs(String date) throws ParseException
    {
        DailySalesTabsSettings[] dailySalesTabsSettings = new DailySalesTabsSettings[9];
        WebContext wctx = WebContextFactory.get();
        Shop shop = (Shop) wctx.getSession().getAttribute("DAILYSALES_SHOP");
        boolean hasChangeShopPriviliage = ((Boolean) wctx.getSession().getAttribute("HAS_CHANGE_SHOP_PREVILIAGE"))
            .booleanValue();
        Calendar displayDate = this.parseDisplayDate(date, shop, ((Boolean) hasChangeShopPriviliage));
        Calendar today = DateUtils.truncate(this.mgr.businessDate(), Calendar.DATE);
        DailySales latestSubmittedDailySales = this.mgr.latestSubmittedDailySalesForShop(shop.getId());
        DailySales earliestDailySales = this.mgr.earliestDailySalesForShop(shop.getId());
        if (latestSubmittedDailySales == null)
            latestSubmittedDailySales = earliestDailySales;
        Calendar firstUnsubmittedDate = (Calendar) latestSubmittedDailySales.getSalesDate().clone();
        Calendar firstSubmittedDate = (Calendar) earliestDailySales.getSalesDate().clone();
        if (hasChangeShopPriviliage == false && latestSubmittedDailySales.isSubmitted())
            firstUnsubmittedDate.add(Calendar.DATE, 1);
        Calendar returndate = (Calendar) displayDate.clone();
        returndate.add(Calendar.DATE, Calendar.SUNDAY - returndate.get(Calendar.DAY_OF_WEEK) - 1);
        returndate = DateUtils.truncate(returndate, Calendar.DATE);
        for (int i = 0; i < 9; i++)
        {
            dailySalesTabsSettings[i] = new DailySalesTabsSettings();
            if (i != 0)
                returndate.add(Calendar.DATE, 1);
            if (returndate.compareTo(displayDate) == 0)
                dailySalesTabsSettings[i].defaultDate = true;
            dailySalesTabsSettings[i].urlDate = AjaxManager.urlDateFormat.format(returndate.getTime());
            dailySalesTabsSettings[i].available = (!firstUnsubmittedDate.before(returndate)
                && !returndate.before(firstSubmittedDate) && !returndate.after(today));
            dailySalesTabsSettings[i].label = AjaxManager.displayDateFormat.format(returndate.getTime());
        }
        dailySalesTabsSettings[0].label = "<<";
        dailySalesTabsSettings[8].label = ">>";
        return dailySalesTabsSettings;
    }

    public String[] getAvailableDateRange()
    {
        String[] returnStrings = new String[2];
        WebContext wctx = WebContextFactory.get();
        Shop shop = (Shop) wctx.getSession().getAttribute("DAILYSALES_SHOP");
        returnStrings[0] = AjaxManager.popupCalendarDateFormat.format(this.mgr.earliestDailySalesForShop(shop.getId())
            .getSalesDate().getTime());
        returnStrings[1] = AjaxManager.popupCalendarDateFormat.format(getDefaultDisplayDateFormDB(shop,
            ((Boolean) wctx.getSession().getAttribute("HAS_CHANGE_SHOP_PREVILIAGE"))).getTime());
        return returnStrings;
    }

//    public String[] getPosReportDateRange()
//    {
//        String[] returnStrings = new String[2];
//        WebContext wctx = WebContextFactory.get();
//        Shop shop = (Shop) wctx.getSession().getAttribute("DAILYSALES_SHOP");
//        returnStrings[0] = AjaxManager.popupCalendarDateFormat.format(
//        		this.mgr.earliestPosReportForShop(shop.getId()).getBusinessDate().getTime());
//        returnStrings[1] = AjaxManager.popupCalendarDateFormat.format(
//         		this.mgr.latestPosReportForShop(shop.getId()).getBusinessDate().getTime());
//        return returnStrings;
//    }

    private Calendar parseDisplayDate(String date, Shop shop, Boolean hasChangeShopPreviliage) throws ParseException
    {
        Calendar defaultDisplayDate = this.getDefaultDisplayDateFormDB(shop, hasChangeShopPreviliage);
        if (StringUtils.isBlank(date))
            return defaultDisplayDate;
        Calendar returndate = DateParser.parseTrimedDate(date);
        if (returndate.after(defaultDisplayDate))
            return defaultDisplayDate;
        else
            return returndate;
    }

    // TODO check whether return date form DB is null
    private Calendar getDefaultDisplayDateFormDB(Shop shop, Boolean hasChangeShopPreviliage)
    {
        Calendar today = DateUtils.truncate(this.mgr.businessDate(), Calendar.DATE);
        Calendar yesterday = (Calendar) today.clone();
        yesterday.add(Calendar.DATE, -1);
        DailySales latestSubmittedDailySales = this.mgr.latestSubmittedDailySalesForShop(shop.getId());
        if (latestSubmittedDailySales == null)
        {
            return DateUtils.truncate(this.mgr.earliestDailySalesForShop(shop.getId()).getSalesDate(), Calendar.DATE);
        }
        Calendar lastSubmittedDate = latestSubmittedDailySales.getSalesDate();
        lastSubmittedDate = DateUtils.truncate(lastSubmittedDate, Calendar.DATE);
        if (hasChangeShopPreviliage.booleanValue() == true)
            return lastSubmittedDate;
        if (lastSubmittedDate.compareTo(yesterday) < 0)
        {
            lastSubmittedDate.add(Calendar.DATE, 1);
            return lastSubmittedDate;
        }
        else
            return today;
    }

}

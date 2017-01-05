package com.pizza73.service.impl;

import com.pizza73.dao.PayrollDao;
import com.pizza73.model.Employee;
import com.pizza73.model.PayYearPeriod;
import com.pizza73.model.Payroll;
import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PayrollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service("payrollManager")
public class PayrollManagerImpl extends UniversalManagerImpl implements PayrollManager
{
    @Autowired
    private PayrollDao payrollDao;
    @Autowired
    private EmployeeManager eMgr;
    @Autowired
    private LookupManager mgr;

    public PayrollManagerImpl()
    {
    }

    public Payroll lastPayroll(Integer employeeId)
    {
        return this.payrollDao.lastPayroll(employeeId);
    }

    public Payroll lastPayrollUnsubmitted(Integer employeeId)
    {
        return this.payrollDao.lastPayrollUnsubmitted(employeeId);
    }

    public Payroll lastPayrollSubmitted(Integer employeeId)
    {
        return this.payrollDao.lastPayrollSubmitted(employeeId);
    }

    public List<Payroll> payrollForEmployee(Integer employeeId)
    {
        return this.payrollDao.payrollForEmployee(employeeId);
    }

    public List<Payroll> payrollForEmployee(Integer employeeId, Integer year)
    {
        return this.payrollDao.payrollForEmployee(employeeId, year);
    }

    public Payroll payrollForPeriod(Integer employeeId, Integer period, Integer year)
    {
        return this.payrollDao.payrollForPeriod(employeeId, period, year);
    }

    public ShopPayroll searchForPayroll(Shop shop, Integer payPeriod, Integer payrollYear)
    {
        ShopPayroll shopPayroll = new ShopPayroll(shop);
        Double totalRegHours = 0.0d;
        Double totalOTHours = 0.0d;
        Double totalStatHours = 0.0d;
        List<Employee> employees = eMgr.activeEmployeesForShop(shop.getId(),true);
        Iterator<Employee> iter = employees.iterator();
        Employee emp = null;
        int payrollCount = 0;
        while (iter.hasNext())
        {
            emp = iter.next();
            Payroll payroll = this.payrollForPeriod(emp.getId(), payPeriod, payrollYear);
            if (payroll != null)
            {
                payrollCount++;
                totalRegHours += payroll.getTotalRegHours();
                totalOTHours += payroll.getTotalOTHours();
                totalStatHours += payroll.getTotalStatHours();
                emp.setCurrentPayroll(payroll);
                shopPayroll.addEmployee(emp);
            }
        }
        if (payrollCount == 0)
        {
            shopPayroll = null;
        }
        else
        {
            Collections.sort(shopPayroll.getEmployees());
            shopPayroll.setTotalOTHours(totalOTHours);
            shopPayroll.setTotalRegHours(totalRegHours);
            shopPayroll.setTotalStatHours(totalStatHours);
        }
        return shopPayroll;
    }

    public ShopPayroll currentShopPayroll(Shop shop)
    {
        ShopPayroll shopPayroll = new ShopPayroll(shop);
        final PayYearPeriod yearPeriodByYear = this.mgr.getYearPeriodByYear(shop.getPayrollYear());
        Integer maxPeriod = yearPeriodByYear.getNumOfPayPeriodYear();
        Integer payrollYear = shop.getPayrollYear();
        Integer payPeriod = shop.getCurrentPayPeriod();
        if (shop.getCurrentPayPeriod().intValue() < maxPeriod.intValue())
        {
            payPeriod++;
        }
        else
        {
            payrollYear++;
            payPeriod = 1;

        }
        this.employeePayrolls(shopPayroll, shop.getId(), payPeriod, payrollYear);
        return shopPayroll;
    }

    public ShopPayroll submittedShopPayroll(Shop shop)
    {
        ShopPayroll shopPayroll = new ShopPayroll(shop);
        Integer payPeriod = shop.getCurrentPayPeriod();
        Integer payrollYear = shop.getPayrollYear();

        this.employeePayrolls(shopPayroll, shop.getId(), payPeriod, payrollYear);

        return shopPayroll;
    }

    public ShopPayroll submittedShopPayroll(Shop shop, Integer selectedPayPeriod, Integer selectedPayYear)
    {
        ShopPayroll shopPayroll = null;
        if (this.isSubmittedPayPeriod(shop, selectedPayPeriod, selectedPayYear) == false)
            return shopPayroll;
        else
            return this.searchForPayroll(shop, selectedPayPeriod, selectedPayYear);
    }

    public List<ShopPayroll> allSubmittedPayrolls(Integer selectedPayPeriod, Integer selectedPayYear)
    {
        List<ShopPayroll> payrolls = new ArrayList<ShopPayroll>();
        List<Shop> shops = this.mgr.activeShops();
        Iterator<Shop> iter = shops.iterator();
        Shop shop = null;
        ShopPayroll shopPayroll = null;
        while (iter.hasNext())
        {
            shop = iter.next();
            shopPayroll = submittedShopPayroll(shop, selectedPayPeriod, selectedPayYear);
            if (shopPayroll != null)
                payrolls.add(shopPayroll);
        }
        return payrolls;
    }

    public List<ShopPayroll> allPayrolls(Integer curPayPeriod, Integer curPayrollYear)
    {
        List<ShopPayroll> payrolls = new ArrayList<ShopPayroll>();
        List<Shop> shops = this.mgr.activeShops();
        Iterator<Shop> iter = shops.iterator();
        Shop shop = null;
        ShopPayroll shopPayroll = null;
        while (iter.hasNext())
        {
            shop = iter.next();
            shopPayroll = submittedShopPayroll(shop, curPayPeriod, curPayrollYear);
            if (shopPayroll == null)
            {
                shopPayroll = new ShopPayroll(shop);
            }
            payrolls.add(shopPayroll);
        }

        return payrolls;
    }

    private void employeePayrolls(ShopPayroll shopPayroll, Integer shopId, Integer payPeriod, Integer payrollYear)
    {
        Double totalRegHours = 0d;
        Double totalOTHours = 0d;
        Double totalStatHours = 0d;

        List<Employee> emps = this.eMgr.hourlyApprovedEmployeesForShop(shopId);
        Iterator<Employee> iter = emps.iterator();
        Employee emp = null;
        while (iter.hasNext())
        {
            emp = iter.next();
            Payroll payroll = this.payrollForPeriod(emp.getId(), payPeriod, payrollYear);
            if (payroll == null)
            {
                payroll = new Payroll();
                payroll.setPayrollYear(payrollYear);
                payroll.setPayrollPeriod(payPeriod);
                payroll.setPayrollId(emp.getPayrollId());
                payroll.setEmployeeId(emp.getId());
            }
            else
            {
                if (emp.isCompressedWorkWeek())
                {

                }
                else
                {
                    totalRegHours += payroll.getTotalRegHours();
                    totalOTHours += payroll.getTotalOTHours();
                    totalStatHours += payroll.getTotalStatHours();
                }
            }
            emp.setCurrentPayroll(payroll);
        }
        Collections.sort(emps);
        shopPayroll.setEmployees(emps);
        shopPayroll.setTotalRegHours(totalRegHours);
        shopPayroll.setTotalOTHours(totalOTHours);
        shopPayroll.setTotalStatHours(totalStatHours);
        shopPayroll.setPayPeriod(payPeriod);
        shopPayroll.setYear(payrollYear);
    }

    public void savePayroll(ShopPayroll payroll)
    {
        List<Employee> employees = payroll.getEmployees();
        Iterator<Employee> iter = employees.iterator();
        Employee emp = null;
        Payroll pay = null;
        while (iter.hasNext())
        {
            emp = iter.next();
            pay = emp.getCurrentPayroll();
            pay.setPayrollWage(BigDecimal.valueOf(emp.getPrimaryWage()));
            if (pay.getPayPeriodStartDate() == null)
            {
                Integer thisPayrollYear = pay.getPayrollYear();
                thisPayrollYear = Integer.valueOf(thisPayrollYear.intValue());
                PayYearPeriod payYear = this.mgr.getYearPeriodByYear(thisPayrollYear);
                Calendar payPeriodStartDate = (Calendar) payYear.getPayrollYearStartDate().clone();
                payPeriodStartDate.add(Calendar.DATE, (pay.getPayrollPeriod() - 1) * 14);
                pay.setPayPeriodStartDate(payPeriodStartDate);
            }
            this.save(pay);
        }
    }

    public List<Payroll> payrollsForShopAndDate(Integer shopId, Calendar requestDate)
    {
        List<Payroll> payrolls = new ArrayList<Payroll>();
        List<Employee> employees = this.eMgr.employeesForShop(shopId);
        Iterator<Employee> iter = employees.iterator();
        Employee emp = null;
        Payroll payroll = null;
        while (iter.hasNext())
        {
            emp = iter.next();
            payroll = this.payrollForDate(emp.getId(), requestDate);
            if (payroll != null)
                payrolls.add(payroll);
        }
        return payrolls;
    }

    public Payroll payrollForDate(Integer employeeId, Calendar requestDate)
    {
        Payroll temp = this.payrollDao.payrollForDate(employeeId, requestDate);
        if (temp == null)
        {
            Calendar sameDayInLastWeek = (Calendar) requestDate.clone();
            sameDayInLastWeek.add(Calendar.DATE, -7);
            temp = this.payrollDao.payrollForDate(employeeId, sameDayInLastWeek);
        }
        return temp;
    }

    private boolean isSubmittedPayPeriod(Shop shop, Integer selectedPayPeriod, Integer selectedPayYear)
    {
        if (shop.getCurrentPayPeriod() == null || shop.getPayrollYear() == null)
            return false;
        if (selectedPayYear.intValue() < shop.getPayrollYear().intValue()
            || (selectedPayYear.intValue() == shop.getPayrollYear().intValue() && selectedPayPeriod.intValue() <= shop
                .getCurrentPayPeriod().intValue()))
            return true;
        else
            return false;
    }

    @Transactional
    public List<Payroll> statPayrollForShop(Integer shopId, Integer year)
    {
        List<Payroll> statPayrolls = this.payrollDao.statPayrollForShop(shopId, year);

        return statPayrolls;
    }

    @Transactional
    public List<Payroll> statPayrollForShopYearAndPeriods(Integer shopId, Integer year, List<Integer> periods)
    {
        List<Payroll> statPayrolls = this.payrollDao.statPayrollForShopYearAndPeriods(shopId, year, periods);

        return statPayrolls;
    }
}

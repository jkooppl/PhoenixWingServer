/**
 *
 */
package com.pizza73.service;

import com.pizza73.model.Payroll;
import com.pizza73.model.Shop;
import com.pizza73.model.ShopPayroll;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * @author chris
 *
 */
// removed b/c of auditing.
// @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public interface PayrollManager extends UniversalManager
{
    public Payroll lastPayroll(Integer employeeId);

    public Payroll lastPayrollUnsubmitted(Integer employeeId);

    public Payroll lastPayrollSubmitted(Integer employeeId);

    public List<Payroll> payrollForEmployee(Integer employeeId);

    public List<Payroll> payrollForEmployee(Integer employeeId, Integer year);

    public ShopPayroll searchForPayroll(Shop shop, Integer payPeriod, Integer payrollYear);

    public ShopPayroll currentShopPayroll(Shop shop);

    public ShopPayroll submittedShopPayroll(Shop shop);

    public List<ShopPayroll> allSubmittedPayrolls(Integer payrPeriod, Integer payrollYear);

    public List<ShopPayroll> allPayrolls(Integer payrPeriod, Integer payrollYear);

    @Transactional(readOnly = false)
    public void savePayroll(ShopPayroll payroll);

    public List<Payroll> payrollsForShopAndDate(Integer shopId, Calendar requestDate);

    public Payroll payrollForDate(Integer employeeId, Calendar requestDate);

    public Payroll payrollForPeriod(Integer employeeId, Integer period, Integer year);

    public List<Payroll> statPayrollForShop(Integer shopId, Integer year);
    public List<Payroll> statPayrollForShopYearAndPeriods(Integer shopId, Integer year, List<Integer> periods);
}

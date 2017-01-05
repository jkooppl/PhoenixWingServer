/**
 *
 */
package com.pizza73.dao;

import com.pizza73.model.Payroll;

import java.util.Calendar;
import java.util.List;

/**
 * @author chris
 *
 */
public interface PayrollDao extends UniversalDao
{
    public Payroll lastPayroll(Integer employeeId);

    public Payroll lastPayrollUnsubmitted(Integer employeeId);

    public Payroll lastPayrollSubmitted(Integer employeeId);

    public Payroll payrollForPeriod(Integer employeeId, Integer period, Integer year);

    public List<Payroll> payrollForEmployee(Integer employeeId);

    public List<Payroll> payrollForEmployee(Integer employeeId, Integer year);

    public boolean exists(Integer id);

    public Payroll get(Integer id);

    public List<Payroll> getAll();

    public void remove(Integer id);

    public Payroll save(Payroll o);

    public Payroll payrollForDate(Integer employeeId, Calendar requestDate);
    public List<Payroll> statPayrollForShop(Integer shopId, Integer year);
    public List<Payroll> statPayrollForShopYearAndPeriods(Integer shopId, Integer year, List<Integer> periods);
}

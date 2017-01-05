package com.pizza73.util;

import com.pizza73.model.Payroll;
import com.pizza73.service.EmployeeManager;
import com.pizza73.service.LookupManager;
import com.pizza73.service.PayrollManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatPayrollExport
{
    protected ClassPathXmlApplicationContext ctx;

    private PayrollManager payrollManager;

    private EmployeeManager employeeManager;

    private LookupManager lookupManager;

    @SuppressWarnings("unchecked")
    public void setUp()
    {
        String[] paths = {"util-applicationContext-resources.xml", "applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
        payrollManager = (PayrollManager) ctx.getBean("payrollManager");
        employeeManager = (EmployeeManager) ctx.getBean("employeeManager");
        lookupManager = (LookupManager) ctx.getBean("lookupManager");
    }

    public static void main(String[] args) throws Exception
    {
        StatPayrollExport spe = new StatPayrollExport();
        spe.setUp();
        Integer shopId = Integer.valueOf(args[0]);
        Integer year = Integer.valueOf(args[0]);
        Integer period = Integer.valueOf(args[0]);
        Integer week = Integer.valueOf(args[0]);
        String dayOfWeek = args[0];
        spe.payrolls(shopId, year, period, week, dayOfWeek);
    }

    public void payrolls(Integer shopId, Integer year, Integer period, Integer week, String dayOfWeek)
    {
        List<Integer> periods = new ArrayList<Integer>();
        int periodStart = period;
        if(week == 1)
        {
            periodStart = period - 1;
        }
        for(int i = period; i > period -5; i--)
        {
            int payPeriod = i;
            if(period <= 0)
            {
                period = period + 26;
            }
            periods.add(i);
        }
        List<Payroll> payrolls = payrollManager.statPayrollForShopYearAndPeriods(shopId, year, periods);

        Map<Integer, List<Payroll>> employeeStatPayrolls = new HashMap<Integer, List<Payroll>>();
        for(Payroll payroll : payrolls)
        {
            Integer employeeId = payroll.getEmployeeId();
            if(employeeStatPayrolls.containsKey(employeeId))
            {
                employeeStatPayrolls.get(employeeId).add(payroll);
            }
            else
            {
                List<Payroll> statPayroll = new ArrayList<Payroll>();
                employeeStatPayrolls.put(employeeId, statPayroll);
            }
        }

        for(Integer employeeId : employeeStatPayrolls.keySet())
        {
            List<Payroll> statPayrolls = employeeStatPayrolls.get(employeeId);
            System.out.println("Employee Id: " + employeeId + " payroll count: " + statPayrolls.size());
            System.out.println("Average stat hours");
        }
    }

}

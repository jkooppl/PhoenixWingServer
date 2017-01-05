<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Partners: Submitted Payrolls</title>
    <pizza73:adminHead />
    <link rel="stylesheet" type="text/css" media="screen" href="./css/payroll.css" />
    <link rel="stylesheet" type="text/css" media="print" href="./css/payrollPrint.css" />
  </head>
  <body>
    <pizza73:adminNav />
    <div id="payrollPage">
     <pizza73:adminTabs />
      <div id="payrollPageContent">
        <div id="payroll">
        <div id="contentColumn">
          <h1>Shop ${PAYROLL.shop.name} Payroll Information for Pay Period ${PAYROLL_PERIOD} (${PAYROLL_YEAR})</h1>
          <h1>Payroll Period Ending: ${PAYROLL_END_DATE}</h1>
          <h2 class="payrollSummary">Total Regular Hours<span class="summarySeperator">|</span><p id="totalRegHours">${PAYROLL.totalRegHours}</p></h2>
          <h2 class="payrollSummary">Total OT Hours<span class="summarySeperator">|</span><p id="totalOTHours">${PAYROLL.totalOTHours}</p></h2>
          <h2 class="payrollSummary">Total Stat Hours<span class="summarySeperator">|</span><p id="totalStatHours">${PAYROLL.totalStatHours}</p></h2>
          <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_PAYROLL,ROLE_SHOP_MGR,ROLE_DSM">
              <h2 class="payrollSummary">Total Labour Cost<p>$<span id="totalLabourCostTop">${TOTAL_LABOUR_COST}</span></p></h2>
          </authz:authorize>
        </div>
			<c:forEach items="${PAYROLL.employees}" var="emp" varStatus="empIndex">
                <c:set var="payroll" value="${emp.currentPayroll}"/>
                <input type="hidden" id="empId:${empIndex.count-1}" value="${emp.id}"/>
                <div <c:if test="${empIndex.count % 2 == 1}">class="payrollZebra"</c:if> style="clear:both">
                  <b>${emp.name} ${emp.lastName} (${emp.payrollId})<b>
                   <table class="payrollTable" cellspacing="0">
                     <thead <c:if test="${empIndex.count > 1 }">class="nonFirstRecord"</c:if>>
                       <tr>
                         <th>&nbsp;</th>
                         <th colspan="2" ><div class="payrollHeaderDay">Sun<div></th>
                         <th colspan="2" ><div class="payrollHeaderDay">Mon</div></th>
                         <th colspan="2" ><div class="payrollHeaderDay">Tues</div></th>
                         <th colspan="2" ><div class="payrollHeaderDay">Wed</div></th>
                         <th colspan="2" ><div class="payrollHeaderDay">Thurs</div></th>
                         <th colspan="2" ><div class="payrollHeaderDay">Fri</div></th>
                         <th colspan="2" ><div class="payrollHeaderDay">Sat</div></th>
                         <th style="text-align:center">&nbsp;</th>
                       </tr>
                       <tr>
                         <th>&nbsp;</th>
                         <th class="payrollHeaderStartEndTime">Start</th>
                         <th class="payrollHeaderStartEndTime">End</th>
                         <th class="payrollHeaderStartEndTime">Start</th>
                         <th class="payrollHeaderStartEndTime">End</th>
                         <th class="payrollHeaderStartEndTime">Start</th>
                         <th class="payrollHeaderStartEndTime">End</th>
                         <th class="payrollHeaderStartEndTime">Start</th>
                         <th class="payrollHeaderStartEndTime">End</th>
                         <th class="payrollHeaderStartEndTime">Start</th>
                         <th class="payrollHeaderStartEndTime">End</th>
                         <th class="payrollHeaderStartEndTime">Start</th>
                         <th class="payrollHeaderStartEndTime">End</th>
                         <th class="payrollHeaderStartEndTime">Start</th>
                         <th class="payrollHeaderStartEndTime">End</th>
                         <th class="payrollHeaderTotal">StatPay (week)</th>
                         <th class="payrollHeaderTotal">Reg (week)</th>
                         <th class="payrollHeaderTotal">OT (week)</th>
                         <th class="payrollHeaderTotal">Reg (total)</th>
                         <th class="payrollHeaderTotal">OT<br/>(total)</th>
                         <th class="payrollHeaderTotal">StatPay (total)</th>
                       </tr>
                     </thead>
                     <tbody>
                        <tr>
                          <td rowspan="2" style="text-align:center;padding:2px;"  class="payrollInputWeekNum">Week One</td>
                          <td id="empColumnSunWeekOneStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneSunStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnSunWeekOneEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneSunEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnMonWeekOneStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneMonStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnMonWeekOneEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneMonEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnTuesWeekOneStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneTuesStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnTuesWeekOneEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneTuesEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnWedWeekOneStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneWedStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
                          </td>
                          <td id="empColumnWedWeekOneEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneWedEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnThursWeekOneStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneThursStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnThursWeekOneEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneThursEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnFriWeekOneStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneFriStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnFriWeekOneEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneFriEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnSatWeekOneStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneSatStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>
                          <td id="empColumnSatWeekOneEnd:${emp.id}" class="payrollInputCell" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneSatEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true"/>
					     </td>					     
					      <td id="empColumnStatWeekOne:${emp.id}" rowspan="2" style="vertical-align:top" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekOneStat}" class="payrollInputStat" style="background-color:#dddddd;" readonly="true"/>
					      </td>
					      <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
					      	<input type="text" value="${emp.currentPayroll.totalHoursWeekOne}" readonly="true" class="payrollInputWeeklySummary" />
					      </td>
					      <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
							<input type="text" value="${emp.currentPayroll.overtimeHoursWeekOne}" readonly="true" class="payrollInputWeeklySummary" />
					      </td>
					      <td rowspan="4" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.totalRegHours}" readonly="true" class="payrollInputTotalSummary" />
					      </td>
					      <td rowspan="4" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.totalOTHours}" readonly="true" class="payrollInputTotalSummary" />
					      </td>
					      <td rowspan="4" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.totalStatHours}" readonly="true" class="payrollInputTotalSummary" />
					      </td>				      					   					   					   
                        </tr>
                        <tr>
                        <td colspan="2" class="payrollInputHourCell"><input type="text" value="${emp.currentPayroll.weekOneSun}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell"><input type="text" value="${emp.currentPayroll.weekOneMon}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell"><input type="text" value="${emp.currentPayroll.weekOneTues}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell"><input type="text" value="${emp.currentPayroll.weekOneWed}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell"><input type="text" value="${emp.currentPayroll.weekOneThurs}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell"><input type="text" value="${emp.currentPayroll.weekOneFri}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell"><input type="text" value="${emp.currentPayroll.weekOneSat}" class="payrollInputHour" readonly="true"/></td>
                        </tr>
                        <tr>
                          <td rowspan="2" style="text-align:center;padding:2px;" class="payrollInputWeekNum">Week Two</td>
                          <td id="empColumnSunWeekTwoStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoSunStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnSunWeekTwoEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoSunEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnMonWeekTwoStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoMonStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnMonWeekTwoEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoMonEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnTuesWeekTwoStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoTuesStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnTuesWeekTwoEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoTuesEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnWedWeekTwoStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoWedStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnWedWeekTwoEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoWedEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnThursWeekTwoStart:${emp.id}" class="payrollInputCell">
                            <input type=text" value="${emp.currentPayroll.weekTwoThursStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnThursWeekTwoEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoThursEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnFriWeekTwoStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoFriStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnFriWeekTwoEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoFriEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnSatWeekTwoStart:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoSatStart}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
                          <td id="empColumnSatWeekTwoEnd:${emp.id}" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoSatEnd}" class="payrollInput" style="background-color:#dddddd;" readonly="true" />
					     </td>
					      <td id="empColumnStatWeekTwo:${emp.id}" rowspan="2" style="vertical-align:top" class="payrollInputCell">
                            <input type="text" value="${emp.currentPayroll.weekTwoStat}" class="payrollInputStat" style="background-color:#dddddd;" readlonly="true" />
					      </td>
					      <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
					      	<input type="text" value="${emp.currentPayroll.totalHoursWeekTwo}" readonly="true" class="payrollInputWeeklySummary" />
					      </td>
					      <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
							<input type="text" value="${emp.currentPayroll.overtimeHoursWeekTwo}" readonly="true" class="payrollInputWeeklySummary" />
					      </td>	   					   					   
                        </tr>
                        <tr>
                        <td colspan="2" class="payrollInputHourCell""><input type="text" value="${emp.currentPayroll.weekTwoSun}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell""><input type="text" value="${emp.currentPayroll.weekTwoMon}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell""><input type="text" value="${emp.currentPayroll.weekTwoTues}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell""><input type="text" value="${emp.currentPayroll.weekTwoWed}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell""><input type="text" value="${emp.currentPayroll.weekTwoThurs}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell""><input type="text" value="${emp.currentPayroll.weekTwoFri}" class="payrollInputHour" readonly="true"/></td>
                        <td colspan="2" class="payrollInputHourCell""><input type="text" value="${emp.currentPayroll.weekTwoSat}" class="payrollInputHour" readonly="true"/></td>
                        </tr>
                        <tr class="payrollEmptyRow"><td>&nbsp;</td></tr>
                     </tbody>
                  </table>
                </div>
                <c:if test="${empIndex.count == 11 ||(empIndex.count>12 && (empIndex.count%12 ==0))}">
                <div class="pageBreakMarker" >&nbsp;</div>
				</c:if>
                <c:set var="empCount" value="${empIndex.count-1}"/>
              </c:forEach> 
          <div id="contentColumn">
          <div class="column1">
           <h2>Total Regular Hours<p id="totalRegHours">${PAYROLL.totalRegHours}</p></h2>
           <h2>Total OT Hours<p id="totalOTHours">${PAYROLL.totalOTHours}</p></h2>
           <h2>Total Stat Hours<p id="totalStatHours">${PAYROLL.totalStatHours}</p></h2>
           <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_PAYROLL,ROLE_SHOP_MGR,ROLE_DSM">
              <h2>Total Labour Cost<p>$<span id="totalLabourCostBottom">${TOTAL_LABOUR_COST}</span></p></h2>
          </authz:authorize>
          </div>                  
          </div>                        
        </div>
      </div>
      <c:remove var="PAYROLL" />
      <c:remove var="PAYROLL_PERIOD" />
      <c:remove var="PAYROLL_YEAR" />
      <c:remove var="TOTAL_LABOUR_COST" />
      <pizza73:adminFooter />
    </div>
  </body>
</html>

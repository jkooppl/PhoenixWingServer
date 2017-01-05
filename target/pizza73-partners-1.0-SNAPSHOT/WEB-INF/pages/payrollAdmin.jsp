<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Payroll Admin</title>
    <pizza73:adminHead />
  </head>
  <body onload="setupSelector();">
    <pizza73:adminNav />
    <div id="page">
     <pizza73:adminTabs />
      <div id="pageContent">
        <div id="contentColumn">
          <div id="payroll">
            <h1>Payroll Admin for current Pay Period ${PAYROLL_PERIOD} (${PAYROLL_YEAR})</h1>
            <div class="column1">
                <c:if test="${not empty MESSAGE}">
                <p><b>${MESSAGE}</b></p>
                </c:if>
                <p>Choose one of the options below.</p>
                <p>
                <a href="payrollPeriod.html"><b>Change Global Pay Period</b></a><br/>
                <a href="payrollPeriod.html?action=SHOP"><b>Change Shop Pay Period</b></a><br/>
                </p>
            </div>
          </div>
        </div>
      </div>
      <pizza73:adminFooter />
    </div>
    <c:remove var="MESSAGE" />
  </body>
</html>
`
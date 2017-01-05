<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee Information</title>
    <pizza73:adminHead />
    <script type='text/javascript' src='<c:url value="dwr/engine.js" />'></script>
    <script type='text/javascript' src='<c:url value="dwr/util.js" />'></script>    
  </head>
  <body>
    <pizza73:adminNav />
    <div id="page">
     <pizza73:adminTabs />
      <div id="pageContent">
        <div id="contentColumn">
          <form:form id="payrollSearch" commandName="payrollSearch">
            <form:errors path="*" cssClass="checkoutError" />
            <div class="customerForm">
              <h1>Submitted Payroll Search</h1>
              <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_DSM">
              <div class="column1">
                <p>
                  <label for="name">Shop</label> 
                  <form:select path="shop" cssClass="textInput" tabindex="1">
                    <form:options items="${SHOPS}" itemValue="id" itemLabel="name"/>
                  </form:select>
                </p>
              </div>
              </authz:authorize>
              <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_SHOP_MGR,ROLE_TIMESHEET">
              <input type="hidden" name="shop" value="<c:out value="${THIS_SHOP.id}" />" />
              </authz:authorize>
              <div class="column1">
                <p>
                  <label for="lastName">Payroll Period</label> 
                  <form:select path="payrollPeriod" items="${PAYROLL_PERIODS}" cssClass="textInput" tabindex="2"/>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="phone">Payroll Year</label> 
                  <form:select path="payrollYear" items="${PAYROLL_YEARS}" cssClass="textInput" tabindex="3"/>
                </p>
              </div>
              <div class="column1">
                <p>
                  <input type="submit" value="Search" tabindex="4"/>
                </p>
              </div>                        
            </div>
          </form:form>
        </div>
      </div>
      <pizza73:adminFooter />
    </div>
  </body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Payroll Period Admin</title>
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
          <form:form id="payrollPeriod" commandName="payrollPeriod">
            <form:errors path="*" cssClass="checkoutError" />
            <div class="customerForm">
              <h1>Update Payroll Period.</h1>
              <c:if test="${not empty ACTION}">
              <div class="column1">
                <p>
                  <label for="name">Shop</label> 
                  <form:select path="shop" cssClass="textInput" tabindex="1">
                    <form:options items="${SHOPS}" itemValue="id" itemLabel="name"/>
                  </form:select>
                </p>
              </div>
              </c:if>
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
                    <c:choose>
	                    <c:when test="${not empty ACTION}">
	                        <input type="submit" value="Update Shop Period" name="action" tabindex="4"/>
	                    </c:when>
	                    <c:otherwise>
	                       <input type="submit" value="Update Pay Period" name="action" tabindex="4"/>
	                    </c:otherwise>
                    </c:choose>
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

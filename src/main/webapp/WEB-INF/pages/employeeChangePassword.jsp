<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73: Shop Admin Change Login Id</title>
    <pizza73:adminHead />
  </head>
  <body>
    <pizza73:adminNav />
    <div id="page">
      <pizza73:adminTabs />
      <div id="pageContent">
        <div id="contentColumn">
          <form:form id="employeeForm" commandName="employee">
            <form:errors path="*" cssClass="checkoutError" />
            <div id="demographics">
              <div class="column1">
                <p>
                  <label for="oldPassword" class="required">Current Login Id&nbsp;</label>
                  <form:password path="oldPassword" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="1" />
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="email" class="required">New Login Id&nbsp;</label>
                  <form:password path="email" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="2" />
                </p>
                <p>
                  <label for="confirmPassword" class="required">Confirm Login Id&nbsp;</label>
                  <form:password path="confirmPassword" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="3" />
                </p>
                <p><input id="createAccount" type="submit" value="Change Login Id" tabindex="4" /></p>
                <p><input type="submit" name="CANCEL_ACTION" value="Cancel" tabindex="5"/></p>
              </div>
            </div>
          </form:form>
        </div>
      </div>
      <pizza73:adminFooter />
    </div>
    <c:remove var="MESSAGE" scope="session" />
  </body>
</html>

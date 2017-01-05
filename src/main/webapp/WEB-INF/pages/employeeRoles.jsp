<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee Information</title>
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
            <div class="customerForm">
              <c:choose>
                <c:when test="${not empty employee.address}">Edit </c:when>
                <c:otherwise>Add </c:otherwise>
              </c:choose>
              <h1>Add or Remove employee role information for ${employee.name} ${employee.lastName}</h1>
              <p>Hold down the "ctrl" key and select the roles that this employee should have</p>
              <div class="column1">
                <p>
                  <label for="roles">Roles</label> 
                  <form:select path="roles" items="${ROLES}" cssClass="textInput" tabindex="1" multiple="true" size="6"/>
                </p>
              </div>              
              <div class="column1">
                <p>
                  <input type="submit" value="Save/Edit" tabindex="2"/>
                  <input type="submit" name="CANCEL_ACTION" value="Cancel" tabindex="3"/>
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

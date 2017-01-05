<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee Information</title>
    <pizza73:adminHead />
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/util.js'></script>    
    <script type='text/javascript' src='dwr/interface/EmployeeAddressValidatorJS.js'></script>
    <script language="JavaScript">
      DWREngine.setErrorHandler(null); // Swallow errors and warnings from DWR; handle appropriately in your app
      DWREngine.setWarningHandler(null);
      
      function validateInputField(element)
      {
         EmployeeAddressValidatorJS.getInputFieldValidationMessage(element.id, element.value, {
            callback:function(dataFromServer) {
               setInputFieldStatus(element.id, dataFromServer);
            }
         });      
      }
      
      function setInputFieldStatus(elementId, message)
      {
         document.getElementById("" + elementId + ".error").innerHTML = message;
         if(message.length > 0)
         {
            document.getElementById("" + elementId).className='textInput checkoutError';
         }
         else
         {
            document.getElementById("" + elementId).className='textInput';
         }
      }
    </script>
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
              <h1>Employee Address Information for ${employee.name} ${employee.lastName}</h1>
              <spring:nestedPath path="address">
                <div class="column2">
                  <p>
                    <label for="streetAddress">Address</label> 
                    <form:input path="streetAddress" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="1" maxlength="50" onblur="validateInputField(this)"/>
                    <span id="streetAddress.error"" class="error"></span>
                  </p>
                </div>    
                <div class="column1">
                  <p>
                    <label for="suiteNumber">Suite Number</label> 
                    <form:input path="suiteNumber" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="2" maxlength="50" onblur="validateInputField(this)"/>
                    <span id="suiteNumber.error"" class="error"></span>
                  </p>
                </div>                                                    
                <div class="column2">
                  <p>
                    <label for="city">City</label> 
                    <form:input path="city" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="3" maxlength="20" onblur="validateInputField(this)"/>
                    <span id="city.error"" class="error"></span>
                  </p>
                </div>
                <div class="column1">
                  <p>
                    <label for="postalCode">Postal Code</label> 
                    <form:input path="postalCode" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="4" maxlength="6" onblur="validateInputField(this)"/>
                    <span id="postalCode.error"" class="error"></span>
                  </p>
                </div>
                <div class="column2">
                  <p>
                    <label for="province">Province</label> 
                    <form:select path="province" items="${PROVINCES}" cssClass="textInput" tabindex="5"/>
                  </p>
                </div>              
              </spring:nestedPath>
              <div class="column1">
                <p>
                  <input type="submit" value="Save/Edit" tabindex="6"/>
                  <input type="submit" name="CANCEL_ACTION" value="Cancel" tabindex="7"/>
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

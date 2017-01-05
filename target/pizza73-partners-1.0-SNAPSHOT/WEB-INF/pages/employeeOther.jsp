<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee Information</title>
    <pizza73:adminHead />
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/util.js'></script>    
    <script type='text/javascript' src='dwr/interface/EmployeeValidatorJS.js'></script>
    <script language="JavaScript">
      DWREngine.setErrorHandler(null); // Swallow errors and warnings from DWR; handle appropriately in your app
      DWREngine.setWarningHandler(null);
      
      function validateInputField(element)
      {
         EmployeeValidatorJS.getInputFieldValidationMessage(element.id, element.value, {
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
                <c:when test="${not empty employee}">Edit </c:when>
                <c:otherwise>Add </c:otherwise>
              </c:choose>
              <h1>Employee Information</h1>
              <div class="column1">
                <p>
                  <label for="name">First Name</label> 
                  <form:input path="name" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="1" maxlength="20" onblur="validateInputField(this)"/>
                  <span id="name.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="lastName">Last Name</label> 
                  <form:input path="lastName" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="2" maxlength="30" onblur="validateInputField(this)"/>
                  <span id="lastName.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="middleInitial">Middle Initial</label> 
                  <form:input path="middleInitial" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="3" maxlength="1" onblur="validateInputField(this)"/>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="email">Username</label> 
                  <form:input path="email" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="4" maxlength="4" onblur="validateInputField(this)"/>
                  <span id="email.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="sin">SIN (Social Insurance Number)</label> 
                  <form:input path="sin" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="5" maxlength="9" onblur="validateInputField(this)"/>
                  <span id="sin.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="birthDay">Date Of Birth</label> 
                  <form:input path="birthDay" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="6" onblur="validateInputField(this)"/>
                  <span id="birthDay.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="hireDate">Hire Date</label> 
                  <form:input path="hireDate" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="7" onblur="validateInputField(this)"/>
                  <span id="hireDate.error"" class="error"></span>
                </p>
              </div>
              <spring:nestedPath path="address">
                <div class="column2">
                  <p>
                    <label for="streetAddress">Address</label> 
                    <form:input path="streetAddress" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="8" maxlength="50" onblur="validateInputField(this)"/>
                    <span id="streetAddress.error"" class="error"></span>
                  </p>
                </div>    
                <div class="column1">
                  <p>
                    <label for="suiteNumber">Suite Number</label> 
                    <form:input path="suiteNumber" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="8" maxlength="50" onblur="validateInputField(this)"/>
                    <span id="suiteNumber.error"" class="error"></span>
                  </p>
                </div>                                                    
                <div class="column2">
                  <p>
                    <label for="city">City</label> 
                    <form:input path="city" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="9" maxlength="20" onblur="validateInputField(this)"/>
                    <span id="city.error"" class="error"></span>
                  </p>
                </div>
                <div class="column1">
                  <p>
                    <label for="postalCode">Postal Code</label> 
                    <form:input path="postalCode" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="10" maxlength="6" onblur="validateInputField(this)"/>
                    <span id="postalCode.error"" class="error"></span>
                  </p>
                </div>
                <div class="column2">
                  <p>
                    <label for="province">Province</label> 
                    <form:input path="province" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="11" maxlength="2" onblur="validateInputField(this)"/>
                    <span id="province.error"" class="error"></span>
                  </p>
                </div>              
              </spring:nestedPath>
              <div class="column1">
                <p>
                  <label for="primaryWage">Primary Wage</label> 
                  <form:input path="primaryWage" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="12" onblur="validateInputField(this)"/>
                  <span id="primaryWage.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="sex">Sex</label> 
                  <form:select path="sex" cssClass="textInput" items="${SEX}" tabindex="13" />
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="signedOTAgreement">Overtime Agreement Signed</label> 
                  <form:select path="signedOTAgreement" items="${YES_NO}" cssClass="textInput" tabindex="14"/>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="salariedEmployee">Salaried Employee</label> 
                  <form:select path="salariedEmployee" items="${YES_NO}" cssClass="textInput" tabindex="15"/>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="directDeposit">Direct Deposit</label> 
                  <form:select path="directDeposit" items="${YES_NO}" cssClass="textInput" tabindex="16"/>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="bankInstitutionCode">Institution Code</label> 
                  <form:input path="bankInstitutionCode" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="17" onblur="validateInputField(this)"/>
                  <span id="bankInstitutionCode.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="bankTransitCode">Transit Code</label> 
                  <form:input path="bankTransitCode" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="18" onblur="validateInputField(this)"/>
                  <span id="bankTransitCode.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="bankAccountNumber">Account Number</label> 
                  <form:input path="bankAccountNumber" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="19" onblur="validateInputField(this)"/>
                  <span id="bankAccountNumber.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="taxExemption">Tax Exemption</label> 
                  <form:select path="taxExemption" items="${TAX_EXEMPTION}" cssClass="textInput" tabindex="20"/>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="status">Employee Status</label> 
                  <form:select path="status" items="${EMP_STATUS}" cssClass="textInput" tabindex="21"/>
                </p>
              </div>  
              <div class="column1">
                <p>
                  <input type="submit" value="Save/Edit" tabindex="22"/>
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

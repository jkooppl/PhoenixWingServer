<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee Information</title>
    <pizza73:adminHead />
    <script type='text/javascript' src='js/yui/yahoo-min.js'></script>  
    <script type='text/javascript' src='js/yui/event-min.js'></script>
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/util.js'></script>    
    <script type='text/javascript' src='dwr/interface/EmployeeBankingValidatorJS.js'></script>
    <script language="JavaScript">
      DWREngine.setErrorHandler(null); // Swallow errors and warnings from DWR; handle appropriately in your app
      DWREngine.setWarningHandler(null);
      
      function validateInputField(element)
      {
         EmployeeBankingValidatorJS.getInputFieldValidationMessage(element.id, element.value, {
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
      
      function init() {            
        updateCompressedWorkWeekHours();
        var cww = document.getElementById('compressedWorkWeek');
      
        YAHOO.util.Event.purgeElement(cww);
        YAHOO.util.Event.on(cww, 'change', updateCompressedWorkWeekHours);
      }
      
      function updateCompressedWorkWeekHours()
      {
        var cwwh = document.getElementById("cwwh");
        if(cwwh){
          var cww = document.getElementById('compressedWorkWeek');
          var options = cww.options;
          var text = options[cww.selectedIndex].text;
          if(text == "Yes"){
            cwwh.style.display = 'block'; 
          } else {
            cwwh.style.display= 'none'; 
          }
        }
      }
    </script>
  </head>
  <body onload="init()">
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
              <h1>Employee Banking Information for ${employee.name} ${employee.lastName}</h1>
              <div class="column1">
                <p>
                  <label for="payrollId">Payroll Id</label> 
                  <form:input path="payrollId" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="1" maxlength="9" onblur="validateInputField(this)"/>
                  <span id="payrollId.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="sin">SIN (Social Insurance Number)</label> 
                  <form:input path="sin" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="2" maxlength="9" onblur="validateInputField(this)"/>
                  <span id="sin.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="birthDay">Date Of Birth</label> 
                  <form:input path="birthDay" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="3" onblur="validateInputField(this)"/>
                  <span id="birthDay.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="hireDate">Hire Date</label> 
                  <form:input path="hireDate" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="4" onblur="validateInputField(this)"/>
                  <span id="hireDate.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="primaryWage">Primary Wage</label> 
                  <form:input path="primaryWage" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="5" onblur="validateInputField(this)"/>
                  <span id="primaryWage.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="salariedEmployee">Salaried Employee</label> 
                  <form:select path="salariedEmployee" items="${YES_NO}" cssClass="textInput" tabindex="6"/>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="directDeposit">Direct Deposit</label> 
                  <form:select path="directDeposit" items="${YES_NO}" cssClass="textInput" tabindex="7"/>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="bankInstitutionCode">Institution Code</label> 
                  <form:input path="bankInstitutionCode" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="8" onblur="validateInputField(this)"/>
                  <span id="bankInstitutionCode.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="bankTransitCode">Transit Code</label> 
                  <form:input path="bankTransitCode" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="9" onblur="validateInputField(this)"/>
                  <span id="bankTransitCode.error"" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="bankAccountNumber">Account Number</label> 
                  <form:input path="bankAccountNumber" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="10" onblur="validateInputField(this)"/>
                  <span id="bankAccountNumber.error"" class="error"></span>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="signedOTAgreement">Overtime Agreement Signed</label> 
                  <form:select path="signedOTAgreement" items="${YES_NO}" cssClass="textInput" tabindex="11"/>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="sex">Sex</label> 
                  <form:select path="sex" cssClass="textInput" items="${SEX}" tabindex="12" />
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="taxExemption">Tax Exemption</label> 
                  <form:select path="taxExemption" items="${TAX_EXEMPTION}" cssClass="textInput" tabindex="13"/>
                </p>
              </div>
              <div class="column1">
                <p>
                  <label for="compressedWorkWeek">Compressed Work Week</label> 
                  <form:select path="compressedWorkWeek" items="${YES_NO}" cssClass="textInput" tabindex="14"/>
                </p>
              </div>     
              <div class="column2" id="cwwh">
                <p>
                  <label for="compressedHours">Compressed Work Week Hours</label> 
                  <form:input path="compressedHours" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="15" onblur="validateInputField(this)"/>
                  <span id="compressedHours.error"" class="error"></span>
                </p>
              </div>         
              <div class="column1">
                <p>
                  <input type="submit" value="Save/Edit" tabindex=16"/>
                  <input type="submit" name="CANCEL_ACTION" value="Cancel" tabindex="17"/>
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Driver Information</title>
    <pizza73:adminHead />
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/util.js'></script>    
    <script type='text/javascript' src='dwr/interface/DriverValidatorJS.js'></script>
    <script language="JavaScript">
      DWREngine.setErrorHandler(null); // Swallow errors and warnings from DWR; handle appropriately in your app
      DWREngine.setWarningHandler(null);
      
      function validateInputField(element)
      {
         DriverValidatorJS.getInputFieldValidationMessage(element.id, element.value, {
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
        <form:form id="driverForm" commandName="driver">
          <form:errors path="*" cssClass="checkoutError" />
          <div class="customerForm">
            <h1>
              <c:choose>
                <c:when test="${not empty driver.id}">Edit </c:when>
                <c:otherwise>Add </c:otherwise>
              </c:choose>
              Driver Information
            </h1>
            <div class="column1">
              <p>
                <label for="name">Name</label> 
                <form:input path="name" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="1" maxlength="20" onchange="validateInputField(this)"/>
                <span id="name.error"" class="error"></span>
              </p>
            </div>
            <div class="column2">
              <p>
                <label for="nickname">Nickname</label> 
                <form:input path="nickname" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="2" maxlength="10" onblur="validateInputField(this)"/>
                <span id="nickname.error"" class="error"></span>
              </p>
            </div>
            <div class="column1">
              <p>
                <label for="address">Address</label> 
                <form:input path="address" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="3" maxlength="64" onblur="validateInputField(this)"/>
                <span id="address.error"" class="error"></span>
              </p>
            </div>
            <div class="column2">
              <p>
                <label for="phone">Phone Number</label> 
                <form:input path="phone" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="4" maxlength="12" onblur="validateInputField(this)"/>
                <span id="phone.error"" class="error"></span>
              </p>
            </div>        
            <div class="column1">
              <p>
                <label for="vehicleDescription">Vehicle Description</label> 
                <form:input path="vehicleDescription" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="5" maxlength="32" onblur="validateInputField(this)"/>
                <span id="vehicleDescription.error"" class="error"></span>
              </p>
            </div>
            <div class="column2">
              <p>
                <label for="insurer">Insurer</label> 
                <form:input path="insurer" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="6" maxlength="32" onblur="validateInputField(this)"/>
                <span id="insurer.error"" class="error"></span>
              </p>
            </div>
            <div class="column1">
              <p>
                <label for="policyNumber">Policy Number</label> 
                <form:input path="policyNumber" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="7" maxlength="32" onblur="validateInputField(this)"/>
                <span id="policyNumber.error"" class="error"></span>
              </p>
            </div>
            <div class="column2">
              <p>
                <label for="insExpiryDate">Expiry Date</label> 
                <form:input path="insExpiryDate" cssClass="textInput" cssErrorClass="textInput checkoutError" tabindex="8" maxlength="64" onchange="validateInputField(this)"/>
                <span id="insExpiryDate.error"" class="error"></span>
              </p>
            </div>
            <div class="column1">
              <p>
                <label for="liabilitySigned">Liability Signed</label> 
                <form:select path="liabilitySigned" items="${YES_NO}" cssClass="textInput" tabindex="9"/>
              </p>
            </div>
            <div class="column2">
              <p>
                <label for="status">Status</label> 
                <form:select path="status" items="${DRIVER_STATUS}" cssClass="textInput" tabindex="10"/>
              </p>
            </div>
            <div class="column1">
              <p>
                <input type="submit" value="Save/Edit" tabindex="11"/>
                <input type="submit" name="CANCEL_ACTION" value="Cancel" tabindex="12"/>
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

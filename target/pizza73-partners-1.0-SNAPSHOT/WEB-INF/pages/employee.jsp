<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee Information</title>
    <pizza73:adminHead />
    <script type='text/javascript' src='<c:url value="dwr/engine.js" />'></script>
    <script type='text/javascript' src='<c:url value="dwr/util.js" />'></script>

   <!-- Individual YUI CSS files -->
<link rel="stylesheet" type="text/css" href="./yui-partners/2.5.0/calendar/assets/skins/sam/calendar.css" />
    <script type="text/javascript" src="./yui-partners/2.5.0/yahoo-dom-event/yahoo-dom-event.js"></script>
    <script type="text/javascript" src="./yui-partners/2.5.0/calendar/calendar-min.js"></script>
    <script type='text/javascript' src='<c:url value="dwr/interface/EmployeeValidatorJS.js" />'></script>

    <script type='text/javascript' src='<c:url value="dwr/interface/EmployeeValidatorJS.js" />'></script>
    <script type='text/javascript' src='./dojo/1.0.2/20080220/dojo.js'></script>
    <script type='text/javascript' src='./dojo/1.0.2/20080220/pizza73_dojo.js'></script>
    <script language="JavaScript">
      DWREngine.setErrorHandler(null); // Swallow errors and warnings from DWR; handle appropriately in your app
      DWREngine.setWarningHandler(null);

      function validateInputField(element)
      {
         EmployeeValidatorJS.getInputFieldValidationMessage(element.id, element.value, {
            callback:function(dataFromServer) {
               setInputFieldStatus(element.id, dataFromServer);
            },async:false
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

      function validateSIN(){
         if(document.getElementById("sin").className.indexOf("checkoutError") != -1)
            return;
         var sin=document.getElementById("sin").value;
         var message='';
         if(Number(sin.substring(0,1)) == 0)
           message="TTN(temporary taxation number) is not acceptable for employement.";
         else if ( sumCheck(sin) == false){
           message="SIN is invalid.";
         }
         setInputFieldStatus("sin",message);
         if(document.getElementById("sin").className.indexOf("checkoutError") == -1 && Number(sin.substring(0,1)) == 9)
            alert("If the SIN begins with a 9, you must provide a copy of your visa stating that you are allowed to work in Canada, and a photocopy of your SIN card.");
      }

      function validateLoginId(node){
         node.value=dojo.trim(node.value);
         validateInputField(node);
      }

      function nullPhoneNumberCheck(){
        if(document.getElementById('phone').className.indexOf("checkoutError") != -1)
           return;
        var phone=dojo.trim(document.getElementById('phone').value);
        var message='';
        if(phone == null || phone == '')
            message='Phone is a required field.';
        setInputFieldStatus("phone",message);

      }

      function sumCheck(sin){
        var i=0;
        var sum=0;
        for(i=0;i<9;i++){
      		if(i%2 == 0)
      		  sum+= Number(sin.substring(i,i+1));
      		else
      		  sum+= digitCal(Number(sin.substring(i,i+1)));
       }
       return (sum%10 ==0);
      }

      function digitCal(aNumber){
        var digit=aNumber;
        digit=digit+digit;
        if(digit >=10)
          digit=1+(digit-10);
       return digit;
      }

      function validateValues(){
        var isNewEmployee=dojo.byId("newEmployee").value;
        if(isNewEmployee == 'false')
           return true;
        else{
          validateAll();
        if (dojo.query('.checkoutError').length == 0)
           return true;
        else{
           alert('Please correct the error(s) before saving.');
           return false;
        }
        }
      }

      function validateAll(){
      	validateInputField(dojo.byId('name'));
      	validateInputField(dojo.byId('middleInitial'));
      	validateInputField(dojo.byId('lastName'));
      	validateInputField(dojo.byId('address.streetAddress'));
      	validateInputField(dojo.byId('phone'));
      	nullPhoneNumberCheck();
      	validateInputField(dojo.byId('address.city'));
      	validateInputField(dojo.byId('address.postalCode'));
      	validateInputField(dojo.byId('address.province'));
      	validateInputField(dojo.byId('sin'));
      	validateSIN();
      	validateInputField(document.getElementById('birthDay'));
      	validateInputField(dojo.byId('sex'));
      	validateInputField(dojo.byId('maritalStatus'));
      	validateInputField(document.getElementById('hireDate'));
      	validateInputField(dojo.byId('position'));
      	validateInputField(dojo.byId('typeOfEmployment'));
      	validateLoginId(dojo.byId('email'));
      	validateInputField(dojo.byId('primaryWage'));
      }

      function init() {
        updateCompressedWorkWeekHours();
        var cww = document.getElementById('compressedWorkWeek');

        YAHOO.util.Event.purgeElement(cww);
        YAHOO.util.Event.on(cww, 'change', updateCompressedWorkWeekHours);

        updateCompressedWorkWeekHours();
        <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
        var calDOB = new YAHOO.widget.Calendar("calDOB","calDOBContainer", { title:"Choose a date:", close:true,navigator:true } );
        calDOB.selectEvent.subscribe(DOBcalendarSelectHandler,calDOB,true);
        calDOB.hideEvent.subscribe(function(){validateInputField(document.getElementById('birthDay'));},calDOB,true);
     	calDOB.render();
        YAHOO.util.Event.addListener("birthDay", "focus", calDOB.show, calDOB, true);
        </authz:authorize>
       <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER">
       <c:if test="${empty employee.id}">
        var calDOB = new YAHOO.widget.Calendar("calDOB","calDOBContainer", { title:"Choose a date:", close:true,navigator:true } );
        calDOB.selectEvent.subscribe(DOBcalendarSelectHandler,calDOB,true);
        calDOB.hideEvent.subscribe(function(){validateInputField(document.getElementById('birthDay'));},calDOB,true);
     	calDOB.render();
        YAHOO.util.Event.addListener("birthDay", "focus", calDOB.show, calDOB, true);
        </c:if>
        </authz:authorize>
        <c:choose>
        <c:when test="${empty employee.id}">
        var calHireDate = new YAHOO.widget.Calendar("hireDate","calHireDateContainer", { title:"Choose a date:", close:true, navigator:true} );
        calHireDate.selectEvent.subscribe(hireDatecalendarSelectHandler,calHireDate,true);
        calHireDate.hideEvent.subscribe(function(){validateInputField(document.getElementById('hireDate'));},calHireDate,true);
     	calHireDate.render();
        YAHOO.util.Event.addListener("hireDate", "focus", calHireDate.show, calHireDate, true);
        </c:when>
        <c:otherwise>
        <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
        var calHireDate = new YAHOO.widget.Calendar("hireDate","calHireDateContainer", { title:"Choose a date:", close:true, navigator:true} );
        calHireDate.selectEvent.subscribe(hireDatecalendarSelectHandler,calHireDate,true);
        calHireDate.hideEvent.subscribe(function(){validateInputField(document.getElementById('hireDate'));},calHireDate,true);
     	calHireDate.render();
        YAHOO.util.Event.addListener("hireDate", "focus", calHireDate.show, calHireDate, true);
        </authz:authorize>
        </c:otherwise>
        </c:choose>
      }

    function hireDatecalendarSelectHandler(type,args,obj) {
    var selected=args[0];
    var selectedDate=this._toDate(selected[0]);
    var year=selectedDate.getFullYear().toString();
    var month=(selectedDate.getMonth()+1).toString();
    var date=selectedDate.getDate().toString();
    if(month.length ==1)
    month='0'+month;
    if(date.length ==1)
    date='0'+date;
    document.getElementById('hireDate').value=(month+'/'+date+'/'+year);
    this.hide();
	}

    function DOBcalendarSelectHandler(type,args,obj) {
    var selected=args[0];
    var selectedDate=this._toDate(selected[0]);
    var year=selectedDate.getFullYear().toString();
    var month=(selectedDate.getMonth()+1).toString();
    var date=selectedDate.getDate().toString();
    if(month.length ==1)
    month='0'+month;
    if(date.length ==1)
    date='0'+date;
    document.getElementById('birthDay').value=(month+'/'+date+'/'+year);
    this.hide();
     validateInputField(document.getElementById('birthDay'));
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
    <style type="text/css"><!--
	.newEmployeeForm {
  	width: 600px;
  	float: left;
	}

    .newEmployeeForm .column1 {
  	float: left;
  	margin-right: 10px;
  	width: 165px;
  	clear: both;
    height: 60px;
	}

    .newEmployeeForm .column2 {
  	float: left;
  	margin-right: 10px;
  	width: 165px;
  	height: 60px;
	}

	.newEmployeeForm .column3 {
	float: left;
  	margin-right: 10px;
  	width: 165px;
  	height: 60px;
	}
	.newEmployeeForm p {
  	padding: 3px 0;
  	margin: 0;
	}

	.newEmployeeForm .textInput {
  	width: 150px;
	}

	.newEmployeeForm .textArea {
 	width: 300px;
  	height: 150px;
	}

	--></style>
  </head>
  <body onload="init()" class="yui-skin-sam">
    <pizza73:adminNav />
    <div id="page">
     <pizza73:adminTabs />
      <div id="pageContent">
        <div id="contentColumn">
          <form:form id="employeeForm" commandName="employee">
            <form:errors path="*" cssClass="checkoutError" />
            <div class="newEmployeeForm">
              <h1>
              <c:choose>
                <c:when test="${not empty employee and not empty employee.id}">
                <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                Edit
                </authz:authorize>
                <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                View
                </authz:authorize>
                </c:when>
                <c:otherwise>Add </c:otherwise>
              </c:choose>
              Employee Information</h1>
              <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME,ROLE_SHOP_OWNER,ROLE_DSM">
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <div class="column1">
                    <p>
                    <label for="payrollId">ADP Id</label>
                    <form:input path="payrollId" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="10" onblur="validateInputField(this)"/>
                    <span id="payrollId.error" class="error"></span>
                    </p>
                    </div>
                    <div class="column2">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
                	<div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
                    </authz:authorize>
                    <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_DSM">
                    <c:if test="${not empty employee.id}">
                    <div class="column1">
                    <p>
                    <label for="payrollId">ADP Id</label>
                    <form:input path="payrollId" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" tabindex="1" maxlength="10" onfocus="this.blur();" readonly="true" />
                    <span id="payrollId.error" class="error"></span>
                    </p>
                    </div>
                    <div class="column2">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
                	<div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
                    </c:if>
                    </authz:authorize>
              </authz:authorize>
              <div class="column1">
                <p>
                  <label for="name">First Name</label>
                  <c:choose>
                  <c:when test="${empty employee.id}">
                  <form:input path="name" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                  </c:when>
                  <c:otherwise>
                  <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                  <form:input path="name" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                  </authz:authorize>
                  <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                  <form:input path="name" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="20" readonly="true"/>
                  </authz:authorize>
                  </c:otherwise>
                  </c:choose>
                  <span id="name.error" class="error"></span>
                </p>
              </div>
              <div class="column2">
                <p>
                  <label for="middleInitial">Middle Initial</label>
                  <c:choose>
                  <c:when test="${empty employee.id}">
                  <form:input path="middleInitial" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="1" onblur="validateInputField(this)"/>
                  </c:when>
                  <c:otherwise>
                  <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                  <form:input path="middleInitial" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="1" onblur="validateInputField(this)"/>
                  </authz:authorize>
                  <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                  <form:input path="middleInitial" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="1" readonly="true"/>
                  </authz:authorize>
                  </c:otherwise>
                  </c:choose>
                  <span id="middleInitial.error" class="error"></span>
                </p>
              </div>
              <div class="column3">
                <p>
                  <label for="lastName">Last Name</label>
                  <c:choose>
                  <c:when test="${empty employee.id}">
                  <form:input path="lastName" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                  </c:when>
                  <c:otherwise>
                  <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                  <form:input path="lastName" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                  </authz:authorize>
                  <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                  <form:input path="lastName" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="20" readonly="true"/>
                  </authz:authorize>
                  </c:otherwise>
                  </c:choose>
                  <span id="lastName.error" class="error"></span>
                </p>
              </div>
              <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME,ROLE_SHOP_OWNER,ROLE_DSM">
              <spring:nestedPath path="address">
                <div class="column1">
                  <p>
                    <label for="streetAddress">Address</label>
                    <c:choose>
                    <c:when test="${ empty employee.id}">
                    <form:input  path="streetAddress" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="50" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input  path="streetAddress" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="50" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input  path="streetAddress" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="50" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="address.streetAddress.error" class="error"></span>
                  </p>
                </div>
                </spring:nestedPath>

                <div class="column2">
                  <p>
                    <label for="phone">Phone Number(ex:7804737373)</label>
                    <c:choose>
                    <c:when test="${ empty employee.id}">
                    <form:input  path="phone" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input  path="phone" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input  path="phone" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="20" readonly="true" />
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="phone.error" class="error"></span>
                  </p>
                </div>
               <div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
                <spring:nestedPath path="address">
                <div class="column1">
                  <p>
                    <label for="suiteNumber">Suite Number</label>
                    <c:choose>
                    <c:when test="${ empty employee.id}">
                    <form:input path="suiteNumber" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="50" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="suiteNumber" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="50" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="suiteNumber" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="50" readonly="true" />
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="address.suiteNumber.error" class="error"></span>
                  </p>
                </div>
                <div class="column2">
                  <p>
                    <label for="city">City</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:input path="city" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="city" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="20" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="city" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="20" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="address.city.error" class="error"></span>
                  </p>
                </div>
                <div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                </div>
                <div class="column1">
                  <p>
                    <label for="postalCode">Postal Code</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:input path="postalCode" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="6" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="postalCode" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="6" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="postalCode" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="6" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="address.postalCode.error" class="error"></span>
                  </p>
                </div>
                <div class="column2">
                  <p>
                    <label for="province">Province</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:select path="province" items="${PROVINCES}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:select path="province" items="${PROVINCES}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="province" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssClass="textInput" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="address.province.error" class="error"></span>
                  </p>
                </div>
                <div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
              </spring:nestedPath>
              <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER">
              <c:if test="${empty employee.id}">
              <div class="column1">
                  <p>
                    <label for="sin">SIN</label>
                    <form:input path="sin" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="9" onblur="validateInputField(this);validateSIN();"/>
                    <span id="sin.error" class="error"></span>
                  </p>
              </div>
              <div class="column2">
                  <p>
                    <label for="birthDay">Date of Birth</label>
                    <form:input path="birthDay" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="10" readonly="true" />
                    <span id="birthDay.error" class="error"></span>
                    <div id="calDOBContainer" style="display:none;"></div>
                  </p>
                </div>
              <div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
              </c:if>
              </authz:authorize>
              <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
              <div class="column1">
                  <p>
                    <label for="sin">SIN</label>
                    <form:input path="sin" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="9" onblur="validateInputField(this);validateSIN();"/>
                    <span id="sin.error" class="error"></span>
                  </p>
              </div>
              <div class="column2">
                  <p>
                    <label for="birthDay">Date of Birth</label>
                    <form:input path="birthDay" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="10" readonly="true" />
                    <span id="birthDay.error" class="error"></span>
                    <div id="calDOBContainer" style="display:none;"></div>
                  </p>
                </div>
               <div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
                	</div>
              </authz:authorize>
              <div class="column1">
                  <p>
                    <label for="sex">Gender</label>
                    <c:choose>
                    <c:when test="${ empty employee.id}">
                    <form:select path="sex" items="${SEX}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:select path="sex" items="${SEX}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="sex" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="sex.error" class="error"></span>
                  </p>
              </div>
              <div class="column2">
                  <p>
                    <label for="maritalStatus">Marital Status</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:select path="maritalStatus" items="${MARITAL_STATUS}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:select path="maritalStatus" items="${MARITAL_STATUS}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="maritalStatus" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="maritalStatus.error" class="error"></span>
                  </p>
              </div>
              <div class="column3">
                	<p>
                	<label style="height:35px">&nbsp;</label>
                	</p>
              </div>
              <div class="column1">
                  <p>
                    <label for="hireDate">Commence Date</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:input path="hireDate" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="10" readonly="true" />
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="hireDate" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="10" readonly="true" />
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="hireDate" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="10" readonly="true" />
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="hireDate.error" class="error"></span>
                    <div id="calHireDateContainer" style="display:none;"></div>
                  </p>
              </div>
              <div class="column2">
                  <p>
                    <label for="hireDate">Position</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:input path="position" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="40" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="position" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="40" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="position" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" maxlength="40" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="position.error" class="error"></span>
                  </p>
              </div>
              <div class="column3">
                  <p>
                    <label for="typeOfEmployment">Type of Employment</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:select path="typeOfEmployment" items="${TYPE_OF_EMPLOYMENT}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:select path="typeOfEmployment" items="${TYPE_OF_EMPLOYMENT}" cssClass="textInput" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="typeOfEmployment" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssClass="textInput" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="typeOfEmployment.error" class="error"></span>
                  </p>
              </div>
              </authz:authorize>
			  <authz:authorize ifAnyGranted="ROLE_SUPREME, ROLE_SHOP_OWNER">
                <div class="column1">
                  <p>
                    <label for="email">Login id</label>
                    <c:choose>
                    <c:when test="${empty employee.id}">
                    <form:input path="email" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="32" onblur="validateLoginId(this);"/>
                    </c:when>
                    <c:otherwise>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="email" cssClass="textInput" cssErrorClass="textInput checkoutError" maxlength="32" onblur="validateLoginId(this);"/>
                    </authz:authorize>
                    <authz:authorize ifNotGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="email" cssClass="textInput" cssErrorClass="textInput checkoutError" cssStyle="background-color:#eeeeee;border:1px solid grey;" maxlength="32" readonly="true"/>
                    </authz:authorize>
                    </c:otherwise>
                    </c:choose>
                    <span id="email.error" class="error"></span>
                  </p>
                </div>
			</authz:authorize>
              <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME, ROLE_SHOP_OWNER, ROLE_DSM">
                <div class="column1">
                  <p>
                    <c:choose>
                    <c:when test="${employee.salariedEmployee}">
                    <label for="primaryWage">Bi-weekly Salary</label>
                    </c:when>
                    <c:otherwise>
                    <label for="primaryWage">Primary Wage</label>
                    </c:otherwise>
                    </c:choose>
                    <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                    <form:input path="primaryWage" cssClass="textInput" cssErrorClass="textInput checkoutError" onblur="validateInputField(this)"/>
                    </authz:authorize>
                    <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_DSM">
                    <c:choose>
                    <c:when test="${not empty employee and not empty employee.id}">
                    <form:input path="primaryWage" cssClass="textInput" cssStyle="background-color:#eeeeee;border:1px solid grey;" cssErrorClass="textInput checkoutError" readonly="true" onfocus="this.blur();" />
                    </c:when>
                    <c:otherwise>
                    <form:input path="primaryWage" cssClass="textInput" cssErrorClass="textInput checkoutError" onblur="validateInputField(this)"/>
                    </c:otherwise>
                    </c:choose>
                    </authz:authorize>
                    <span id="primaryWage.error" class="error"></span>

                  </p>
                </div>
              </authz:authorize>
              <form:hidden path="newEmployee" />
                <c:choose>
                  <c:when test="${not empty employee and not empty employee.id}">
                  <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
                  <div class="column1">
                  <p>
                  <input type="submit" value="Edit"  onclick="return validateValues();" />
                  <input type="submit" name="CANCEL_ACTION" value="Cancel" />
                  </p>
                  </div>
                  </authz:authorize>
                  </c:when>
                  <c:otherwise>
                  <div class="column1">
                  <p>
                  <input type="submit" value="Save" onclick="return validateValues();"/>
                  <input type="submit" name="CANCEL_ACTION" value="Cancel" />
                  </p>
                  </div>
                  </c:otherwise>
                 </c:choose>
              <authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME, ROLE_SHOP_OWNER,ROLE_DSM">
              <c:choose>
              <c:when test="${not empty employee and not empty employee.id}">
              <div style="clear:both"><h3>NOTE: A change in payroll status form must be filled out and sent to head office in order to change employee information.</h3></div>
              </c:when>
              <c:otherwise>
              <div style="clear:both;color:blue;font-weight:bold;"><h3>NOTE: Payrolls for new employees are only available after head office approve printed new employee forms.Before that, the new employees will NOT show in the paryoll page.</h3></div>
              </c:otherwise>
              </c:choose>
              </authz:authorize>
            </div>
          </form:form>
        </div>
      </div>
      <pizza73:adminFooter />
    </div>
  </body>
</html>

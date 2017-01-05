<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp" %>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
  <title>Pizza 73 Partners: Current Payroll</title>
  <pizza73:adminHead/>
  <script type='text/javascript' src="<c:url value="dwr/engine.js" />"></script>
  <script type='text/javascript' src="<c:url value="dwr/util.js" />"></script>
  <script type='text/javascript' src="<c:url value="dwr/interface/AjaxManager.js" />"></script>
  <script type='text/javascript' src='./dojo/1.0.2/20080220/dojo.js'></script>
  <script type='text/javascript' src='./dojo/1.0.2/20080220/pizza73_dojo.js'></script>
  <link rel="stylesheet" type="text/css" media="screen" href="./css/payroll.css"/>
  <link rel="stylesheet" type="text/css" media="print" href="./css/payrollPrint.css"/>

  <<!--CSS file (default YUI Sam Skin) -->
  <link type="text/css" rel="stylesheet" href="./yui-partners/2.5.2/autocomplete/assets/skins/sam/autocomplete.css">

  <!-- Dependencies -->
  <script type="text/javascript" src="./yui-partners/2.5.2/yahoo-dom-event/yahoo-dom-event.js"></script>

  <!-- OPTIONAL: Get (required only if using Script Node DataSource) -->
  <script type="text/javascript" src="./yui-partners/2.5.2/get/get-min.js"></script>

  <!-- Source file -->
  <script type="text/javascript" src="./yui-partners/2.5.2/autocomplete/autocomplete-min.js"></script>
  <c:set var="STAT_HOLIDAY" value="${PAYROLL_PERIOD eq 7}"/>
  <script language="JavaScript">
    var statHoliday = ${STAT_HOLIDAY},
      message = null,
        statWeek = 'weekTwoStat',
        startStatDay = 'weekTwoFriStart',
        endStatDay = 'weekTwoFriEnd';

    function updateWeekOneHours(element) {
      message = validateInputField(element);
      if (message.length > 0) {
        dojo.query("#" + element.id).addClass('checkoutError');
      }
      else {
        dojo.query("#" + element.id).removeClass('checkoutError');
        updateHours(element.id, "One");
      }
    }

    function updateWeekTwoHours(element) {
      var message = validateInputField(element);
      if (message.length > 0) {
        dojo.query("#" + element.id).addClass('checkoutError');
      }
      else {
        dojo.query("#" + element.id).removeClass('checkoutError');
        updateHours(element.id, "Two");
      }
    }

    function validateInputField(element) {
      var regex = /^\d+(\.\d+)?$/;
      var message = '';
      if (!regex.test(element.value)) {
        message = '*';
      }
      return message;
    }

    function updateHours(elementId, week) {
      var index = elementId.indexOf('s');
      var end = elementId.indexOf('.current');

      var empIndex = elementId.substring(index + 1, end);
      var empid = document.getElementById('empId:' + empIndex);

      var days = new Array('Sun', 'Mon', 'Tues', 'Wed', 'Thurs', 'Fri', 'Sat', 'Stat');
      var hoursArray = new Array();

      var tempVal = null;

      for (i = 0; i < days.length; i++) {
        var day = document.getElementById('employees' + empIndex + '.currentPayroll.week' + week + days[i]);
        if (day.value == '' || day.value == null)
          tempVal = '0';
        else
          tempVal = day.value;
        var hours = (+tempVal);
        hoursArray[i] = tempVal;
      }

      AjaxManager.calculateHours(hoursArray, empid.value, {
        callback: function (data) {
          updateData(data, empIndex, week);
        }, async: false
      });
    }

    function updateData(data, empIndex, week) {
      var empid = document.getElementById('empId:' + empIndex);
      var totalRegHours = dwr.util.getValue('totalRegHoursTop');
      var totalOTHours = dwr.util.getValue('totalOTHoursTop');
      var totalStatHours = dwr.util.getValue('totalStatHoursTop');

      var totalEmpRegHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.totalRegHours');
      var totalEmpOTHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.totalOTHours');
      var totalEmpStatHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.totalStatHours');

      var totalWeekOneHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.totalHoursWeekOne');
      var totalWeekOneOTHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.overtimeHoursWeekOne');
      var totalWeekOneStatHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.weekOneStat');

      var totalWeekTwoHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.totalHoursWeekTwo');
      var totalWeekTwoOTHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.overtimeHoursWeekTwo');
      var totalWeekTwoStatHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.weekTwoStat');

      var subtractRegHours = 0;
      var subtractOTHours = 0;
      if (week == 'One') {
        subtractRegHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.totalHoursWeekOne');
        subtractOTHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.overtimeHoursWeekOne');
      }
      else {
        subtractRegHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.totalHoursWeekTwo');
        subtractOTHours = dwr.util.getValue('employees' + empIndex + '.currentPayroll.overtimeHoursWeekTwo');
      }
      dwr.util.setValue('employees' + empIndex + '.currentPayroll.totalHoursWeek' + week, data[0]);
      dwr.util.setValue('employees' + empIndex + '.currentPayroll.overtimeHoursWeek' + week, data[1]);

      totalEmpRegHours = eval(totalEmpRegHours - subtractRegHours + data[0]);
      totalEmpOTHours = eval(totalEmpOTHours - subtractOTHours + data[1]);
      /*          totalWeekOneStatHours = eval(totalWeekOneStatHours - subtractStatHours + data[1]); */
      var totalEmpStatHoursAfter = eval((+totalWeekOneStatHours) + (+totalWeekTwoStatHours));

      totalRegHours = eval(totalRegHours - subtractRegHours + data[0]);
      totalOTHours = eval(totalOTHours - subtractOTHours + data[1]);
      var temp = eval((+totalEmpStatHoursAfter) - (+totalEmpStatHours));
      totalStatHours = eval((+totalStatHours) + temp);

      dwr.util.setValue('employees' + empIndex + '.currentPayroll.totalRegHours', totalEmpRegHours);
      dwr.util.setValue('employees' + empIndex + '.currentPayroll.totalOTHours', totalEmpOTHours);
      dwr.util.setValue('employees' + empIndex + '.currentPayroll.totalStatHours', totalEmpStatHoursAfter);

      dwr.util.setValue('totalRegHoursTop', totalRegHours);
      dwr.util.setValue('totalOTHoursTop', totalOTHours);
      dwr.util.setValue('totalStatHoursTop', totalStatHours);


      dwr.util.setValue('totalRegHoursBottom', totalRegHours);
      dwr.util.setValue('totalOTHoursBottom', totalOTHours);
      dwr.util.setValue('totalStatHoursBottom', totalStatHours);

      //labour cost
      if (document.getElementById("totalLabourCostTop") != null) {
        //update employee's labour cost
        var subtractLabourCost = dwr.util.getValue('empLabourCostWeek' + week + ':' + empid.value);
        dwr.util.setValue('empLabourCostWeek' + week + ':' + empid.value, data[3]);
        //update total labour cost
        var totalLabourCost = dwr.util.getValue('totalLabourCostTop');
        totalLabourCost = totalLabourCost - subtractLabourCost + data[3];
        dwr.util.setValue('totalLabourCostTop', totalLabourCost);
        dwr.util.setValue('totalLabourCostBottom', totalLabourCost);
      }
    }

    function dailyHoursCalculate(node) {
      var startTime = "";
      var endTime = "";
      var temp = "";
      var startNode = null;
      var endNode = null;
      var sumNode = null;
      var statNode = null;
      if (node.id.indexOf('Start') != -1) {
        startTime = node.value;
        temp = node.id;
        startNode = node;
        endNode = document.getElementById(temp.replace("Start", "End"));
        endTime = endNode.value;
        sumNode = document.getElementById(node.id.substr(0, node.id.length - 5));
        <c:if test="${STAT_HOLIDAY}">
          if (temp.indexOf(startStatDay) >= 0) {
            statNode = document.getElementById(node.id.replace(/weekTwoFriStart/, statWeek));
          }
//          else if (temp.indexOf('weekTwoFriStart') >= 0) {
//            statNode = document.getElementById(node.id.replace(/weekTwoFriStart/, 'weekTwoStat'));
//          }
        </c:if>
        if ((/^\d{1,2}[:]\d{2}[A-P][M]$/).test(startTime))
          startNode.className = "payrollInput";
        else
          startNode.className = "payrollInput checkoutError";
        if ((/^\d{1,2}[:]\d{2}[A-P][M]$/).test(endTime))
          endNode.className = "payrollInput";
        else
          endNode.className = "payrollInput checkoutError";
      }
      else {
        endTime = node.value;
        temp = node.id;
        startNode = document.getElementById(temp.replace("End", "Start"));
        endNode = node;
        startTime = startNode.value;
        sumNode = document.getElementById(node.id.substr(0, node.id.length - 3));
        <c:if test="${STAT_HOLIDAY}">
          if (temp.indexOf(endStatDay) >= 0) {
            statNode = document.getElementById(node.id.replace(/weekTwoFriEnd/, statWeek));
          }
//          else if (temp.indexOf('weekTwoFriEnd') >= 0) {
//            statNode = document.getElementById(node.id.replace(/weekTwoFriEnd/, 'weekTwoStat'));
//          }
        </c:if>
        if ((/^\d{1,2}[:]\d{2}[A-P][M]$/).test(startTime))
          startNode.className = "payrollInput";
        else
          startNode.className = "payrollInput checkoutError";
        if ((/^\d{1,2}[:]\d{2}[A-P][M]$/).test(endTime))
          endNode.className = "payrollInput";
        else
          endNode.className = "payrollInput checkoutError";
      }
      if ((/^\d{1,2}[:]\d{2}[A-P][M]$/).test(startTime) && (/^\d{1,2}[:]\d{2}[A-P][M]$/).test(endTime)) {
        var sumStatNode=sumNode;
        <c:if test="${STAT_HOLIDAY}">
          var isStatDay = (temp.indexOf(endStatDay) >= 0 || temp.indexOf(startStatDay) >= 0);
            //|| (temp.indexOf('weekTwoFriEnd') >= 0 || temp.indexOf('weekTwoFriStart') >= 0);
          if (isStatDay) {
            sumStatNode = statNode;
          }
        </c:if>
        updateSumNode(startTime, endTime, sumStatNode);
        if (statNode != null) {
          if (statNode.value != 0) {
          sumNode.value = '0.0';
          sumNode.onchange();
          }
        }
      }
      else if (dojo.trim(startTime) == '' || dojo.trim(endTime) == '') {
        if (sumNode.value != 0) {
          sumNode.value = '0.0';
          sumNode.onchange();
        }
        if (statNode != null) {
          statNode.value = '0.0';
          statNode.onchange();
        }

      }

      if (dojo.trim(startTime) == '' && dojo.trim(endTime) == '') {
        startNode.className = "payrollInput";
        endNode.className = "payrollInput";
      }
    }

    function updateSumNode(startTime, endTime, sumNode) {
      var temp = (calQuarters(endTime) - calQuarters(startTime)) / 4.0;
      if (temp < 0)
        temp = temp + 24;
      if (sumNode.value != temp) {
        sumNode.value = temp;
        sumNode.onchange();
      }
    }

    function calQuarters(timeTag) {
      var index = timeTag.indexOf(':');
      if (index == 2) {
        var hour = parseInt(timeTag.substr(0, 2));
        var min = parseInt(timeTag.substr(3, 2));
      }
      else {
        var hour = parseInt(timeTag.substr(0, 1));
        var min = parseInt(timeTag.substr(2, 2));
      }
      if (timeTag.indexOf('PM') > 0 && hour < 12)
        return (hour + 12) * 4 + min / 15;
      else if (timeTag.indexOf('AM') > 0 && hour == 12)
        return  min / 15;
      else
        return hour * 4 + min / 15;
    }

    function hasError() {
      var errorFlag = (dojo.query(".checkoutError").length == 0);
      //has error
      if (errorFlag == false)
        alert("please correct the invalid field(s) before save.");
      return errorFlag;
    }

    // A JavaScript Array DataSource
    var myArray1 = [
      "1:00PM", "1:15PM", "1:30PM", "1:45PM",
      "2:00PM", "2:15PM", "2:30PM", "2:45PM",
      "3:00PM", "3:15PM", "3:30PM", "3:45PM",
      "4:00PM", "4:15PM", "4:30PM", "4:45PM",
      "5:00PM", "5:15PM", "5:30PM", "5:45PM",
      "6:00PM", "6:15PM", "6:30PM", "6:45PM",
      "7:00PM", "7:15PM", "7:30PM", "7:45PM",
      "8:00PM", "8:15PM", "8:30PM", "8:45PM",
      "9:00PM", "9:15PM", "9:30PM", "9:45PM",
      "1:00AM", "1:15AM", "1:30AM", "1:45AM",
      "2:00AM", "2:15AM", "2:30AM", "2:45AM",
      "3:00AM", "3:15AM", "3:30AM", "3:45AM",
      "4:00AM", "4:15AM", "4:30AM", "4:45AM",
      "5:00AM", "5:15AM", "5:30AM", "5:45AM",
      "6:00AM", "6:15AM", "6:30AM", "6:45AM",
      "7:00AM", "7:15AM", "7:30AM", "7:45AM",
      "8:00AM", "8:15AM", "8:30AM", "8:45AM",
      "9:00AM", "9:15AM", "9:30AM", "9:45AM",
      "10:00PM", "10:15PM", "10:30PM", "10:45PM",
      "10:00AM", "10:15AM", "10:30AM", "10:45AM",
      "11:00PM", "11:15PM", "11:30PM", "11:45PM",
      "11:00AM", "11:15AM", "11:30AM", "11:45AM",
      "12:00PM", "12:15PM", "12:30PM", "12:45PM",
      "12:00AM", "12:15AM", "12:30AM", "12:45AM"
    ];
    var myDataSource = new YAHOO.widget.DS_JSArray(myArray1);

    function goToNext(node) {
      navigationDown(node);
    }

    function navigationRegistor(formNode) {
      var inputNodes = dojo.query("input", formNode);
      var i = 0;
      for (i = 0; i < inputNodes.length; i++)
        if (inputNodes[i].type != 'submit')
          dojo.connect(inputNodes[i], 'onkeypress', navigationProcess);
        else
          dojo.connect(inputNodes[i], 'onkeypress', navigationSubmit);
    }

    function navigationSubmit(event) {
      key = event.keyCode;
      if (key == dojo.keys.PAGE_DOWN) {
        navigationDown(event.target);
        dojo.stopEvent(event);
      }
      else if (key == dojo.keys.PAGE_UP) {
        navigationUp(event.target);
        dojo.stopEvent(event);
      }
      return true;
    }

    function navigationProcess(event) {
      key = event.keyCode;
      if (key == dojo.keys.PAGE_DOWN || key == dojo.keys.NUMPAD_ENTER || key == dojo.keys.ENTER) {
        navigationDown(event.target);
        dojo.stopEvent(event);
      }
      else if (key == dojo.keys.PAGE_UP) {
        navigationUp(event.target);
        dojo.stopEvent(event);
      }
      else if (key == dojo.keys.TAB) {

      }
      else {
        autoCompleteSetup(event.target);
      }
      return true;
    }

    function navigationDown(node) {
      var form = dojo.byId('payrollForm');
      var index = dojo.indexOf(form, node);
      index++;
      while (index < form.length && (form[index].type == 'hidden' || (form[index].type == 'text' && form[index].readOnly)))
        index++;
      if (index < form.length) {
        form[index].focus();
        form[index].select();
      }
    }

    function navigationUp(node) {
      var form = dojo.byId('payrollForm');
      var index = dojo.indexOf(form, node);
      index--;
      while (index < form.length && (form[index].type == 'hidden' || (form[index].type == 'text' && form[index].readOnly)))
        index--;
      if (index >= 0) {
        form[index].focus();
        form[index].select();
      }
    }

    var myAutoComp = null;

    function autoCompleteSetup(node) {
      if (dwr.util.getValue('focusId') != node.id) {
        if (myAutoComp != null) {
          myAutoComp.destroy();
        }
        myAutoComp = null;
        pNode = node.parentNode;
        ppNode = pNode.parentNode;
        ppNodeId = ppNode.id;
        subString1 = ppNodeId.substring(0, 3);
        subString2 = ppNodeId.substring(9);
        container = subString1 + subString2;
        myAutoComp = new YAHOO.widget.AutoComplete(node.id, container, myDataSource);
        myAutoComp.maxResultsDisplayed = 40;
        dwr.util.setValue('focusId', node.id);
      }
    }

    function loadPage() {
      navigationRegistor(document.getElementById("payrollForm"));
    }

    dojo.addOnLoad(loadPage);

  </script>


</head>
<body class="yui-skin-sam">
<pizza73:adminNav/>
<div id="payrollPage">
  <pizza73:adminTabs/>
  <div id="payrollPageContent">
    ${STAT_HOLIDAY}
    <form:form id="payrollForm" commandName="shopPayroll">
      <form:errors path="*" cssClass="checkoutError"/>
      <div style="display:none" id="focusId"></div>
      <div id="payroll">
        <div id="contentColumn">
          <h1>Shop ${shopPayroll.shop.name} Payroll Information for Pay Period ${PAYROLL_PERIOD} (${PAYROLL_YEAR})</h1>

          <h1>Payroll Period Ending: ${PAYROLL_END_DATE}</h1>

          <h2 class="payrollSummary">Total Regular Hours<span class="summarySeperator">|</span>

            <p id="totalRegHoursTop">${shopPayroll.totalRegHours}</p></h2>
          <h2 class="payrollSummary">Total OT Hours<span class="summarySeperator">|</span>

            <p id="totalOTHoursTop">${shopPayroll.totalOTHours}</p></h2>
          <h2 class="payrollSummary">Total Stat Hours<span class="summarySeperator">|</span>

            <p id="totalStatHoursTop">${shopPayroll.totalStatHours}</p></h2>
          <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_PAYROLL,ROLE_SHOP_MGR">
            <h2 class="payrollSummary">Total Labour Cost<p>$<span id="totalLabourCostTop">${TOTAL_LABOUR_COST}</span></p></h2>
          </authz:authorize>
        </div>
        <div class="column1">
          <p>
            <input type="submit" name="action" value="Save/Edit" onclick="return hasError();"/>
            <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME">
              <input type="submit" name="CANCEL_ACTION" value="Cancel"/>
              <input type="submit" name="action" value="Save and Submit" onclick="return hasError();"/>
            </authz:authorize>
            <authz:authorize ifAnyGranted="ROLE_SHOP_MGR, ROLE_SHOP_OWNER, ROLE_TIMESHEET">
              <input type="submit" name="CANCEL_ACTION" value="Cancel"/>
              <input type="submit" name="action" value="Send To Office" onclick="return hasError();"/>
            </authz:authorize>
          </p>
          <br/>
        </div>
        <c:forEach items="${shopPayroll.employees}" var="emp" varStatus="empIndex">
          <c:set var="payroll" value="${emp.currentPayroll}"/>
          <input type="hidden" id="empId:${empIndex.count-1}" value="${emp.id}"/>

          <div
              <c:if test = "${empIndex.count % 2 == 1}">class="payrollZebra"</c:if> style="clear:both">
            <b>${emp.name} ${emp.lastName} (${emp.payrollId})</b>
            <table class="payrollTable" cellspacing="0">
              <thead <c:if test = "${empIndex.count > 1 }">class="nonFirstRecord"</c:if>>
              <tr>
                <th>&nbsp;</th>
                <th colspan="2">
                  <div class="payrollHeaderDay">Sun</div>
                </th>
                <th colspan="2">
                  <div class="payrollHeaderDay">Mon</div>
                </th>
                <th colspan="2">
                  <div class="payrollHeaderDay">Tues</div>
                </th>
                <th colspan="2">
                  <div class="payrollHeaderDay">Wed</div>
                </th>
                <th colspan="2">
                  <div class="payrollHeaderDay">Thurs</div>
                </th>
                <th colspan="2">
                  <div class="payrollHeaderDay">Fri</div>
                </th>
                <th colspan="2">
                  <div class="payrollHeaderDay">Sat</div>
                </th>
                <th style="text-align:center">&nbsp;</th>
              </tr>
              <tr>
                <th>&nbsp;</th>
                <th class="payrollHeaderStartEndTime">Start</th>
                <th class="payrollHeaderStartEndTime">End</th>
                <th class="payrollHeaderStartEndTime">Start</th>
                <th class="payrollHeaderStartEndTime">End</th>
                <th class="payrollHeaderStartEndTime">Start</th>
                <th class="payrollHeaderStartEndTime">End</th>
                <th class="payrollHeaderStartEndTime">Start</th>
                <th class="payrollHeaderStartEndTime">End</th>
                <th class="payrollHeaderStartEndTime">Start</th>
                <th class="payrollHeaderStartEndTime">End</th>
                <th class="payrollHeaderStartEndTime">Start</th>
                <th class="payrollHeaderStartEndTime">End</th>
                <th class="payrollHeaderStartEndTime">Start</th>
                <th class="payrollHeaderStartEndTime">End</th>
                <th class="payrollHeaderTotal">StatPay (week)</th>
                <th class="payrollHeaderTotal">Reg (week)</th>
                <th class="payrollHeaderTotal">OT (week)</th>
                <th class="payrollHeaderTotal">Reg (total)</th>
                <th class="payrollHeaderTotal">OT<br/>(total)</th>
                <th class="payrollHeaderTotal">StatPay (total)</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td rowspan="2" style="text-align:center;padding:2px;" class="payrollInputWeekNum">Week One</td>
                <td id="empColumnSunWeekOneStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSunStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSunWeekOneStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnSunWeekOneEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSunEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSunWeekOneEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnMonWeekOneStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneMonStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative;"/>
                    <div id="empMonWeekOneStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnMonWeekOneEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneMonEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empMonWeekOneEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnTuesWeekOneStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneTuesStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empTuesWeekOneStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnTuesWeekOneEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneTuesEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empTuesWeekOneEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnWedWeekOneStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneWedStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empWedWeekOneStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnWedWeekOneEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneWedEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empWedWeekOneEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnThursWeekOneStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneThursStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empThursWeekOneStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnThursWeekOneEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneThursEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empThursWeekOneEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnFriWeekOneStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneFriStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empFriWeekOneStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnFriWeekOneEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneFriEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empFriWeekOneEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnSatWeekOneStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSatStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSatWeekOneStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnSatWeekOneEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2+1}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSatEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSatWeekOneEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnStatWeekOne:${emp.id}" rowspan="2" style="vertical-align:top" class="payrollInputCell">
                  <c:choose>
                    <c:when test="${PAYROLL_PERIOD eq 59}">
                      <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneStat" maxlength="5" onchange="updateWeekOneHours(this)" readonly="true"
                                  cssStyle="background-color:pink" cssClass="payrollInputWeeklySummary"/>
                    </c:when>
                    <c:otherwise>
                      <form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneStat" maxlength="5" onchange="updateWeekOneHours(this)" readonly="true"
                                  cssClass="payrollInputWeeklySummary"/>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
                  <form:input path="employees[${empIndex.count-1}].currentPayroll.totalHoursWeekOne" onfocus="goToNext(this);" readonly="true"
                              cssClass="payrollInputWeeklySummary"/>
                </td>
                <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
                  <form:input path="employees[${empIndex.count-1}].currentPayroll.overtimeHoursWeekOne" readonly="true" onfocus="goToNext(this);"
                              cssClass="payrollInputWeeklySummary"/>
                  <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_PAYROLL,ROLE_SHOP_MGR">
                    <c:set var="labourCostWeekOneId" value="EMPLOYEES_LABOUR_COSTS_${emp.id*2}"/>
                    <input type="hidden" id="empLabourCostWeekOne:${emp.id}" name="empLabourCostWeekOne:${emp.id}"
                           value="${EMPLOYEES_LABOUR_COSTS[labourCostWeekOneId] }"/>
                  </authz:authorize>
                </td>
                <td rowspan="4" class="payrollInputCell">
                  <form:input path="employees[${empIndex.count-1}].currentPayroll.totalRegHours" onfocus="goToNext(this);" readonly="true"
                              cssClass="payrollInputTotalSummary"/>
                </td>
                <td rowspan="4" class="payrollInputCell">
                  <form:input path="employees[${empIndex.count-1}].currentPayroll.totalOTHours" onfocus="goToNext(this);" readonly="true"
                              cssClass="payrollInputTotalSummary"/>
                </td>
                <td rowspan="4" class="payrollInputCell">
                  <form:input path="employees[${empIndex.count-1}].currentPayroll.totalStatHours" onfocus="goToNext(this);" readonly="true"
                              cssClass="payrollInputTotalSummary"/>
                </td>
              </tr>
              <tr>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 240}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSun" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSun" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 5}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneMon" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneMon" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 100}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneTues" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneTues" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                <c:when test="${PAYROLL_PERIOD eq 100}">
                <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneWed" maxlength="5"
                                                                         onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                         cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneWed" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)" cssClass="payrollInputHour"
                                                                             readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneThurs" maxlength="5"
                                                                         onfocus="goToNext(this);" onchange="updateWeekOneHours(this)" cssClass="payrollInputHour"
                                                                         readonly="true"/></td>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 100}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneFri" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneFri" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 100}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSat" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekOneSat" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekOneHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
              </tr>
              <tr>
                <td rowspan="2" style="text-align:center;padding:2px;" class="payrollInputWeekNum">Week Two</td>
                <td id="empColumnSunWeekTwoStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoSunStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSunWeekTwoStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnSunWeekTwoEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoSunEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSunWeekTwoEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnMonWeekTwoStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoMonStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empMonWeekTwoStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnMonWeekTwoEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoMonEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empMonWeekTwoEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnTuesWeekTwoStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoTuesStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empTuesWeekTwoStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnTuesWeekTwoEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoTuesEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empTuesWeekTwoEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnWedWeekTwoStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoWedStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empWedWeekTwoStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnWedWeekTwoEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoWedEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empWedWeekTwoEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnThursWeekTwoStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoThursStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empThursWeekTwoStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnThursWeekTwoEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoThursEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empThursWeekTwoEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnFriWeekTwoStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoFriStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empFriWeekTwoStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnFriWeekTwoEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoFriEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empFriWeekTwoEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnSatWeekTwoStart:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoSatStart" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSatWeekTwoStart:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnSatWeekTwoEnd:${emp.id}" class="payrollInputCell">
                  <div style="z-index:${9000-empIndex.count*2}">
                    <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoSatEnd" maxlength="7" onblur="dailyHoursCalculate(this)"
                                cssClass="payrollInput" cssStyle="width:50px;position:relative"/>
                    <div id="empSatWeekTwoEnd:${emp.id}" style="color:black;display:block;position:absolute;left:0px;"></div>
                  </div>
                </td>
                <td id="empColumnStatWeekTwo:${emp.id}" rowspan="2" style="vertical-align:top" class="payrollInputCell">
                  <c:choose>
                    <c:when test="${PAYROLL_PERIOD eq 19}">
                      <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoStat" maxlength="5" onchange="updateWeekTwoHours(this)" readonly="true"
                                  cssStyle="background-color:pink" cssClass="payrollInputWeeklySummary"/>
                    </c:when>
                    <c:otherwise>
                      <form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoStat" maxlength="5" onchange="updateWeekTwoHours(this)" readonly="true"
                                  cssClass="payrollInputWeeklySummary"/>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
                  <form:input path="employees[${empIndex.count-1}].currentPayroll.totalHoursWeekTwo" onfocus="goToNext(this);" readonly="true"
                              cssClass="payrollInputWeeklySummary"/>
                </td>
                <td rowspan="2" style="vertical-align:top" class="payrollInputCell">
                  <form:input path="employees[${empIndex.count-1}].currentPayroll.overtimeHoursWeekTwo" onfocus="goToNext(this);" readonly="true"
                              cssClass="payrollInputWeeklySummary"/>
                  <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_PAYROLL,ROLE_SHOP_MGR">
                    <c:set var="labourCostWeekTwoId" value="EMPLOYEES_LABOUR_COSTS_${emp.id*2+1}"/>
                    <input type="hidden" id="empLabourCostWeekTwo:${emp.id}" name="empLabourCostWeekTwo:${emp.id}"
                           value="${EMPLOYEES_LABOUR_COSTS[labourCostWeekTwoId]}"/>
                  </authz:authorize>
                </td>
              </tr>
              <tr>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 110}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoSun" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoSun" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 190}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoMon" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoMon" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 140}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoTues" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssClass="payrollInputHour" cssStyle="background-color:pink" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoTues" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>

                  </c:otherwise>
                </c:choose>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 140}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoWed" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)" cssClass="payrollInputHour"
                                                                             cssStyle="background-color:pink" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoWed" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)" cssClass="payrollInputHour"
                                                                             readonly="true"/></td>
                  </c:otherwise>
                </c:choose>

                <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoThurs" maxlength="5"
                                                                         onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)" cssClass="payrollInputHour"
                                                                         readonly="true"/></td>
                <c:choose>
                  <c:when test="${PAYROLL_PERIOD eq 7}">
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoFri" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssStyle='background-color:pink' cssClass="payrollInputHour" readonly="true"/></td>
                  </c:when>
                  <c:otherwise>
                    <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoFri" maxlength="5"
                                                                             onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)"
                                                                             cssClass="payrollInputHour" readonly="true"/></td>
                  </c:otherwise>
                </c:choose>
                <td colspan="2" class="payrollInputHourCell"><form:input path="employees[${empIndex.count-1}].currentPayroll.weekTwoSat" maxlength="5"
                                                                         onfocus="goToNext(this);" onchange="updateWeekTwoHours(this)" cssClass="payrollInputHour"
                                                                         readonly="true"/></td>
              </tr>
              <tr class="payrollEmptyRow">
                <td>&nbsp;</td>
              </tr>
              </tbody>
            </table>
          </div>
          <c:if test="${empIndex.count == 11 ||(empIndex.count>12 && (empIndex.count%12 ==0))}">
            <div class="pageBreakMarker">&nbsp;</div>
          </c:if>
          <c:set var="empCount" value="${empIndex.count-1}"/>
        </c:forEach>
        <div id="contentColumn">
          <div class="column1">
            <h2>Total Regular Hours<p id="totalRegHoursBottom">${shopPayroll.totalRegHours}</p></h2>
            <h2>Total OT Hours<p id="totalOTHoursBottom">${shopPayroll.totalOTHours}</p></h2>
            <h2>Total Stat Hours<p id="totalStatHoursBottom">${shopPayroll.totalStatHours}</p></h2>
            <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_PAYROLL,ROLE_SHOP_MGR">
              <h2>Total Labour Cost<p>$<span id="totalLabourCostBottom">${TOTAL_LABOUR_COST}</span></p></h2>
            </authz:authorize>
          </div>
        </div>
        <div class="column1">
          <p>
            <input type="submit" name="action" value="Save/Edit" tabindex="${empCount * 16 + 17}" onclick="return hasError();"/>
            <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME">
              <input type="submit" name="CANCEL_ACTION" value="Cancel" tabindex="${empCount * 16 + 18}"/>
              <input type="submit" name="action" value="Save and Submit" tabindex="${empCount * 16 + 19}" onclick="return hasError();"/>
            </authz:authorize>
            <authz:authorize ifAnyGranted="ROLE_SHOP_MGR, ROLE_SHOP_OWNER, ROLE_TIMESHEET">
              <input type="submit" name="CANCEL_ACTION" value="Cancel" tabindex="${empCount * 16 + 18}"/>
              <input type="submit" name="action" value="Send To Office" tabindex="${empCount * 16 + 19}" onclick="return hasError();"/>
            </authz:authorize>
          </p>
        </div>
      </div>
    </form:form>
  </div>
</div>
<pizza73:adminFooter/>
</div>
</body>
</html>

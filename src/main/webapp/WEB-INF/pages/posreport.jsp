<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: ${MESSAGE.header}</title>
    <pizza73:adminHead />
<link rel="stylesheet" type="text/css" href="./css/dailySales.css" />
<link rel="stylesheet" type="text/css" media="print" href="./css/posReportPrint.css" />  
<!-- DOJO 1.0.2 IN AOL CDN, BEGIN -->
<!-- for developement only, should be replaced by a cumstom build in production server -->
<script type='text/javascript' src='./dojo/1.0.2/20080220/dojo.js'></script>
<script type='text/javascript' src='./dojo/1.0.2/20080220/pizza73_dojo.js'></script>
<!-- DOJO 1.0.2 IN AOL CDN, END -->

<!--CSS file (default YUI Sam Skin) --> 
    <link rel="stylesheet" type="text/css" href="./yui-partners/2.5.0/calendar/assets/skins/sam/calendar.css" /> 
    <link rel="stylesheet" type="text/css" href="./yui-partners/2.5.0/container/assets/container.css" /> 
    <!-- Dependencies --> 
    <script type="text/javascript" src="./yui-partners/2.5.0/yahoo-dom-event/yahoo-dom-event.js"></script> 
    <!-- Source file --> 
    <script type="text/javascript" src="./yui-partners/2.5.0/calendar/calendar-min.js"></script> 
    <script type="text/javascript" src="./yui-partners/2.5.0/container/container-min.js"></script> 

<script type='text/javascript' src='<c:url value="dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="dwr/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="dwr/interface/AjaxManager.js"/>'></script>
<script language="Javascript">

var globalObj={errors:new Array(),
        warnings:new Array(),
        cancelAction:false,
        status:true,
        waitPanel:null,
        calendarPanel:null            
        }; 

//calendar
function calendarSelectHandler(type,args,obj) { 
var selected=args[0];
var selectedDate=this._toDate(selected[0]);
var year=selectedDate.getFullYear().toString();
var month=(selectedDate.getMonth()+1).toString();
var date=selectedDate.getDate().toString();
if(month.length ==1)
month='0'+month;
if(date.length ==1)
date='0'+date;
this.hide();
window.location.href = '/posreport.html?date='+year+month+date;
} 
  
function popupACalendar(){
	<fmt:formatDate var="selectedDate" value="${POS_REPORT.businessDate.time}" type="date" pattern="yyyyMMdd" />
	selectedDate='${selectedDate}';
	<c:remove var="selectedDate" />
AjaxManager.getPosReportDateRange(function(range){   
   globalObj.calendarPanel= new YAHOO.widget.Calendar('cal2','popupCalendar2',
                                                      {title:'Choose a date:',
                                                       close:true,
                                                       mindate:range[0],
                                                       maxdate:range[1],
                                                       selected:(selectedDate.substr(4,2)+'/'+selectedDate.substr(6,2)+'/'+selectedDate.substr(0,4)),
                                                       pagedate:(selectedDate.substr(4,2)+'/'+selectedDate.substr(0,4))
                                                       });
   
   globalObj.calendarPanel.selectEvent.subscribe(calendarSelectHandler,globalObj.calendarPanel,true);
   globalObj.calendarPanel.render(); 
   YAHOO.util.Event.addListener("calendarImageTag", "click", globalObj.calendarPanel.show, globalObj.calendarPanel, true); 	
   });	
}

	function loadMainPage(){
		popupACalendar();
	}

	dojo.addOnLoad(loadMainPage);	
</script>
  </head>
  <body class="yui-skin-sam">
  <pizza73:adminNav />
    <div id="page">
     <pizza73:adminTabs />
      <div id="pageContent">
      <fmt:formatDate var="selectedDate" value="${POS_REPORT.businessDate.time}" type="date" pattern="yyyy-MM-dd" />
      <div id="dateInfo" style="font-size:16px;font-weight:bold;">Shop ${POS_REPORT.shopId} latest Z report in ${selectedDate} <img class="calendarImage" src="./images/calendar_icon1.png" id="calendarImageTag" onclick="popupACalendar();"></div>
      <c:remove var="selectedDate" />
      <p stlye="padding-topp:10px"><tt style="font-size:12px">
      <c:choose>
      <c:when test="${not empty HTML_CONTENT}">
      ${HTML_CONTENT}
      </c:when>
      <c:otherwise>
      
      Z&nbsp;report:#17331&nbsp;&nbsp;Generated&nbsp;at&nbsp;2013-07-19&nbsp;05:55:02<br/>
Sales&nbsp;Report&nbsp;for&nbsp;shop&nbsp;#98&nbsp;2013-7-18<br/>
Completed&nbsp;Sales<br/>
Computer&nbsp;Sales:&nbsp;&nbsp;&nbsp;&nbsp;3474.01&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;111&nbsp;orders<br/>
Walk-in&nbsp;Sales:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;814.04&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;38&nbsp;orders<br/>
Net&nbsp;to&nbsp;Pizza73:&nbsp;&nbsp;&nbsp;&nbsp;4288.05<br/>
GST:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;229.56&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(0.0535)<br/>
Pizza&nbsp;Card&nbsp;Reload:&nbsp;0.00&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0&nbsp;orders<br/>
Total:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4517.61&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;149&nbsp;orders<br/>
<br/>
Receipts<br/>
Visa:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;87.60&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3&nbsp;receipts<br/>
Mastercard:&nbsp;39.13&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2&nbsp;receipts<br/>
Amex:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;14.69&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;receipts<br/>
Debit:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2846.04&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;42&nbsp;receipts<br/>
Pizzacard:&nbsp;&nbsp;0.00&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0&nbsp;receipts<br/>
Cash:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1530.32&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;49&nbsp;receipts<br/>
Total:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4517.78&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;97&nbsp;receipts<br/>
</c:otherwise>
</c:choose>
</tt>
</p>
      </div>
      <pizza73:adminFooter />
      <div id="popupCalendar2" style="display:none; position:absolute; left: 326px; top: 55px; z-index:100;color:black;"/>
    </div>
    <c:remove var="MESSAGE" scope="session" />
  </body>
</html>

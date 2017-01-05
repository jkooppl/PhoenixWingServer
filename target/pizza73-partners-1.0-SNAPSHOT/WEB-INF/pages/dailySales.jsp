<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<title>Pizza 73 Parnters: Daily/Weekly Sales Report</title>
<pizza73:adminHead />
<link rel="stylesheet" type="text/css" href="./css/dailySales.css" />
<link rel="stylesheet" type="text/css" media="print" href="./css/dailySalesPrint.css" />  
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
<script type='text/javascript' src='<c:url value="js/dailySales8.js"/>'></script>
<script language="Javascript">             	

     function getDailySalesForm(date) {
      var requestUrl="<c:url value="dailySalesForm.html" />?date="+date;
      if(dojo.byId("shopSelector") !=null && !isEmpty(dojo.trim(dojo.byId("shopSelector").value)))
      requestUrl+=("&shopId="+dojo.byId("shopSelector").value);
      dojo.xhrGet( {
        url: requestUrl, 
        handleAs: "text",

        timeout: 120000,

        load: function(response, ioArgs) {
          dojo.byId("reportPortion").innerHTML = response;
          globalObj.populateValidationNodes();
          validate();
          autoUpdate();
          dojo.byId("computerSales").focus();
          dojo.byId("computerSales").select();
          navigationRegistor(dojo.byId('dailySales'));
          getLoadingInfo().hide();
          return response;
        },

        error: function(response, ioArgs) { 
          console.error("HTTP status code: ", ioArgs.xhr.status); 
          return response;
          }
        });
      }      	
      
     function getWeeklySalesForm(date) {
     var requestUrl="<c:url value="weeklySalesForm.html"/>?date="+date;
     if(dojo.byId("shopSelector") !=null && !isEmpty(dojo.trim(dojo.byId("shopSelector").value)))
     requestUrl+=("&shopId="+dojo.byId("shopSelector").value);
      dojo.xhrGet( {
        url: requestUrl, 
        handleAs: "text",

        timeout: 120000,

        load: function(response, ioArgs) {
          dojo.byId("reportPortion").innerHTML = response;
		  if(dojo.byId("formNotAvailableImage") == null){
		  	   weeklyValidate();
               weeklyAutoUpdate();
               if(dojo.byId("openingInventory").type != "hidden"){
               dojo.byId("openingInventory").focus();
               dojo.byId("openingInventory").select();
               }else{
               dojo.byId("commissaryPurchases").focus();
               dojo.byId("commissaryPurchases").select();
               }
            navigationWeeklyRegistor(dojo.byId('weeklySales'));			   
		  }
		  getLoadingInfo().hide();
          return response;
        },

        error: function(response, ioArgs) { 
          console.error("HTTP status code: ", ioArgs.xhr.status); 
          return response;
          }
        });
      }
      
    function navigationWeeklyRegistor(formNode){
	   var inputNodes=dojo.query("input",formNode);
	     var i=0;	     
	     for(i=0;i<inputNodes.length;i++)
	        if(inputNodes[i].type != 'submit')
	        dojo.connect(inputNodes[i],'onkeypress',navigationWeeklyProcess);
	        else
	        dojo.connect(inputNodes[i],'onkeypress',navigationWeeklySubmit);	   
	}

	function navigationWeeklySubmit(event){
	   key = event.keyCode;
	   if(key == dojo.keys.PAGE_DOWN){
	       navigationWeeklyDown(event.target);
	       dojo.stopEvent(event);
	   }
	   else if (key == dojo.keys.PAGE_UP){
	       navigationWeeklyUp(event.target);
	       dojo.stopEvent(event);
	   }
	   return true;
	}	
	
	function navigationWeeklyProcess(event){
	   key = event.keyCode;
	   if(key == dojo.keys.PAGE_DOWN || key ==dojo.keys.NUMPAD_ENTER ||  key ==dojo.keys.ENTER){
	       navigationWeeklyDown(event.target);
	       dojo.stopEvent(event);
	   }
	   else if (key == dojo.keys.PAGE_UP){
	       navigationWeeklyUp(event.target);
	       dojo.stopEvent(event);
	   }
	   return true;
	}
	
		function navigationWeeklyDown(node){
	   var form=dojo.byId('weeklySales');
	   var index=dojo.indexOf(form,node);
	   index++;
	   if(index<form.length)
	   form[index].focus();
	}
	
    function navigationWeeklyUp(node){
	   var form=dojo.byId('weeklySales');
	   var index=dojo.indexOf(form,node);
	   if(index >0){
	   index--;
	   if(form[index].type !='hidden')
	   form[index].focus();
	   }	   
	}
    
	dojo.addOnLoad(loadMainPage);	
</script>
</head>
<body class="yui-skin-sam">
<script>
</script>
<pizza73:adminNav />
<div id="page"><pizza73:adminTabs />
<authz:authorize ifAnyGranted="ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_RECEPTION,ROLE_DSM">
<div id="shopSelectorDiv" style="height: 20px; padding-left: 10px; padding-top: 5px; padding-bottom: 5px;">
<select id="shopSelector" onchange="changeShop(this.value);">
<option value=" ">please select a shop</option>
<c:forEach var="shop" items="${SHOPS_BY_ID}">
  <option value="<c:out value="${shop.id}"/>"><c:out value="${shop.id}"/>&nbsp;-&nbsp;<c:out value="${shop.name}"/></option>
</c:forEach>
</select>
<span id="shopSelectorInfo">
</span>
</div>
</authz:authorize>
<div id="currentShopId" style="display:none"><c:out value="${DAILYSALES_SHOP.id}"/></div>
<div id="pageContent">
<ul class="glossymenu">
<li id="lastWeek"></li>
<li id="day_0"></li>
<li id="day_1"></li>
<li id="day_2"></li>
<li id="day_3"></li>
<li id="day_4"></li>
<li id="day_5"></li>
<li id="day_6"></li>
<li id="day_7"></li>
<li id="weeklySalesTag"></li>
<li id="nextWeek"></li>
<li>
<img id="calendarImageTag" src="./images/calendar_icon1.png" class="calendarImage" />
</li>
</ul>
<div id="reportPortion">
</div>
</div>
</div>
<pizza73:adminFooter />
<div id="popupCalendar" />
</body>
</html>

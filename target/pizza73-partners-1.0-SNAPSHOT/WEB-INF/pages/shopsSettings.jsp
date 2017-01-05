<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<title>Pizza 73 Partners: Current Payroll</title>
<pizza73:adminHead />
<script type='text/javascript' src="<c:url value="dwr/engine.js" />"></script>
<script type='text/javascript' src="<c:url value="dwr/util.js" />"></script>
<script type='text/javascript'
	src="<c:url value="dwr/interface/AjaxManager.js" />"></script>
<script type='text/javascript' src='./dojo/1.0.2/20080220/dojo.js'></script>
<script type='text/javascript' src='./dojo/1.0.2/20080220/pizza73_dojo.js'></script>
<script type="text/javascript">
   function getShopSettings(shopId){
      AjaxManager.getShopSettings(shopId,{callback:function(settings){
        dojo.byId('numberofFrontCounterMachines').value=settings[0];
        dojo.byId('numberofWirelessMachines').value=settings[1];
        dojo.byId('shop_id').value=settings[2];
      },async:false});
   }
   
   function saveSettings(){
       AjaxManager.setShopSettings(dojo.byId('shop_id').value,
       dojo.byId('numberofFrontCounterMachines').value,
       dojo.byId('numberofWirelessMachines').value,
       {callback:function(flag){
         if(flag==true)
            dojo.byId('infoConsole').innerHTML="changes for shop #"+dojo.byId('shop_id').value+" have been saved.";
         else
            dojo.byId('infoConsole').innerHTML="changes operation for shop #"+dojo.byId('shop_id').value+" fails.";
       },async:false});
   }

</script>

</head>
<body class="yui-skin-sam">
<pizza73:adminNav />
<div id="page"><pizza73:adminTabs />
<div id="pageContent">
   <select id="shopSelector"
	onchange="getShopSettings(this.value);">
	<option value=" ">please select a shop</option>
	<c:forEach var="shop" items="${SHOPS_BY_ID}">
		<option value="<c:out value="${shop.id}"/>"><c:out
			value="${shop.id}" />&nbsp;-&nbsp;<c:out value="${shop.name}" /></option>
	</c:forEach>
    </select>
    <br/>
    <div id="infoConsole" style="margin:10px 0px 10px 0px; height:10px"></div>
    <table border="0" style="margin-bottom:10px">
    <tr>
    <td>
    Number of front-counter machines:&nbsp;
    </td>
    <td>
    <input type="text" id="numberofFrontCounterMachines" name="numberofFrontCounterMachines" style="width:40px"/>
    </td>
    </tr>
    <tr>
    <td>
    Number of wireless machines:&nbsp;
    </td>
    <td><input type="text" id="numberofWirelessMachines" name="numberofWirelessMachines" style="width:40px"/>
    </td>
    </tr>
    </table>
    <input type="hidden" id="shop_id" name="shop_id" />
    <button id="save" name="save" onclick="saveSettings();">Save</button>
</div>
</div>
<pizza73:adminFooter />
</div>
</body>
</html>

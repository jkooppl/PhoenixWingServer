<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee List</title>
    <pizza73:adminHead />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/interface/AjaxManager.js"/>'></script>
    <script type='text/javascript' src='./dojo/1.0.2/20080220/dojo.js'></script>
    <script type='text/javascript' src='./dojo/1.0.2/20080220/pizza73_dojo.js'></script>
    
<script type='text/javascript' src='./yui-partners/yahoo-min.js'></script>
<script type='text/javascript' src='./yui-partners/event-min.js'></script>
<script type='text/javascript' src='./yui-partners/dom-min.js'></script>
<script type='text/javascript' src='./yui-partners/animation-min.js'></script>

    <script type="text/javascript">
      function init(e)
      {
    	  <authz:authorize ifAnyGranted="ROLE_SHOP_CRUD,ROLE_SHOP_MGR,ROLE_SHOP_OWNER,ROLE_TIMESHEET">
    	      getEmployeesForShop(<c:out value="${THIS_SHOP.id}" />); 
    	    </authz:authorize>
      }
    
      function getEmployees(e)
      {
        var index = e.selectedIndex;
        var shopId = e.options[index].value;
        
        AjaxManager.employeesForShop(shopId, {
          callback:function(data) {
            drawEmployees(data);}, async:false
        });
      }
      
      function getEmployeesForShop(shopId)
      {
          AjaxManager.employeesForShop(shopId, {
	          callback:function(data) {
	            drawEmployees(data);}, async:false
          });
      }
      
      function drawEmployees(data)
      {
        dwr.util.setValue("employeesForShop", data, { escapeHtml:false });
      }
      
      function flip(){
        var textContent=dojo.byId('flip');
        if(textContent.innerHTML.indexOf('Hide') != -1){
        	dojo.query(".disabled").style("display","none");
            textContent.innerHTML="Show all employee(s)";	
        }else{
        	dojo.query(".disabled").style("display","");
        	textContent.innerHTML="Hide inactive employee(s)";
        }
      }
     
      YAHOO.util.Event.onDOMReady(init); 
  </script>
  </head>
  <body>
    <pizza73:adminNav />
    <div id="page">
      <pizza73:adminTabs />
      <div id="pageContent">
	      <div id="contentColumn">
		      <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME">
		      <a id="exportNewEmployees" href="<c:url value="exportNewEmployees.html"></c:url>" class="extras">Export New Employees</a>
		      </authz:authorize>
		      <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME,ROLE_DSM">
		      <h1>Employees for shop</h1>
		          <div class="column1">
		            <p>
		              <label for="name">Shop</label> 
		              <select id="shopList" onchange="getEmployees(this)">
		                <option id="shop0" value="0" >-- Select a shop --</option>
		                <c:forEach var="shop" items="${SHOPS}"> 
		                  <option id="shop${shop.id}" value="${shop.id}" >${shop.name}</option>
		                </c:forEach>
		              </select>
		            </p>
		          </div>              
		        </authz:authorize>
		        <div id="employeesForShop">
		        </div>
	      </div>
      </div>
      <pizza73:adminFooter />
    </div>
  </body>
</html>

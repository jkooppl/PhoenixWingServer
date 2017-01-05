<%@tag body-content="scriptless"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="authz"%>
<ul id="orderSteps">
	<authz:authorize ifAnyGranted="ROLE_SHOP_CRUD,ROLE_SHOP_MGR,ROLE_SHOP_OWNER,ROLE_TIMESHEET">
		<li id="employees"><a href="<c:url value="employeeList.html"/>">Employees</a></li>
	</authz:authorize>
	<authz:authorize ifAnyGranted="ROLE_SUPREME">
		<li id="shopsSettings"><a href="<c:url value="shopsSettings.html"/>">Shops Settings</a></li>
	</authz:authorize>
	<authz:authorize ifAnyGranted="ROLE_SUPREME,ROLE_PAYROLL">
		<li id="payrollAdmin"><a href="<c:url value="payrollAdmin.html"/>">Payroll Admin</a></li>
	</authz:authorize>
	<authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME,ROLE_DSM">
		<li id="employees"><a href="<c:url value="employeeList.html"/>">Employee search</a></li>
	</authz:authorize>
	<authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
		<li id="payroll"><a href="<c:url value="payrollExport.html"/>">Payroll Export</a></li>
	</authz:authorize>
	<authz:authorize ifAnyGranted="ROLE_SHOP_MGR, ROLE_SHOP_OWNER, ROLE_TIMESHEET">
		<li id="payroll"><a href="<c:url value="payroll.html"/>">Payroll</a></li>
	</authz:authorize>

	<authz:authorize
		ifAnyGranted="ROLE_SHOP_MGR,ROLE_SHOP_OWNER,ROLE_TIMESHEET,ROLE_PAYROLL,ROLE_SUPREME,ROLE_ACCOUNTANT,ROLE_DSM">
		<li id="payrollSearch"><a href="<c:url value="payrollSearch.html"/>">Payroll Search</a></li>
	</authz:authorize>

	<authz:authorize ifAnyGranted="ROLE_PAYROLL, ROLE_SUPREME">
		<li id="employeeUpdate"><a href="<c:url value="employeeUpdate.html"/>">Employee Update</a></li>
	</authz:authorize>

	<authz:authorize
		ifAnyGranted="ROLE_SHOP_OWNER,ROLE_ACCOUNTANT,ROLE_SUPREME,ROLE_DAILY_SALES,ROLE_RECEPTION,ROLE_DSM">
		<li id="dailySalesTab"><a href="<c:url value="dailySales.html"/>">Daily Sales</a></li>
	</authz:authorize>
	<authz:authorize
		ifAnyGranted="ROLE_SUPREME,ROLE_CSC,ROLE_SHOP_OWNER,ROLE_DSM,ROLE_CRV">
		<li id="complaintListTab"><a href="<c:url value="complaintList.html"/>">Complaint Reversal</a></li>
	</authz:authorize>	
	<authz:authorize ifAnyGranted="ROLE_SUPREME,ROLE_SHOP_OWNER">
		<li id="posreport"><a href="<c:url value="posreport.html"/>">POS Z-report</a></li>
	</authz:authorize>
	<authz:authorize ifAnyGranted="ROLE_SHOP_CRUD,ROLE_TIMESHEET,ROLE_SHOP_MGR,ROLE_SHOP_OWNER,ROLE_PAYROLL,ROLE_SUPREME,ROLE_ACCOUNTANT,ROLE_DAILY_SALES, ROLE_RECEPTION">
		<li id="documentationTab"><a href="<c:url value="documentation.html"/>">Documentation</a></li>
	</authz:authorize>
	<c:choose>
		<c:when test="${SPRING_SECURITY_CONTEXT == null}">
			<li id="login" class="active"><a
				href="<c:url value="login.html"/>">Login</a></li>
		</c:when>
		<c:otherwise>
			<li id="logout"><a href="<c:url value="logout.html"/>">Logout</a></li>
		</c:otherwise>
	</c:choose>
</ul>

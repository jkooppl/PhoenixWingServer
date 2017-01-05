<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<head>
<title>Pizza 73 Parnters: Complaint List</title>
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
		<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<pizza73:adminHead />
</head>
<body>
<pizza73:adminNav />
<div id="page"><pizza73:adminTabs />
<div id="pageContent" style="font-size:14px">
<authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_SUPREME,ROLE_CSC,ROLE_DSM,ROLE_CRV">
<authz:authorize ifAnyGranted="ROLE_SUPREME,ROLE_CSC,ROLE_DSM">
   <c:choose>
   <c:when test="${SHOWALL == false}">
   <h1 style="font-size:20px"> Active Record List</h1>
   <a href="javascript:void()" onclick="location.href='complaintList.html?showall=1';" >Click to show all records in past 30 days</a>
   </c:when>
   <c:otherwise>
   <h1 style="font-size:20px"> All Records in Past 30 Days</h1>
   <a href="javascript:void()" onclick="location.href='complaintList.html';">Click to show only active records</a>
   </c:otherwise>
   </c:choose>
</authz:authorize>
<c:forEach var="complaint" items="${SHOP_COMPLAINTS}">
   <div style="clear:both;padding-top:20px">
   <div style="float:left">
   <%@ include file="complaintInfo.jsp"%>
   </div>
   <div style="float:left;padding-left:20px">
       <c:choose>
       <c:when test="${complaint.id !=null}">
       <c:set var="redirectUrl" value="/complaintReversal.html?complaint_id=${complaint.id}"/>
       </c:when>
       <c:otherwise>
       <c:set var="redirectUrl" value="/complaintReversal.html?reversal_id=${complaint.complaintReversal.id}"/>
       </c:otherwise>
       </c:choose>
   	   <c:choose>
   	   <c:when test="${complaint.complaintReversal == null}" >
   	        <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_SUPREME">    	   
   	   		<button onclick="location.href='<c:out value="${redirectUrl}"/>';" style="font-size:16px">Request a reversal</button>
   	   		</authz:authorize>
   	   </c:when>
   	   <c:when test="${complaint.complaintReversal !=null and complaint.complaintReversal.responded == false}" > 
   	        <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_DSM,ROLE_CRV">  	   
   	   		<a href="javascript:void()" onclick="location.href='<c:out value="${redirectUrl}"/>';" style="font-size:16px;color:orange">View submitted request</a>
   	   		</authz:authorize>
   	        <authz:authorize ifAnyGranted="ROLE_SUPREME,ROLE_CSC">  	   
   	   		<button onclick="location.href='<c:out value="${redirectUrl}"/>';" style="font-size:16px">Process this request</button>
   	   		</authz:authorize>
   	   		
   	   </c:when>
   	   <c:otherwise>
   	   		<authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_SUPREME,ROLE_CRV,ROLE_DSM,ROLE_CSC"> 
   	        <a href="javascript:void()" onclick="location.href='<c:out value="${redirectUrl}"/>';" style="font-size:16px;color:green">View CSC response</a>
   	        </authz:authorize>
   	   </c:otherwise>
   	   </c:choose>
   </div>
   </div>      
</c:forEach>
  
</authz:authorize>
</div>
</div>
</body>
</html>
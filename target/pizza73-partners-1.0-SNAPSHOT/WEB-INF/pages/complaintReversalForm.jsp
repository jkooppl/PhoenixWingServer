<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="authz"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<head>
<title>Pizza 73 Parnters: Complaint Reversal</title>
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
		<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<pizza73:adminHead />
<script language="Javascript">
function input_field_length_limiter(node,len,lengthlabel){
	truncated=false;
	if(node.value != null && node.value.length > len){
		node.value=node.value.substr(0,len)
		truncated=true;
		}
	document.getElementById(lengthlabel).innerHTML="("+(len-node.value.length)+" chars left)";
	return truncated;
}
</script>
</head>
<body>
<pizza73:adminNav />
<div id="page"><pizza73:adminTabs />
<div id="pageContent" style="font-size:14px">
<authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_SUPREME,ROLE_CSC,ROLE_DSM,ROLE_CRV">
<c:set var="complaint" value="${COMPLAINT}" />
   <%@ include file="complaintInfo.jsp"%>
   <div style="width:500px;padding-top:20px;font-size:16px">
   <c:choose>
   <c:when test="${complaintReversal.id == null}">
   <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_SUPREME">
   <div style="font-size:16px;color:blue">Please enter your reversal request:</div> 
   <div id="content_length" style="font-size:16px;color:blue"></div>
   </authz:authorize>    
   </c:when>
   <c:otherwise>
   <div style="font-size:16px;color:blue">Submitted Reversal Request:</div>
   </c:otherwise>
   </c:choose>
   
   <form:form commandName="complaintReversal">
   <c:choose>
   <c:when test="${complaintReversal.id == null}">
   <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_SUPREME">  
   <form:textarea path="content" rows="13" cols="75" onkeyup="input_field_length_limiter(this,1000,'content_length')" cssStyle="width:550px;font-size:16px"  />
   <input type="submit" value="Submit"/>
   </authz:authorize>
   </c:when>
   <c:otherwise>
   <form:textarea path="content" rows="13" cols="75"  readonly="true" cssStyle="background-color:#DDDDDD;border:0px;width:550px;font-size:16px"/>
   <c:choose>
   <c:when test="${complaintReversal.responded and not REVISABLE}">
   <div style="font-size:16px;color:blue">CSC Response:<br/>
   <authz:authorize ifAnyGranted="ROLE_SUPREME,ROLE_CSC">
          <c:choose>
       <c:when test="${complaint.id !=null}">
       <c:set var="redirectUrl" value="/complaintReversal.html?complaint_id=${complaint.id}"/>
       </c:when>
       <c:otherwise>
       <c:set var="redirectUrl" value="/complaintReversal.html?reversal_id=${complaint.complaintReversal.id}"/>
       </c:otherwise>
       </c:choose>  	   
   	   <a onclick="return confirm('A response has been created before. Do you want to revise the old response?');" href="<c:out value="${redirectUrl}"/>&revise=1" style="font-size:16px">Revise the response</a> 
   </authz:authorize>
   </div>
   <form:textarea path="response" rows="13" cols="75" readonly="true" cssStyle="background-color:#DDDDDD;border:0px;width:550px;font-size:16px" />
   </c:when>
   <c:otherwise>
   <authz:authorize ifAnyGranted="ROLE_SUPREME,ROLE_CSC">
   <c:if test="${complaint.id != null}">
   <a href="https://alhambra.pizza73.com/iqueso-bin/sales_memo.py?sales_memo_id=${complaint.id}&function=edit" target="_blank">Edit the complaint</a>
   &nbsp;&nbsp;|&nbsp;&nbsp;
   <a onclick="return confirm('Are you sure you want to delete this complaint?');" href="https://alhambra.pizza73.com/iqueso-bin/sales_memo.py?sales_memo_id=${complaint.id}&function=delete" target="_blank">Delete the complaint</a>
   </c:if>
   <div style="font-size:16px;color:blue">Please enter CSC response:</div> 
   <div id="response_length" style="font-size:16px;color:blue"></div> 
   <form:textarea path="response" rows="13" cols="75" cssStyle="width:550px;font-size:16px" onkeyup="input_field_length_limiter(this,1000,'response_length')" />
   <input type="submit" value="Submit"/>
   </authz:authorize>
   </c:otherwise>
   </c:choose>
   </c:otherwise>
   </c:choose>
   </form:form>
   </div>
</authz:authorize>
</div>
</div>
</body>
</html>
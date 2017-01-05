<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<style type="text/css">
@media print {
    .buttonStyle{
    display:none;
    }
  }
</style>
</head>
<html>
<body>
<button class="buttonStyle" onclick="window.print();">Print</button>
<button class="buttonStyle" onclick="document.location='./employeeList.html';document.reload();">Back to employee list</button>
<div style="width:650px">
<h1 style="text-align:center">New Employee Information</h1>
Unit Number:&nbsp;&nbsp;<b><u>#${shop.id}&nbsp;-&nbsp;${shop.shortName}</u></b>
<br/>
<br/>
<table style="border:1px solid black" border="0" width="100%">
<tr >
<td colspan="6" style="border-bottom:1px solid black;text-align:center">
Personal Information
</td>
</tr>
<tr>
<td>
Employee&nbsp;Name:
</td>
<td colspan="2" style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.lastName}</i></b>
</td>
<td colspan="2" style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.name}</i></b>
</td>
<td style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.middleInitial}</i></b>
</td>
</tr>
<tr>
<td>
&nbsp;
</td>
<td colspan="2" style="text-align:center">
last
</td>
<td colspan="2" style="text-align:center">
First
</td>
<td style="text-align:center">
init
</td>
</tr>
<tr>
<td>
Present&nbsp;Address:
</td>
<td style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.address.suiteNumber}</i></b>
</td >
<td style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.address.streetAddress}</i></b>
</td>
<td style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.address.city}</i></b>
</td>
<td style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.address.province}</i></b>
</td>
<td style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.address.postalCode}</i></b>
</td>
</tr>
<tr>
<td>
&nbsp;
</td>
<td style="text-align:center">
apt#
</td>
<td style="text-align:center">
street address
</td>
<td style="text-align:center">
city
</td>
<td style="text-align:center">
province
</td>
<td style="text-align:center">
Postal Code
</td>
</tr>
<tr>
<td>
Telephone:
</td>
<td colspan="2" style="border-bottom:1px solid black;">
&nbsp;<b><i>${employee.phone}</i></b>
</td>
<td colspan="3">
</td>
</tr>
<tr>
<td>
Type of Employment:
</td>
<td colspan="5">
<c:choose>
<c:when test="${employee.typeOfEmployment == 'FT'}">
FT&nbsp;<input type="checkbox" name="FT" value="FT" checked="true" disabled="true"/>&nbsp;PT&nbsp;<input type="checkbox" name="PT" value="PT" disabled="true"/>
</c:when>
<c:otherwise>
FT&nbsp;<input type="checkbox" name="FT" value="FT" disabled="true"/>&nbsp;PT&nbsp;<input type="checkbox" name="PT" value="PT" checked="true" disabled="true"/>
</c:otherwise>
</c:choose>
</td>
<td colspan="3">
</td>
</tr>
</table>
<br/>
<table style="border:1px solid black" border="0" width="100%">
<tr >
<td colspan="6" style="border-bottom:1px solid black;text-align:center">
To be Completed By employee Upon Hiring
</td>
</tr>
<tr>
<td width="10px">
Social&nbsp;Insurance&nbsp;#:
</td>
<td width="200px" style="border-bottom:1px solid black;text-align:center;">
<b><i>${employee.sin}</i></b>
</td>
<td width="10px">Date&nbsp;of&nbsp;Birth:</td>
<td width="200px" style="border-bottom:1px solid black;text-align:center">
<b><i><fmt:formatDate value="${employee.birthDay}" type="date" pattern="MM/dd/yyyy" /></i></b>
</td>
<td>
Gender:
</td>
<td width="100px" style="border-bottom:1px solid black;text-align:center">
<b><i>
<c:choose>
<c:when test="${employee.sex == 'm'}">
Male
</c:when>
<c:otherwise>
Female
</c:otherwise>
</c:choose>
</i></b>
</td>
</tr>
<tr>
<td>
&nbsp;
</td>
<td>
&nbsp;
</td>
<td>
&nbsp;
</td>
<td style="text-align:center">
mm/dd/yy
</td>
<td>
&nbsp;
</td>
<td>
&nbsp;
</td>
</tr>
<tr>
<td>
Marital&nbsp;Status:
</td>
<td colspan="2" style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.maritalStatus}</i></b>
</td >
<td colspan="3">
&nbsp;
</td>
</tr>
<tr>
<td>
&nbsp;
</td>
<td colspan="2" style="text-align:center">
Single/Married/Comlaw
</td >
<td colspan="3">
&nbsp;
</td>
</tr>
<tr>
<td colspan="6" style="text-align:center">
**If your SIN begins with a 9, you must provide a copy of your visa stating that your are allowed to work in Canada, and a photocopy of your SIN card.
</td>
</tr>
</table>
<br/>
<table style="border:1px solid black" border="0" width="100%">
<tr >
<td colspan="6" style="border-bottom:1px solid black;text-align:center">
To be Completed By Hiring Manager
</td>
</tr>
<tr>
<td width="10px">
Commence&nbsp;Date:
</td>
<td width="200px" style="border-bottom:1px solid black;text-align:center;">
<b><i><fmt:formatDate value="${employee.hireDate}" type="date" pattern="MM/dd/yyyy" /></i></b>
</td>
<td width="10px">Position:</td>
<td width="200px" style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.position}</i></b>
</td>
<td>
Wage:
</td>
<td width="100px" style="border-bottom:1px solid black;text-align:center">
<b><i>${employee.primaryWage}</i></b>
</td>
</tr>
<tr>
<td>
&nbsp;
</td>
<td style="text-align:center">
mm/dd/yy
</td>
<td>
&nbsp;
</td>
<td style="text-align:center">
&nbsp;
</td>
<td>
&nbsp;
</td>
<td style="text-align:center">
$/hr
</td>
</tr>
<tr>
<td colspan="2" style="border-bottom:1px solid black;text-align:center">
&nbsp;
</td >
<td>
&nbsp;
</td>
<td colspan="2" style="border-bottom:1px solid black;text-align:center">
&nbsp;
</td>
<td>
&nbsp;
</td>
</tr>
<tr>
<td colspan="2" style="text-align:center">
Manager's signature
</td >
<td>
&nbsp;
</td>
<td colspan="2" style="text-align:center">
Date
</td >
<td>
&nbsp;
</td>
</tr>
</table>
</div>
<button class="buttonStyle" onclick="window.print();">Print</button>
<button class="buttonStyle" onclick="document.location='./employeeList.html';document.reload();">Back to employee list</button>
</body>
</html>
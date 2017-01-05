   <table border="1" width=550 bordercolor="black">
   <c:choose>
   	   <c:when test="${complaint.complaintReversal != null and complaint.complaintReversal.responded == false}" >
   	   <tr style="color:orange">
   	   <td>
   	   Reversal Request Time: 
   	   </td>
   	   <td><fmt:formatDate value="${complaint.complaintReversal.createTime.time}" type="both" dateStyle="full" timeStyle="full" />
   	   </td>
   	   </tr>   	   
   	   </c:when>
   	   <c:when test="${complaint.complaintReversal != null and complaint.complaintReversal.responded == true}" >
   	   <tr style="color:green">
   	   <td>
   	   CSC Response Time: 
   	   </td>
   	   <td><fmt:formatDate value="${complaint.complaintReversal.responseTime.time}" type="both" dateStyle="full" timeStyle="full" />
   	   </td>   	   
   	   </tr>   	   
       <tr style="color:orange">
   	   <td>  	   
   	   Reversal Request Time: 
   	   </td>
   	   <td ><fmt:formatDate value="${complaint.complaintReversal.createTime.time}" type="both" dateStyle="full" timeStyle="full" />
   	   </td>
   	   </tr>
   	   </c:when>
   </c:choose>
   <c:choose>
   <c:when test="${complaint.id == null}">
   <tr>
   <td colspan="2">The complaint has been removed by CSC after review.</td>
   </tr>
   </c:when>
   <c:otherwise>      
   <tr>
   <td width=200> Complaint Date/Time </td>
   <td width=350><fmt:formatDate value="${complaint.salesMemoDatetime.time}" type="both" dateStyle="full" timeStyle="full" /></td>
   </tr>
   <tr>
   <td>CSC Order Reference:</td>
   <td>#<fmt:formatNumber type="number" minIntegerDigits="3" value="${complaint.sales.id%1000}" /></td>
   </tr>
   <tr>
   <td>Shop Id:</td>
   <td><fmt:formatNumber type="number" minIntegerDigits="4" value="${complaint.shopId+1000}" groupingUsed="false" /></td>
   </tr>
   <tr>
   <td>Order Time:</td>
   <td><fmt:formatDate value="${complaint.sales.orderDate.time}" type="date" dateStyle="full" /> <c:out value="${complaint.sales.orderHour}" />:<c:out value="${complaint.sales.orderMinute}" /> </td>
   </tr>
   <tr>
   <td>Phone number:</td>
   <td>(<c:out value="${complaint.sales.areaCode}" />)<c:out value="${fn:substring(complaint.sales.phone,0,3)}" />-<c:out value="${fn:substring(complaint.sales.phone,3,7)}" /></td>
   </tr>
   <tr>
   <td>Address:</td>
   <td><c:out value="${fn:trim(complaint.sales.addressOne)}" /> <br/>
   	   <c:out value="${fn:trim(complaint.sales.addressTwo)}" /> <br/>
   	   <c:out value="${fn:trim(complaint.sales.addressThree)}" />
    </td>
   </tr>
   <tr>       
   <td>Order Total:</td>
   <td>$<c:out value="${complaint.sales.total}" />
    </td>
   </tr>
   <tr>
   <td>Agent</td>
   <td><c:out value="${complaint.reportedBy}" /></td>
   </tr>
   <c:if test="${complaint.complaintType1.id != 0}">
   <tr>
   <td>Complaint Type 1:</td>
   <td style="color:blue;font-weight:bold"><c:out value="${complaint.complaintType1.complaintDescr}" /></td>
   </tr>
   </c:if>
   <c:if test="${complaint.complaintType2.id != 0}">
   <tr>
   <td>Complaint Type 2:</td>
   <td style="color:blue;font-weight:bold"><c:out value="${complaint.complaintType2.complaintDescr}" /></td>
   </tr>
   </c:if>
   <c:if test="${complaint.complaintType3.id != 0}">
   <tr>
   <td>Complaint Type 3:</td>
   <td style="color:blue;font-weight:bold"><c:out value="${complaint.complaintType3.complaintDescr}" /></td>
   </tr>
   </c:if>   
   <c:if test="${complaint.complaintType4.id != 0}">
   <tr>
   <td>Complaint Type 4:</td>
   <td style="color:blue;font-weight:bold"><c:out value="${complaint.complaintType4.complaintDescr}" /></td>
   </tr>
   </c:if>
   <tr>
   <td>Complaint Message:
   </td>
   <td style="color:blue;font-weight:bold"><c:out value="${fn:trim(complaint.salesMemoText)}" /></td>
   </tr>
   <tr>
   <td>Responsible Party:
   </td>
   <td><c:out value="${fn:trim(complaint.responsibleParty.responsiblePartyAbbr)}" /></td>
   </tr>
   <tr>
   <td>Action Taken:
   </td>
   <td><c:out value="${fn:trim(complaint.actionTaken)}" /></td>
   </tr>
   </c:otherwise>
   </c:choose>
   </table>
  
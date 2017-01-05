<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="authz"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<h1 id="printHeader"> Daily Sales Report - Shop ${DAILYSALES_SHOP.name}</h1>
<table id="reportInfo" border="0" class="mainInfoTable">
	<tr>
		<td class="dateInfoTD">DATE:&nbsp;<fmt:formatDate
			value="${SELECTED_DATE.time}" type="date" dateStyle="full" /></td>
		<td class="writerInfoTD">
		<c:choose>
		<c:when test="${not dailySales.submitted}">
		EDITED
		</c:when>
		<c:otherwise>
		COMPLETED
		</c:otherwise>
		</c:choose>
		&nbsp;BY:&nbsp;${EMPLOYEE.lastName},&nbsp;${EMPLOYEE.name}&nbsp;${EMPLOYEE.middleInitial}</td>
		<td class="shopInfoTD">LOCATION/UNIT#:&nbsp;${DAILYSALES_SHOP.id}&nbsp;-&nbsp;${DAILYSALES_SHOP.name}</td>
		<c:choose>
		<c:when test="${not dailySales.submitted}">
		<td class="excelImageHolderTD"><a id="excelBWImageContainer" class="excelBWImage" href="#" onclick="return false;" onmouseover="createTip(this,'<img style=\'vertical-align:bottom\' src=\'./images/warning_16.png\' />Export is only avaiable after submitting to office.')"><img src="./images/excel_B_W.png" /></a></td>
		</c:when>
		<c:otherwise>
		<fmt:formatDate var="selectedDate" value="${SELECTED_DATE.time}" type="date" pattern="yyyy-MM-dd" />
		<td class="excelImageHolderTD"><a class="excelImage" href="<c:url value="dailySalesExport.html"><c:param name="date" value="${selectedDate}"/></c:url>"><img src="./images/excel.png" /></a></td>
		<c:remove var="selectedDate" />
		</c:otherwise>
		</c:choose>
	</tr>
</table>
<form:form commandName="dailySales">
		<c:if test="${not empty INFO_MESSAGES or true}">
		<div id="infoConsole">
		<c:forEach var="infoMessage" items="${INFO_MESSAGES}">
		<div><img style="vertical-align:bottom" src="./images/icons/accept.png"/><c:out value="${infoMessage}"/></div>
		</c:forEach>
		<div style="color:blue;font-size:12px;margin-bottom:5px;">Starting from this week(Feb 24th - Mar 2nd), please enter all CSC payments for catering order under "Catering credit" to balance daily cash.</div>
		</div>
		<c:remove var="INFO_MESSAGES" />
		</c:if>
		<div id="errorConsole"></div>
<form:hidden path="actualCash" />
<form:hidden path="version" />
<form:hidden path="numberofFrontCounterMachines"/>
<form:hidden path="numberofWirelessMachines" />
<table class="mainTableFrame">
	<tr>
		<td class="layoutTD">
		<table class="layoutTable" cellspacing="0">
			<tr>
				<td colspan="2" align="center" class="tdCell"><b>NET SALES</b></td>
			</tr>
			<tr id="computerSales_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="computerSales_label_1" class="tdCell">+Computer Sales</td>
				<td id="computerSales_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">
				<form:input path="computerSales" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="computerSales" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr id="walkInSales_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="walkInSales_label_1" class="tdCell">+Walk-In Sales</td>
				<td id="walkInSales_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">				
				<form:input path="walkInSales" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="walkInSales" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr id="miscSales_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="miscSales_label_1" class="tdCell">+Misc. Sales</td>
				<td id="miscSales_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="miscSales" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="miscSales" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr id="returns_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="returns_label_1" class="tdCell">-Returns</td>
				<td id="returns_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="returns" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="returns" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr id="netToPizza73_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="netToPizza73_label_1" class="tdCell" >=Net to Pizza73</td>			
				<td id="netToPizza73_cell_1" class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="gst_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="gst_label_1" class="tdCell">+G.S.T.</td>
				<td id="gst_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="gst" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="gst" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr id="gift_certificate_sold_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="gift_certificate_sold_label_1" class="tdCell">+Gift Certificate Sold</td>
				<td id="gift_certificate_sold_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="giftCertificateSold" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="giftCertificateSold" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr id="giftcard_reload_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="giftcard_reload_label_1" class="tdCell">+Pizza Card Reload</td>
				<td id="giftcard_reload_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="giftcardReload" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="giftcardReload" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>			
			<tr id="totalReceipts_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="totalReceipts_label_1" class="tdCell">=Total Receipts</td>			
				<td id="totalReceipts_cell_1" class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="overShort_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="overShort_label_1" class="tdCell">Cash Over/Short</td>
				<td id="overShort_cell_1" class="tdCell digitCell autoCell ">ERROR</td>
			</tr>			
		</table>
		</td>
		<td class="layoutTD" style="vertical-align:top">
		<table border="0" style="vertical-align:top">
		<tr style="vertical-align:top"><td style="vertical-align:top">
		<table class="layoutTable" cellspacing="0">
			<tr>
				<td colspan="2" align="center" class="tdCell"><b>GROSS SALES</b></td>
			</tr>
			<tr id="netToPizza73_2_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="netToPizza73_label_2" class="tdCell">+Net to Pizza73</td>
				<td id="netToPizza73_cell_2" class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="discountsAndAdvertising_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="discountsAndAdvertising_label_1" class="tdCell">+Discounts/Advertising</td>
				<td id="discountsAndAdvertising_cell_1"
					class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">	
			   <form:input path="discountsAndAdvertising" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
			   </c:when>
			   <c:otherwise>
			   <form:input path="discountsAndAdvertising" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
			   </c:otherwise>
			   </c:choose>
			   </td>
			</tr>
			<tr id="coupons_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="coupons_label_1" class="tdCell">+Coupons</td>
				<td id="coupons_cell_1" class="tdCell digitCell  autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="coupons" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="coupons" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr id="netSales_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="netSales_label_1" class="tdCell">=Net Sales</td>
				<td id="netSales_cell_1" class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="gst_2_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="gst_label_2" class="tdCell">+G.S.T</td>
				<td id="gst_cell_2" class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="grossSales_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="grossSales_label_1" class="tdCell">=Gross Sales</td>
				<td id="grossSales_cell_1" class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
		</table>
		</td>
		<td align="left" style="vertical-align:top">
		<c:if test="${dailySales.version > 1}">
		<table style="margin:0px 50px 20px 0px;vertical-align:top;" cellspacing="0">
			<tr>
				<td colspan="3" align="center" class="tdCell"><b>BILLS</b></td>
			</tr>
			<tr id="oneHundredDollarBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="oneHundredDollarBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$100</td><td>X</td></tr></table></td>
				<td id="oneHundredDollarBill_cell_1" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="oneHundredDollarBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="oneHundredDollarBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>				
				</td>
				<td id="oneHundredDollarBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="oneHundredDollarBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="fiftyDollarBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="fiftyDollarBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$50</td><td>X</td></tr></table></td>
				<td id="fiftyDollarBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="fiftyDollarBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="fiftyDollarBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="fiftyDollarBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="fiftyDollarBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="twentyDollarBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="twentyDollarBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$20</td><td>X</td></tr></table></td>
				<td id="twentyDollarBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="twentyDollarBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="twentyDollarBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="twentyDollarBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="twentyDollarBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="tenDollarBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="tenDollarBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$10</td><td>X</td></tr></table></td>
				<td id="tenDollarBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="tenDollarBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="tenDollarBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="tenDollarBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="tenDollarBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="fiveDollarBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="fiveDollarBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$5</td><td>X</td></tr></table></td>
				<td id="fiveDollarBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="fiveDollarBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="fiveDollarBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="fiveDollarBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="fiveDollarBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="bills_1_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="bills_label_1" colspan="2" class="tdCell" >BILLS TOTAL</td>				
				<td id="bills_cell_1"  class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr>
				<td colspan="3" align="center" class="tdCell"><b>COINS</b></td>
			</tr>
			<tr id="twoDollarBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="twoDollarBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$2</td><td>X</td></tr></table></td>
				<td id="twoDollarBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="twoDollarBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="twoDollarBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="twoDollarBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="twoDollarBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="oneDollarBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="oneDollarBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$1</td><td>X</td></tr></table></td>
				<td id="oneDollarBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="oneDollarBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="oneDollarBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="oneDollarBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="oneDollarBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="twentyFiveCentBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="twentyFiveCentBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$0.25</td><td>X</td></tr></table></td>
				<td id="twentyFiveCentBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="twentyFiveCentBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="twentyFiveCentBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="twentyFiveCentBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="twentyFiveCentBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="tenCentBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="tenCentBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$0.10</td><td>X</td></tr></table></td>
				<td id="tenCentBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="tenCentBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="tenCentBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="tenCentBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="tenCentBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="fiveCentBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="fiveCentBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$0.05</td><td>X</td></tr></table></td>
				<td id="fiveCentBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="fiveCentBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="fiveCentBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="fiveCentBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="fiveCentBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="oneCentBill_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="oneCentBill_label_1" class="tdCell"><table border="0" width="45px"><tr><td style="width:34px">$0.01</td><td>X</td></tr></table></td>
				<td id="oneCentBill_cell_" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="oneCentBill" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatIntegerDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="oneCentBill" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
				<td id="oneCentBill_total" class="tdCell digitCell  autoCell blueFlag"><span id="oneCentBill_total_span_1">ERROR</span></td>
			</tr>
			<tr id="coins_1_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="coins_label_1"  colspan="2" class="tdCell" >COINS TOTAL</td>				
				<td id="coins_cell_1"   class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr>
				<td colspan="3" align="center" class="tdCell"><b>CHEQUES</b></td>
			</tr>
			<tr id="chequesTotal_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="chequesTotal_label_1" colspan="2" class="tdCell">CHEQUES TOTAL</td>
				<td id="chequesTotal_cell_1" class="tdCell digitCell autoCell">
				<c:choose>
				<c:when test="${not dailySales.submitted}">					
				<form:input path="chequesTotal" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
				</c:when>
				<c:otherwise>
				<form:input path="chequesTotal" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
				</c:otherwise>
				</c:choose>					
				</td>
			</tr>
			<tr id="acutalCash_2_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="actualCash_label_2" colspan="2" class="tdCell">ACTUAL CASH</td>				
				<td id="actualCash_cell_2"  class="tdCell digitCell  autoCell"><span id="actualCash_cell_span_2">ERROR</span></td>
			</tr>
		</table>
		</c:if>		
		</td>
		</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td class="layoutTD">
		<table class="layoutTable" cellspacing="0">
			<tr>
				<td colspan="2" align="center" class="tdCell"><b>TOTAL</b></td>
			</tr>
			<tr id="actualCash_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="actualCash_label_1" class="tdCell">ACTUAL CASH</td>
				<td id="actualCash_cell_1" class="tdCell digitCell  autoCell">
				ERROR
				</td>
			</tr>
			<c:if test="${dailySales.version > 2}">
			<tr id="cateringCredit_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="cateringCredit_label_1" class="tdCell">Catering Credit</td>
				<td id="cateringCredit_cell_1"
					class="tdCell digitCell  autoCell">
				    <c:choose>
				    <c:when test="${not dailySales.submitted}">	
					<form:input path="cateringCredit" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
					</c:when>
					<c:otherwise>
					<form:input path="cateringCredit" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
					</c:otherwise>
					</c:choose>
					</td>
			</tr>
			</c:if>
			<tr id="giftCertificateRedeemed_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="giftCertificateRedeemed_label_1" class="tdCell">Gift Certificate Redeemed</td>
				<td id="giftCertificateRedeemed_cell_1"
					class="tdCell digitCell  autoCell">
				    <c:choose>
				    <c:when test="${not dailySales.submitted}">	
					<form:input path="giftCertificateRedeemed" cssClass="inputField" onfocus="highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
					</c:when>
					<c:otherwise>
					<form:input path="giftCertificateRedeemed" cssClass="transparentInputFiled" onfocus="this.blur();" readonly="true" />
					</c:otherwise>
					</c:choose>
					</td>
			</tr>
			<tr>
				<td colspan="2" align="center" class="tdCell"><b id="frontCounterSummary">FRONT-COUNTER MACHINES</b></td>
			</tr>
			<tr id="inStoreDebit_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="inStoreDebitTotal_label_1" class="tdCell">DEBIT</td>
				<td id="inStoreDebitTotal_cell_1" class="tdCell digitCell  autoCell">
                ERROR
				</td>
			</tr>			
			<tr id="inStoreVisa_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="inStoreVisaTotal_label_1" class="tdCell">VISA</td>
				<td id="inStoreVisaTotal_cell_1" class="tdCell digitCell  autoCell">
                ERROR
				</td>
			</tr>
			<tr id="inStoreAmex_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="inStoreAmexTotal_label_1" class="tdCell">AMEX</td>
				<td id="inStoreAmexTotal_cell_1" class="tdCell digitCell  autoCell">
                ERROR
				</td>
			</tr>			
			<tr id="inStoreMastercard_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="inStoreMastercardTotal_label_1" class="tdCell">MASTERCARD</td>
				<td id="inStoreMastercardTotal_cell_1" class="tdCell digitCell  autoCell">
                ERROR
				</td>
			</tr>
			<tr id="inStoreGiftcard_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="inStoreGiftcardTotal_label_1" class="tdCell">PIZZA CARD</td>
				<td id="inStoreGiftcardTotal_cell_1" class="tdCell digitCell  autoCell">
                ERROR
				</td>
			</tr>			
			<tr id="inStoreTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="inStoreTotal_label_1" class="tdCell">TOTAL</td>
				<td id="inStoreTotal_cell_1" class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr>
				<td colspan="2" align="center" class="tdCell"><b>WIRELESS MACHINES</b></td>
			</tr>
			<tr id="wirelessDriverDebitTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="wirelessDriverDebitTotal_label_1" class="tdCell">DEBIT</td>
				<td id="wirelessDriverDebitTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>			
			<tr id="wirelessDriverVisaTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="wirelessDriverVisaTotal_label_1" class="tdCell">VISA</td>
				<td id="wirelessDriverVisaTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="wirelessDriverAmexTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="wirelessDriverAmexTotal_label_1" class="tdCell">AMEX</td>
				<td id="wirelessDriverAmexTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="wirelessDriverMastercardTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="wirelessDriverMastercardTotal_label_1" class="tdCell">MASTERCARD</td>
				<td id="wirelessDriverMastercardTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>			
			<tr id="wirelessDriverGiftcardTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="wirelessDriverGiftcardTotal_label_1" class="tdCell">PIZZA CARD</td>
				<td id="wirelessDriverGiftcardTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>			
			<tr id="wirelessDriverTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="wirelessDriverTotal_label_1" class="tdCell">TOTAL</td>
				<td id="wirelessDriverTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
		    <tr>
				<td colspan="2" align="center" class="tdCell"><b>GRAND TOTAL</b></td>
			</tr>
			<tr id="CashTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="cashTotal_label_1" class="tdCell">CASH</td>
				<td id="cashTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<c:if test="${dailySales.version > 2}">
			<tr id="CateringCredit_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="cateringCredit_label_2" class="tdCell">Catering Credit</td>
				<td id="cateringCredit_cell_2"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			</c:if>			
			<tr id="GiftCertificateTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="giftCertificateRedeemed_label_2" class="tdCell">Gift Certificate Redeemed</td>
				<td id="giftCertificateRedeemed_cell_2"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="DebitTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="debitTotal_label_1" class="tdCell">DEBIT</td>
				<td id="debitTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>			
			<tr id="VisaTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="visaTotal_label_1" class="tdCell">VISA</td>
				<td id="visaTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="AmexTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="amexTotal_label_1" class="tdCell">AMEX</td>
				<td id="amexTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			<tr id="MastercardTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="mastercardTotal_label_1" class="tdCell">MASTERCARD</td>
				<td id="mastercardTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>			
			<tr id="giftcardTotal_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="giftcardTotal_label_1" class="tdCell">PIZZA CARD</td>
				<td id="giftcardTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>			
			<tr id="Total_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="grandTotal_label_1" class="tdCell">TOTAL TENDER</td>
				<td id="grandTotal_cell_1"
					class="tdCell digitCell  autoCell">ERROR</td>
			</tr>
			
		</table>
		</td>
		<td class="layoutTD">
				<table class="dailyWirelessForm" cellspacing="0">
			<tr>
				<td colspan="8" align="center" class="tdCell"><b>FRONT-COUNTER MACHINES</b></td>
			</tr>
			<tr>
				<td id="inStoreBatchRecord_Machine_label"
					class="tdCell machineCellInWirelssForm">MACHINE</td>
				<td id="inStoreBatchRecord_Debit_label"
					class="tdCell digitCellInWirelessForm">DEBIT</td>					
				<td id="inStoreBatchRecord_Visa_label"
					class="tdCell digitCellInWirelessForm">VISA</td>
				<td id="inStoreBatchRecord_Amex_label"
					class="tdCell digitCellInWirelessForm">AMEX</td>
				<td id="inStoreBatchRecord_Mastercard_label"
					class="tdCell digitCellInWirelessForm">MC</td>					
				<td id="inStoreBatchRecord_Giftcard_label"
					class="tdCell digitCellInWirelessForm">PIZZA CARD</td>					
				<td id="inStoreBatchRecord_Batch_label" class="tdCell ">BATCH
				NUMBER</td>
				<td id="inStoreBatchRecord_total_label"
					class="tdCell digitCellInWirelessForm">MACHINE<span style="width:1px">&nbsp;</span>TOTAL</td>
			</tr>
						<c:forEach var="i" begin="1" end="${dailySales.numberofFrontCounterMachines}">
							<tr id="BatchRecord_${i}" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
								<td id="BatchRecord_Machine_cell_${i}"
									class="tdCell machineCellInWirelssForm">${i}</td>
								<td id="BatchRecord_Debit_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_debit" path="batchRecords[${i-1}].debit" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_debit" path="batchRecords[${i-1}].debit" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>									
								<td id="BatchRecord_Visa_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_visa" path="batchRecords[${i-1}].visa" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_visa" path="batchRecords[${i-1}].visa" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>

								<td id="BatchRecord__Amex_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_amex" path="batchRecords[${i-1}].amex" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_amex" path="batchRecords[${i-1}].amex" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>
								<td id="BatchRecord_Mastercard_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_mastercard" path="batchRecords[${i-1}].mastercard" cssClass="batchRecordDigitMastercardInputField batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_mastercard" path="batchRecords[${i-1}].mastercard" cssClass="batchRecordDigitMastercardInputField transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>									
								<td id="BatchRecord_giftcard_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_giftcard" path="batchRecords[${i-1}].giftcard" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_giftcard" path="batchRecords[${i-1}].giftcard" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>									
								<td id="BatchRecord_Batch_cell_${i}"
									class="tdCell  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_batchNumber" path="batchRecords[${i-1}].batchNumber" cssClass="batchRecordNumberInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_batchNumber" path="batchRecords[${i-1}].batchNumber" cssClass="transparentBatchRecordNumberInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>
								<td
									class="tdCell michineTotalCellInWirelessForm digitCellInWirelessForm  autoCell blueFlag">
									<div  id="BatchRecord_total_cell_${i-1}">
									ERROR
									</div>
									</td>
							</tr>
						</c:forEach>			
			<tr id="inStoreBatch_total" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="NA_inStore_machine_total" class="tdCell machineCellInWirelssForm">SUBTOTAL</td>
				<td id="inStoreDebitTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>				
				<td id="inStoreVisaTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>
				<td id="inStoreAmexTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>
				<td id="inStoreMastercardTotal_cell_2"
					class="tdCell batchRecordDigitMastercardInputField digitCellInWirelessForm  autoCell">ERROR</td>					
				<td id="inStoreGiftcardTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>					
				<td id="NA_inStore_batchNumber_total" class="tdCell  autoCell">N/A</td>
				<td id="inStoreTotal_cell_2"
					class="tdCell michineTotalCellInWirelessForm digitCellInWirelessForm  autoCell">ERROR</td>
			</tr>
		</table>
		<table class="dailyWirelessForm" cellspacing="0">
			<tr>
				<td colspan="8" align="center" class="tdCell"><b>WIRELESS MACHINES</b></td>
			</tr>
			<tr>
				<td id="wirelessBatchRecord_Machine_label"
					class="tdCell machineCellInWirelssForm">MACHINE</td>
				<td id="wirelessBatchRecord_Debit_label"
					class="tdCell digitCellInWirelessForm">DEBIT</td>					
				<td id="wirelessBatchRecord_Visa_label"
					class="tdCell digitCellInWirelessForm">VISA</td>
				<td id="wirelessBatchRecord_Amex_label"
					class="tdCell digitCellInWirelessForm">AMEX</td>
				<td id="wirelessBatchRecord_Mastercard_label"
					class="tdCell digitCellInWirelessForm">MC</td>					
				<td id="wirelessBatchRecord_Giftcard_label"
					class="tdCell digitCellInWirelessForm">PIZZA CARD</td>					
				<td id="wirelessBatchRecord_Batch_label" class="tdCell ">BATCH
				NUMBER</td>
				<td id="wirelessBatchRecord_total_label"
					class="tdCell digitCellInWirelessForm">MACHINE<span style="width:1px">&nbsp;</span>TOTAL</td>
			</tr>
						<c:forEach var="i" begin="${dailySales.numberofFrontCounterMachines+1}" end="${dailySales.numberofFrontCounterMachines+dailySales.numberofWirelessMachines}">
							<tr id="BatchRecord_${i}" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
								<td id="BatchRecord_Machine_cell_${i}"
									class="tdCell machineCellInWirelssForm">${i-dailySales.numberofFrontCounterMachines}</td>
								<td id="BatchRecord_Debit_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_debit" path="batchRecords[${i-1}].debit" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_debit" path="batchRecords[${i-1}].debit" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>									
								<td id="BatchRecord_Visa_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_visa" path="batchRecords[${i-1}].visa" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_visa" path="batchRecords[${i-1}].visa" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>
								<td id="BatchRecord__Amex_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_amex" path="batchRecords[${i-1}].amex" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_amex" path="batchRecords[${i-1}].amex" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>
								<td id="BatchRecord_Mastercard_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_mastercard" path="batchRecords[${i-1}].mastercard" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_mastercard" path="batchRecords[${i-1}].mastercard" cssClass="batchRecordDigitMastercardInputField transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>
								<td id="BatchRecord_Giftcard_cell_${i}"
									class="tdCell digitCellInWirelessForm  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_giftcard" path="batchRecords[${i-1}].giftcard" cssClass="batchRecordDigitInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();formatDigit(this);autoUpdate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_giftcard" path="batchRecords[${i-1}].giftcard" cssClass="transparentBatchRecordDigitInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>
								<td id="BatchRecord_Batch_cell_${i}"
									class="tdCell  autoCell">
									<c:choose>
				                    <c:when test="${not dailySales.submitted}">
									<form:input id="BatchRecords_${i-1}_batchNumber" path="batchRecords[${i-1}].batchNumber" cssClass="batchRecordNumberInputField" onfocus="removeFocusRowError(this);highlightInputField(this);this.select();" onblur="normalizeInputField(this);validate();"/>
									</c:when>
									<c:otherwise>
									<form:input id="BatchRecords_${i-1}_batchNumber" path="batchRecords[${i-1}].batchNumber" cssClass="transparentBatchRecordNumberInputField" onfocus="this.blur();" readonly="true" />
									</c:otherwise>
									</c:choose>
									</td>
								<td 
									class="tdCell michineTotalCellInWirelessForm digitCellInWirelessForm  autoCell blueFlag">
									<div id="BatchRecord_total_cell_${i-1}">
									ERROR
									</div>
									</td>
							</tr>
						</c:forEach>			
			<tr id="wirelessBatch_total" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="NA_wireless_machine_total" class="tdCell machineCellInWirelssForm">SUBTOTAL</td>
				<td id="wirelessDriverDebitTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>				
				<td id="wirelessDriverVisaTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>
				<td id="wirelessDriverAmexTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>
				<td id="wirelessDriverMastercardTotal_cell_2"
					class="tdCell batchRecordDigitMastercardInputField digitCellInWirelessForm  autoCell">ERROR</td>					
				<td id="wirelessDriverGiftcardTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>
				<td id="NA_batchNumber_total" class="tdCell  autoCell">N/A</td>
				<td id="wirelessDriverTotal_cell_2"
					class="tdCell michineTotalCellInWirelessForm digitCellInWirelessForm  autoCell">ERROR</td>
			</tr>
			<tr id="Batch_total" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
				<td id="NA_wireless_machine_total" class="tdCell machineCellInWirelssForm">TOTAL</td>
				<td id="debitTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>				
				<td id="visaTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>
				<td id="amexTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>
				<td id="mastercardTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>					
				<td id="giftcardTotal_cell_2"
					class="tdCell digitCellInWirelessForm  autoCell">ERROR</td>					
				<td id="NA_total_batchNumber_total" class="tdCell  autoCell">N/A</td>
				<td id="machineTotal_cell_1"
					class="tdCell autoCell digitCellInWirelessForm" >ERROR</td>
			</tr>			
		</table>
		</td>
	</tr>
	<c:if test="${not dailySales.submitted}">
	<tr class="buttonHolder">
		<td colspan="2">
		<p><input type="submit" id="saveOrEdit" name="action" onclick="return checkBeforeSave();"
			value="Save/Edit" /> <input
			type="submit" id="cancelAction" name="CANCEL_ACTION" value="Cancel" onclick="return checkBeforeCancel();"/>
		<input type="submit" id="sendToOffice" name="action"
			value="Send To Office" onclick="return checkBeforeSubmit();"/></p>
		</td>
	</tr>
	</c:if>
</table>
</form:form>

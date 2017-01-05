<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="authz"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<h1 id="printHeader"> Weekly Sales Report - Shop ${DAILYSALES_SHOP.name}</h1>
<table id="reportInfo" border="0" class="mainInfoTable" width="894">
	<tr>
		<td class="dateInfoTD">Week:&nbsp;<fmt:formatDate
			value="${SELECTEDWEEK[0].time}" type="date" pattern="MM/dd/yyyy" />&nbsp;-&nbsp;<fmt:formatDate
			value="${SELECTEDWEEK[6].time}" type="date" pattern="MM/dd/yyyy" /></td>
		<td class="shopInfoTD">LOCATION/UNIT#:&nbsp;${DAILYSALES_SHOP.id}&nbsp;-&nbsp;${DAILYSALES_SHOP.name}</td>
		<c:choose>
		<c:when test="${!ISFORBIDDEN}">
		<fmt:formatDate var="selectedDate" value="${SELECTED_DATE.time}" type="date" pattern="yyyyMMdd" />
		<td align="right" class="excelImageHolderTD"><a class="excelImage"
			href="<c:url value="weeklySalesExport.html"><c:param name="date" value="${selectedDate}"/></c:url>"><img
			src="./images/excel.png" /></a></td>
		<c:remove var="selectedDate" />
		</c:when>
		<c:otherwise>		
		<td align="right" class="excelImageHolderTD"><a id="excelBWImage" class="excelImage" href="#" onclick="return false;" onmouseover="createTip(this,'<img style=\'vertical-align:bottom\' src=\'./images/warning_16.png\' />Export is only avaiable after all the dailySales reports are submitted.')"><img src="./images/excel_B_W.png" /></a></td>
		</c:otherwise>
		</c:choose>
	</tr>
</table>
<c:if test="${not empty INFO_MESSAGES or true}">
<div id="infoConsole" style="margin-bottom:10px">
<c:forEach var="infoMessage" items="${INFO_MESSAGES}">
<div><img style="vertical-align:bottom" src="./images/icons/accept.png"/><c:out value="${infoMessage}"/></div>
</c:forEach>
</div>
<c:remove var="INFO_MESSAGES" />
</c:if>
<div id="errorConsole">
</div>
<table class="mainTableFrame" >
	<tr>
		<td style="vertical-align: top">
		<c:choose>
		<c:when test="${ISFORBIDDEN}">
		<img id="formNotAvailableImage" src="./images/weeklyFormNotAvailable.png" onmouseover="createTip(this,'<img style=\'vertical-align:bottom\' src=\'./images/warning_16.png\' />Weekly form is only avaiable after all the daily sales reports in this week are submitted and all previous weekly sales reports are submitted.')" />
		</c:when>
		<c:otherwise>
		<form:form
			commandName="weeklySales">
			<form:hidden path="foodAndBeverageSales" />
			<c:if test="${not empty LASTWEEKLYSALES}">
					<input type="hidden" name="openingInventory" id="openingInventory" value="${LASTWEEKLYSALES.closingInventory}" />			     
			</c:if>
			<table style="width:230px" cellspacing="0">
				<tr>
					<td align="center" colspan="2" class="tdCell">FOOD INVENTORY<br />
					AND COST CALCULATIONS</td>
				</tr>
			   <c:choose>
			   <c:when test="${not empty LASTWEEKLYSALES}">
			   <tr id="openingInventory_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
			   </c:when>
			   <c:otherwise>
			   <tr id="openingInventory_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
			   </c:otherwise>
			   </c:choose>
					<td id="openingInventory_label"  align="left" class="tdCell textCell">OPENING INVENTORY</td>
					<td id="openingInventory_cell" class="tdCell digitCell autoCell">
								   <c:choose>
			   <c:when test="${not empty LASTWEEKLYSALES}">
			      ${LASTWEEKLYSALES.closingInventory}
			   </c:when>
			   <c:otherwise>
			   <c:choose>
			   <c:when test="${not weeklySales.submitted}">
					<form:input
						path="openingInventory" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);"/>
			  </c:when>
			  <c:otherwise>
					<form:input
						path="openingInventory" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />			  
			  </c:otherwise>
			  </c:choose>
			  </c:otherwise>
			  </c:choose>		
			   </td>
				</tr>
				<tr>
					<td align="center" colspan="2" class="tdCell">PURCHASES</td>
				</tr>
				<tr id="commissaryPurchases_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
					<td id="commissaryPurchases_label" align="left" class="tdCell textCell">COMMISSARY PURCHASES</td>
					<td id="commissaryPurchases_cell" class="tdCell digitCell autoCell" >
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="commissaryPurchases" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
				    </c:when>
				    <c:otherwise>
				    	<form:input
						path="commissaryPurchases" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />	
				    </c:otherwise>
				    </c:choose>
				    </td>
				</tr>
				<tr id="sysco_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
					<td id="sysco_label" align="left" class="tdCell textCell">SYSCO</td>
					<td id="sysco_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input path="sysco"
						cssClass="inputField" onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
				    </c:when>
				    <c:otherwise>
				    	<form:input
						path="sysco" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />					    
				    </c:otherwise>
				    </c:choose>
				    </td>
				</tr>
				<tr id="lilydale_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
					<td id="lilydale_label" align="left" class="tdCell textCell">LILYDALE</td>
					<td id="lilydale_cell"class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="lilydale" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);formatDigit(this);weeklyValidate();weeklyAutoUpdate();" />
				    </c:when>
				    <c:otherwise>
				    	<form:input
						path="lilydale" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />				    
				    </c:otherwise>
				    </c:choose>
				    </td>
				</tr>
				<tr id="pepsi_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
					<td id="pepsi_label" align="left" class="tdCell textCell">COKE</td>
					<td id="pepsi_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">					
					<form:input path="pepsi"
						cssClass="inputField" onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
					</c:when>
					<c:otherwise>
				    	<form:input
						path="pepsi" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />	
					</c:otherwise>
					</c:choose>
				    </td>
				</tr>
				<tr id="pettyCash_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
					<td id="pettyCash_label" align="left" class="tdCell textCell">PETTY CASH</td>
					<td id="pettyCash_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="pettyCash" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
					</c:when>
					<c:otherwise>
					<form:input
						path="pettyCash" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />
					</c:otherwise>
					</c:choose>
				    </td>
				</tr>
				<tr id="others_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
					<td id="others_label" align="left" class="tdCell textCell">OTHERS</td>
					<td id="others_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="others" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
					</c:when>
					<c:otherwise>
					<form:input
						path="others" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr id="totalPurchases_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
					<td id="totalPurchases_label" align="left" class="tdCell textCell">TOTAL PURCHASES</td>
					<td id="totalPurchases_cell" class="tdCell digitCell autoCell">ERROR</td>
				</tr>
				<tr id="closingInventory_tr" onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
					<td id="closingInventory_label" align="left" class="tdCell textCell">CLOSING INVENTORY</td>
					<td id="closingInventory_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="closingInventory" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
					</c:when>
					<c:otherwise>
					<form:input
						path="closingInventory" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />					
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr id="costOfSales_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
					<td id="costOfSales_label" align="left" class="tdCell textCell">COST OF SALES</td>
					<td id="costOfSales_cell" class="tdCell digitCell autoCell">ERROR</td>
				</tr>
				<tr id="foodAndBeverageSales_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
					<td id="foodAndBeverageSales_label" align="left" class="tdCell textCell">FOOD AND BEVERAGE SALES</td>
					<td id="foodAndBeverageSales_cell" class="tdCell digitCell autoCell">ERROR</td>
				</tr>
				<tr id="foodCost_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
					<td id="foodCost_label" align="left" class="tdCell textCell">FOOD COST</td>
					<td id="foodCost_cell" class="tdCell digitCell autoCell">N/A</td>
				</tr>
				<tr id="labourCost_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
					<td id="labourCost_label" align="left" class="tdCell textCell">EMPLOYEES LABOUR COST</td>
					<td id="labourCost_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="labourCost" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
					</c:when>
					<c:otherwise>
					<form:input
						path="labourCost" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />					
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<c:if test="${not empty IS_ENDING_WEEK}">				
				<tr id="paper_closing_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
					<td id="paper_closing_label" align="left" class="tdCell textCell">PAPER CLOSING</td>
					<td id="paper_closing_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="paperClosingInventory" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
					</c:when>
					<c:otherwise>
					<form:input
						path="paperClosingInventory" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />					
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr id="cleaning_closing_tr" onMouseOver="highlightAutoRow(this)" onMouseOut="normalizeAutoRow(this)">
					<td id="cleaning_closing_label" align="left" class="tdCell textCell">CLEANING CLOSING</td>
					<td id="cleaning_closing_cell" class="tdCell digitCell autoCell">
					<c:choose>
					<c:when test="${not weeklySales.submitted}">
					<form:input
						path="cleaningClosingInventory" cssClass="inputField"
						onfocus="highlightInputField(this);this.select();"
						onblur="normalizeInputField(this);weeklyValidate();weeklyAutoUpdate();formatDigit(this);" />
					</c:when>
					<c:otherwise>
					<form:input
						path="cleaningClosingInventory" cssClass="transparentInputFiled"
						onfocus="this.blur();" readonly="true" />					
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				</c:if>				
				<c:if test="${not weeklySales.submitted}">
				<tr class="buttonHolder"style="border: 0px solid none;">
					<td align="left" style="border: 0px solid none;" colspan="2">
					<div style="padding-top: 5px"><input type="submit" style="width:60px"
						id="saveOrEdit" name="action" onclick="return weeklyCheckBeforeSave();"
						value="Save/Edit" /> <input type="submit" id="cancelAction" style="width:50px"
						name="CANCEL_ACTION" value="Cancel"
						onclick="return checkBeforeCancel();" /> <input type="submit" style="width:90px"
						id="sendToOffice" name="action" value="Send To Office"
						onclick="return weeklyCheckBeforeSubmit();" /></div>
					</td>
				</tr>
				</c:if>
			</table>
		</form:form>
		</c:otherwise>
		</c:choose></td>
		<td width="40px">&nbsp;</td>
		<td>
		<table cellspacing="0">
			<tr>
				<td align="right" class="tdCell textCell">DAY:&nbsp;&nbsp;</td>
				<c:forEach var="i" begin="0" end="6">
					<td align="center" class="tdCell textCell"><fmt:formatDate
						value="${SELECTEDWEEK[i].time}" type="date" pattern="EEE" /></td>
				</c:forEach>
				<td align="center" class="tdCell textCell">TOTAL</td>
			</tr>
			<tr>
				<td align="right" class="tdCell textCell">DATE:&nbsp;&nbsp;</td>
				<c:forEach var="i" begin="0" end="6">
					<td align="center" class="tdCell textCell"><fmt:formatDate
						value="${SELECTEDWEEK[i].time}" type="date" pattern="MMM/dd" /></td>
				</c:forEach>
				<td align="center" class="tdCell textCell">&nbsp;</td>
			</tr>
			<tr style="height: 10px" />
			<tr id="cashTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="cashTotal_label" align="left" class="tdCell weeklyLabel ">+CASH</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="cashTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].cashTotal}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].cashTotal}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="cashTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="debitTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="debitTotal_label" align="left" class="tdCell weeklyLabel ">+DEBIT</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="debitTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].debitTotal}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].debitTotal}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="debitTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>			
			<tr id="visaTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="visTotal_label" align="left" class="tdCell weeklyLabel ">+VISA</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="visaTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].visaTotal}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].visaTotal}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="cashTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>

			<tr id="amexTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="amexTotal_label" align="left" class="tdCell weeklyLabel ">+AMEX</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="amextotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].amexTotal}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].amexTotal}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="amexTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="mastercardTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="mastercardTotal_label" align="left"
					class="tdCell weeklyLabel ">+MASTERCARD</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="mastercardTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].mastercardTotal}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].mastercardTotal}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="mastercardTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="giftcardTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="giftcardTotal_label" align="left" class="tdCell weeklyLabel ">+PIZZA CARD</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="giftcardTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].giftcardTotal}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].giftcardTotal}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="giftcardTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<c:if  test="${version3}">
			<tr id="cateringCreditTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="cateringCreditTotal_label" align="left"
					class="tdCell weeklyLabel ">+Catering Credit</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="cateringCreditTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].cateringCredit}
				<c:set var="SUM"
										value="${SUM+DAILYSALESREPORTS[i].cateringCredit}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="cateringCreditTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			</c:if>						
			<tr id="giftCertificateTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="giftCertificateTotal_label" align="left"
					class="tdCell weeklyLabel ">+Gift Certificate Redeemed</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="giftCertificateTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].giftCertificateRedeemed}
				<c:set var="SUM"
										value="${SUM+DAILYSALESREPORTS[i].giftCertificateRedeemed}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="giftCertificateTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="totalTenderTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="netSalesTotal_label" align="left"
					class="tdCell weeklyLabel ">=TOTAL TENDER&nbsp;(a)</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="tenderTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].totalTender}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].totalTender}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="netSalesTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="overShortTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="overShortTotal_label" align="left"
					class="tdCell weeklyLabel ">OVER/SHORT&nbsp;(a)-(b)</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="overShortTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].overShort}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].overShort}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="overShortTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr>
				<td colspan="9">&nbsp;</td>
			</tr>
			<tr id="netToPizza73_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="netOfGSTTotal_label" align="left"
					class="tdCell weeklyLabel ">+NET TO PIZZA 73</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="netOfGSTTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].netToPizza73}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].netToPizza73}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="netToPizza73Total_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
						<tr id="discountAndAdversitingTotal_tr"
				onMouseOver="highlightRow(this)" onMouseOut="normalizeRow(this)">
				<td id="discountAndAdversitingTotal_label" align="left"
					class="tdCell weeklyLabel">+DISCOUNTS/ADVERSITING</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="discountAndAdversitingTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].discountsAndAdvertising}
				<c:set var="SUM"
										value="${SUM+DAILYSALESREPORTS[i].discountsAndAdvertising}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="discountAndAdversitingTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="couponsTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="couponsTotal_cell_${i}" align="left"
					class="tdCell weeklyLabel ">+COUPONS</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td align="center" class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].coupons}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].coupons}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="couponsTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="netSalesTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="netSalesTotal_label" align="left"
					class="tdCell weeklyLabel ">=NET SALES</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="netSalesTotal_cell_${i}_2" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].netSales}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].netSales}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="netSalesTotal_cell_sum_2" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>			
			<tr id="gstTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="gstTotal_label" align="left" class="tdCell weeklyLabel ">+G.S.T.</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="gstTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].gst}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].gst}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="gstTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="grossSalesTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="grossSalesTotal_label" align="left"
					class="tdCell weeklyLabel ">=GROSS SALES</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="grossSalesTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].grossSales}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].grossSales}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="grossSalesTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr>
				<td colspan="9">&nbsp;</td>
			</tr>
			<tr id="computerSalesTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="computerSalesTotal_label" align="left"
					class="tdCell weeklyLabel ">+COMPUTER SALES</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="computerSalesTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].computerSales}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].computerSales}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td align="center" class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="walkInSalesTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="walkInSalesTotal_label" align="left"
					class="tdCell weeklyLabel ">+WALK-IN SALES</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="walkInSalesTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].walkInSales}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].walkInSales}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="walkInSalesTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="miscSalesTotal_tr" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="miscSalesTotal_label" align="left"
					class="tdCell weeklyLabel ">+MISC SALES</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="miscSalesTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].miscSales}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].miscSales}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="miscSalesTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
						<tr id="returnsTotal_tr_1" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="returnsTotal_label_1" align="left" class="tdCell weeklyLabel ">-RETURNS</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="returnsTotal_cell__${i}_1" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].returns}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].returns}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
			<td id="gstTotal_cell_sum_2" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="netToPizza73Total_tr_2" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="netSalesTotal_label_2" align="left"
					class="tdCell weeklyLabel ">=NET TO PIZZA73</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="netSalesTotal_cell_${i}_2" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].netToPizza73}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].netToPizza73}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="netToPizza73Total_cell_sum_2" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>			
			<tr id="gstTotal_tr_2" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="gstTotal_label_2" align="left" class="tdCell weeklyLabel ">+G.S.T.</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="gstTotal_cell_${i}_2" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].gst}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].gst}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="gstTotal_cell__sum_2" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="giftCertificateSoldTotal_tr_1" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="giftCertificateSoldTotal_label_1" align="left"
					class="tdCell weeklyLabel ">+GIFT CERTIFICATE SOLD</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="giftCertificateSoldTotal_cell_${i}_1" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].giftCertificateSold}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].giftCertificateSold}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="giftCertificateSoldTotal_cell_sum_1" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
			<tr id="giftcardReloadTotal_tr_1" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="giftcardReloadTotal_label_1" align="left"
					class="tdCell weeklyLabel ">+PIZZA CARD RELOAD</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="giftCertificateSoldTotal_cell_${i}_1" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].giftcardReload}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].giftcardReload}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="giftCertificateSoldTotal_cell_sum_1" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>			
			<tr id="totalReceiptsTotal_tr_2" onMouseOver="highlightRow(this)"
				onMouseOut="normalizeRow(this)">
				<td id="totalReceiptsTotal_label_2" align="left"
					class="tdCell weeklyLabel ">=TOTAL RECEIPTS&nbsp;(b)</td>
				<c:set var="SUM" value="0" />
				<c:forEach var="i" begin="0" end="6">
					<td id="totalReceiptsTotal_cell_${i}" align="center"
						class="tdCell autoCell weeklyDigitCell"><c:choose>
						<c:when test="${not empty DAILYSALESREPORTS[i].id}">
							<c:choose>
								<c:when test="${DAILYSALESREPORTS[i].submitted}">
				${DAILYSALESREPORTS[i].totalReceipts}
				<c:set var="SUM" value="${SUM+DAILYSALESREPORTS[i].totalReceipts}" />
								</c:when>
								<c:otherwise>
				EDITING
				</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
				N/A
				</c:otherwise>
					</c:choose></td>
				</c:forEach>
				<td id="totalReceiptsTotal_cell_sum" align="center"
					class="tdCell autoCell weeklyDigitCell">${SUM}</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

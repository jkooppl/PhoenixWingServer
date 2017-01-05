<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Shop payrolls</title>
    <script language="Javascript">
       function setupSelector(){
       document.getElementById("period").value="${PAYROLL_PERIOD}";
       document.getElementById("year").value="${PAYROLL_YEAR}";
       }
    </script>
    <pizza73:adminHead />
  </head>
  <body onload="setupSelector();">
    <pizza73:adminNav />
    <div id="page">
     <pizza73:adminTabs />
      <div id="pageContent">
        <div id="contentColumn">
          <div id="payroll">
            <h1>Shop Payroll Information for Pay Period ${PAYROLL_PERIOD} (${PAYROLL_YEAR})</h1>
            <div class="column1">
                <p>
            <form action="./payrollExport.html" method="get">
                 Period:
                 <select id="period" name="period" class="textInput">
                  <c:forEach items="${PAYROLL_PERIODS}" var="period">
                  <option value="${period}">${period}</option>
                  </c:forEach>
                  </select>
                 Year:
                 <select id="year" name="year" class="textInput">
                  <c:forEach items="${PAYROLL_YEARS}" var="year">
                  <option value="${year}">${year}</option>
                  </c:forEach>
                  </select>
                  <input type="submit" value="change"/>
                </p>
              </div>                        
            </div>
          </form>            
            <c:if test="${EXPORTABLE}">
              <h2 class="section"><a id="exportAllTop" href="<c:url value="exportPayroll.html"><c:param name="year" value="${PAYROLL_YEAR}"/><c:param name="period" value="${PAYROLL_PERIOD}"/></c:url>" class="extras">Export All</a>
              &nbsp;|&nbsp;
              <a id="exportShopTotalsTop" href="<c:url value="exportShopTotals.html"><c:param name="year" value="${PAYROLL_YEAR}"/><c:param name="period" value="${PAYROLL_PERIOD}"/></c:url>" class="extras">Export Shop Totals</a>
              </h2>
            </c:if>
            <c:forEach items="${SHOPS}" var="shop" varStatus="shopIndex">
              <p <c:if test="${shopIndex.count % 2 == 1}">class="zebra"</c:if>>
              <c:set var="shopExportable" value="EXPORTABLE_${shop.id}" />
              <c:set var="shopUpdatable" value="UPDATABLE_${shop.id}" />
              <c:set var="shopNA" value="NA_${shop.id}" />            
                <c:choose>
                  <c:when test="${requestScope[shopExportable]}">
                    <c:set var="SUBMIT_IMG" value="accept.png"/>
                  </c:when>
                  <c:when test="${requestScope[shopUpdatable]}">
                    <c:set var="SUBMIT_IMG" value="exclamation.png"/>
                  </c:when>
                  <c:otherwise>
                    <c:set var="SUBMIT_IMG" value="exclamation.png"/>
                  </c:otherwise>
                </c:choose>
                <img src="images/icons/${SUBMIT_IMG}" alt="submitted" />&nbsp;<strong>${shop.name}</strong>
                <c:choose>
                  <c:when test="${requestScope[shopExportable]}">
                    &nbsp;&nbsp;<a href="<c:url value="exportPayroll.html"><c:param name="id" value="${shop.id}"/><c:param name="year" value="${PAYROLL_YEAR}"/><c:param name="period" value="${PAYROLL_PERIOD}"/></c:url>">export</a>
                  </c:when>
                  <c:when test="${requestScope[shopUpdatable]}">
                    &nbsp;&nbsp;<a href="<c:url value="payroll.html?id=${shop.id}"/>">update payroll</a>
                  </c:when>
                  <c:otherwise>
                     &nbsp;&nbsp; &nbsp; N/A
                  </c:otherwise>
                </c:choose>
              </p>
            </c:forEach>
            <c:if test="${EXPORTABLE}">
              <h2 class="section"><a id="exportAllBottom" href="<c:url value="exportPayroll.html"><c:param name="year" value="${PAYROLL_YEAR}"/><c:param name="period" value="${PAYROLL_PERIOD}"/></c:url>" class="extras">Export All</a>
              &nbsp;|&nbsp;
              <a id="exportShopTotalsBottom" href="<c:url value="exportShopTotals.html"><c:param name="year" value="${PAYROLL_YEAR}"/><c:param name="period" value="${PAYROLL_PERIOD}"/></c:url>" class="extras">Export Shop Totals</a></h2>              
            </c:if>
          </div>
        </div>
      </div>
      <pizza73:adminFooter />
    </div>
  </body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Driver List</title>
    <pizza73:adminHead />
  </head>
  <body>
    <pizza73:adminNav />
    <div id="page">
      <pizza73:adminTabs />
      <div id="pageContent">
        <div id="contentColumn">
          <c:choose>
            <c:when test="${not empty DRIVERS}">
              <h1>
                Drivers for shop ${THIS_SHOP.name}
              </h1>
              <p><a href="<c:url value="driver.html"/>">Add Driver</a></p>
              <c:forEach var="driver" items="${DRIVERS}"varStatus="index">
                <p<c:if test="${index.count % 2 == 1}"> class="zebra"</c:if>>
                  <strong>Name: </strong>${driver.name} (${driver.nickname}) 
                  <a href="<c:url value="driver.html?id=${driver.id}"/>">edit</a>
                </p>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <h2>No Driver data available.</h2>
              <p><a href="<c:url value="driver.html"/>">Add Driver</a></p>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <pizza73:adminFooter />
    </div>
  </body>
</html>

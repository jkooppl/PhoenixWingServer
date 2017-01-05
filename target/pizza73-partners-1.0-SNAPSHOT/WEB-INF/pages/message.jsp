<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73a" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73</title>
    <pizza73a:adminHead />
  </head>
  <body>
    <pizza73a:adminNav />
    <pizza73a:adminTabs />
    <div id="page">
      <div id="pageContent">
        <div id="contentColumn">
          <c:choose>
            <c:when test="${not empty MESSAGE}">
              ${MESSAGE}
            </c:when>
            <c:otherwise>
              <h2>Click <a href="/orderSearch.html">here</a> to search for an order.</h2>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <pizza73a:adminFooter />
    </div>
    <c:remove var="MESSAGE" scope="session" />
  </body>
</html>

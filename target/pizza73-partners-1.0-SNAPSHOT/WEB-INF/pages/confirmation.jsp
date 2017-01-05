<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: ${MESSAGE.header}</title>
    <pizza73:adminHead />
  </head>
  <body>
    <pizza73:adminNav />
    <div id="page">
     <pizza73:adminTabs />
      <div id="pageContent">
        <h1>${MESSAGE.header}</h1>
        <p>${MESSAGE.message}</p>
      </div>
      <pizza73:adminFooter />
    </div>
    <c:remove var="MESSAGE" scope="session" />
  </body>
</html>

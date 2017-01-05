<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="pizza73a" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73: 404</title>
    <pizza73a:adminHead />
  </head>
  <body>
    <pizza73a:adminNav />
    <div id="page">
      <div id="pageContent">
        <div id="contentColumn">
          <h1>404 - An error has occurred [not found]</h1>
          <p>February, 2007: We've just launched an updated website. We've removed
          many out of date pages, so we apologize if you are looking for something we're
          no longer providing. We're not sure if it was your fault or our fault, but an
          error has occured.</p>
        </div>
      </div>
      <pizza73a:adminFooter />
    </div>
    <c:remove var="MESSAGE" scope="session" />
  </body>
</html>

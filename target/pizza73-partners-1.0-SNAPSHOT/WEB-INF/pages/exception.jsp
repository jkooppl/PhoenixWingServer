<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="pizza73a" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73: Error</title>
    <pizza73a:adminHead />
  </head>
  <body>
    <pizza73a:adminNav />
    <div id="page">
      <div id="menuContent">
        <h1>An application error has occured.</h1>
        <p>An application error has occured when trying to complete your request.
        Please email <a href="mailto:webAdmin@pizza73.com?subject=[Application Error]">webAdmin@pizza73.com</a>
        in order to help correct the problem.</p>
      </div>
      <pizza73a:adminFooter />
    </div>
    <c:remove var="MESSAGE" scope="session" />
  </body>
</html>

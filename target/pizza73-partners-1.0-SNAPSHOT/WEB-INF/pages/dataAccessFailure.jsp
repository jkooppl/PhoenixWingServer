<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73a" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73: Data Access Error</title>
    <pizza73a:adminHead />
  </head>
  <body>
    <pizza73a:adminNav />
    <div id="page">
      <div id="pageContent">
        <div id="contentColumn">
          <h1>Data Access Error Occured.</h1>
          <p>The application was unable to complete your request due to a database
          error. Please email <a href="mailto:webAdmin@pizza73.com?subject=[Data Access Error]">
          webAdmin@pizza73.com</a> in order to help correct the problem.</p>
        </div>
      </div>
      <pizza73a:adminFooter />
    </div>
  </body>
</html>

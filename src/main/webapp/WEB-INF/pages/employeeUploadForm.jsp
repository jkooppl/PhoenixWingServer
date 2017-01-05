<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>Pizza 73 Shop App: Employee List</title>
    <pizza73:adminHead />
  </head>
  <body>
    <pizza73:adminNav />
    <div id="page">
      <pizza73:adminTabs />
      <div id="pageContent">
      <div id="contentColumn">
        <form:form commandName="uploadItem" method="post" enctype="multipart/form-data">
            <div class="customerForm">
            <div class="column1">
                <p>
                <legend>Upload Fields</legend>
                    <form:label for="fileData" path="fileData">File</form:label><br/>
                    <form:input path="fileData" type="file"/>
                </p>
           </div>
                <div class="column1">
                <p>
                    <input type="submit" />
                </p>
                </div>
            </div>
        </form:form>
      </div>
      </div>
      <pizza73:adminFooter />
    </div>
  </body>
</html>

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
      <h1 style="font-size:22px">HR Forms:</h1>
      <p style="padding-top:5px"><a href="<c:url value="/pdf/2010_New_Hire_Form.pdf" />" target="_blank"><h1 style="font-size:16px">New Employee Form</h2></a></p>
      <p style="padding-top:5px"><a href="<c:url value="/pdf/change_form.pdf" />" target="_blank"><h1 style="font-size:16px">Employee Change Form</h2></a></p>
      <p style="padding-top:5px"><a href="<c:url value="/pdf/Direct_Deposit_Agreement_Form.pdf" />" target="_blank"><h1 style="font-size:16px">Direct Deposit Agreement Form</h2></a></p>
      <p style="padding-top:5px"><a href="<c:url value="/pdf/Benefits_Applications.pdf" />" target="_blank"><h1 style="font-size:16px">Benefits Application</h2></a></p>
      <p style="padding-top:5px"><a href="<c:url value="/pdf/Request_for_Employment_Verification_Letter.pdf" />" target="_blank"><h1 style="font-size:16px">Request for Employment Verification Letter</h2></a></p>
       <h1 style="font-size:22px">Manual:</h1>        
       <p style="padding-top:5px"><a href="<c:url value="/pdf/manual.pdf" />" target="_blank"><h1 style="font-size:16px">Partners Web Application Manual</h2></a></p>
       <br/>
       <h1 style="font-size:22px">FAQ:</h1>
      <p style="font-size:18px;">
      <div style="width:850px;text-align:justify">
      <div style="font-size:16px;font-weight:bold;">1. Timeout</div>
      <div style="font-size:14px">The system has a 3 hour timeout. Once you start to enter a form (daily sales/payroll), you need to finish it in 3 hours. If you need more time than that, use the "Save/Edit" button. 3 hour is always counted from the time you load/save a form.<br/>For example, if you open a daily sales report at 8:00PM, and you click "Save/Edit" at 8:15PM, the form will timeout at 11:15PM.If you need to be away for longer than 3 hours, you can click "Save/Edit" AND logout.Next time when you login, you can continue to edit that form.</div>
      <br/>
      
      <div style="font-size:16px;font-weight:bold;">2. "Save/Edit" and "Send To Office"</div>
        <div style="font-size:14px">"Save/Edit" means storing data to database. "Send To Office" means you have finished the report and it's ready for other departments to process your data.Data is NOT official after you click "Save/Edit" and you can still edit the data.Data is official after you click "Send To Office" and you can NOT edit the data anymore.<br/>
        </div>
      <br/>
      <div style="font-size:16px;font-weight:bold;">3. New Employee</div>
       <div style="font-size:14px">All the New Employee forms should be received in the head office by the 2nd Thursday of bi-weekly pay period. You can mail/fax them to the head office. Faxing is the recommended way.</div>
      <br/>
      <div style="font-size:16px;font-weight:bold;">4. Update Employee Information(such as wage, address.....)</div>       
        <div style="font-size:14px">Employee information on the website is updated from head office every Friday. To update your employee information, send the information to Dana by every Thursday. You can mail/fax that to the head office. Faxing is the recommended way.</div>                                         
      <br/>
      </div>
      </p>
      </div>
      <pizza73:adminFooter />
    </div>
    <c:remove var="MESSAGE" scope="session" />
  </body>
</html>
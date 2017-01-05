<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="taglibs.jsp"%>
<%@taglib prefix="pizza73" tagdir="/WEB-INF/tags/admin"%>   
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
   <head>
      <title>Pizza 73: Shop Login</title>
      <pizza73:adminHead />
      <script type='text/javascript' src='./dojo/1.0.2/20080220/dojo.js'></script>
	  <script type='text/javascript' src='./dojo/1.0.2/20080220/pizza73_dojo.js'></script>
	  <script type='text/javascript'>
       function removeCookie(){
	   dojo.cookie("displayTag",null);
	   dojo.cookie("displayShop",null);
	   if(dojo.isIE >6)
	       dojo.byId("browserInfo").innerHTML="You are using the recommended browser: Microsoft Internet Explorer "+dojo.isIE+".";
	   else if(dojo.isIE == 6)
	       dojo.byId("browserInfo").innerHTML="You are using the browser:Microsoft Internet Explorer "+dojo.isIE+". It's recomended to upgrade your browser.";
	   else if(dojo.isFF >1)
	       dojo.byId("browserInfo").innerHTML="You are using the recommended browser: Firefox "+dojo.isFF+".";
	   else if(dojo.isFF ==1)
	      dojo.byId("browserInfo").innerHTML="You are using the browser: Firefox "+dojo.isFF+". It's recommended to upgrade your browser.";
	   else
	      dojo.byId("browserInfo").innerHTML="It's recommended to use Microsoft Internet Explorer 7 / Firefox 2.";
	   }
	   dojo.addOnLoad(removeCookie);	
</script>
   </head>
   <body >
      <!-- HEADER -->
      <pizza73:adminNav />
      <div id="page">
         <div id="pageContent">  
         <div id="contentColumn">
          <div class="customerForm">  
            <div id="loginDiv">     
               <div class="column1">
                  <h2 class="section">Partner Login</h2>
                  <form method="post" id="loginForm" action='<c:url value="loginProcessing.html"/>'>
                     <div class="error">
                     <c:if test="${not empty error}">
                        <span class="checkoutError">Unable to login due to: ${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
                        </c:if>
                     </div>
                     <div id="signIn"> 
                        <p>
                           <label for="email">Username</label>
                           <input type="text" name="j_username" id="j_username" tabindex="1" class="textInput"/>
                        </p>
                        <p>
                           <label for="password">Password</label>
                           <input type="password" name="j_password" id="j_password" tabindex="2" class="textInput"/>
                        </p>
                        <p>
                           <input id="login" type="submit" value="Login" tabindex="3"/>
                        </p>
                     </div>
                  </form>
               </div>
                              <div id="browserInfo" style="clear:both;padding-top:10px;"></div>
            </div>
          </div>
        </div>
      </div>
      <pizza73:adminFooter/>
    </div>
  </body>
</html>

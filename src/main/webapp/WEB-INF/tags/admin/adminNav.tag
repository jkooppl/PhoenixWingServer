<%@tag body-content="scriptless" %>
<%@attribute name="active" required="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header" >
  <a href="employeeList.html"><img src="images/logo.jpg" id="logo" alt="Pizza 73" /></a>
  <ul id="menuMain" >
  </ul>
	<ul id="menuAccount">
    <c:choose>
    	<c:when test="${SPRING_SECURITY_CONTEXT == null}">
    		<li id="login" ><a href="<c:url value="login.html"/>" >Login</a></li>		
    	</c:when>
    	<c:otherwise>
    		<li id="logoutTab" ><a href="<c:url value="logout.html"/>" >Logout</a></li>							
    	</c:otherwise>
    </c:choose>
	</ul>	
</div>	
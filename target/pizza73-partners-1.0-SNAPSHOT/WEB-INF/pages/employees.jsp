<%@ include file="taglibs.jsp"%>
<c:choose>
  <c:when test="${not empty EMPLOYEES}">
    <h1>
      Employees for shop ${SHOP.name}
    </h1>
    <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME,ROLE_SHOP_OWNER,ROLE_DSM">
    <p>
    <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME,ROLE_SHOP_OWNER">
    <button style="width:150px" onclick="document.location='employee.html${URL_SESSION_ID}?shopId=${SHOP.id}';document.reload();">Add an employee</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </authz:authorize>
    <button style="width:180px" onclick="flip();"><span id="flip">Show all employee(s)</span></button></p>
    </authz:authorize>
    <c:forEach var="employee" items="${EMPLOYEES}" varStatus="index">
       <p 
         <c:choose>
          <c:when test="${employee.enabled}">
            <c:set var="ENABLED_IMG" value="accept.png"/>
            <c:set var="ENABLED_STRING" value="Disable"/>
            <c:choose>
            <c:when test="${index.count % 2 == 1}">
            class="zebra enabled"
            </c:when>
            <c:otherwise>
            class="enabled"
            </c:otherwise>
            </c:choose>
          </c:when>
          <c:otherwise>
            <c:set var="ENABLED_IMG" value="exclamation.png"/>
            <c:set var="ENABLED_STRING" value="Enable"/>
            <c:choose>
            <c:when test="${index.count % 2 == 1}">
            class="zebra disabled" style="display:none"
            </c:when>
            <c:otherwise>
            class="disabled" style="display:none"
            </c:otherwise>
            </c:choose>            
          </c:otherwise>
        </c:choose>
        >
        <img src="images/icons/${ENABLED_IMG}" />
        <strong>Name: </strong>${employee.name} ${employee.lastName} (${employee.payrollId})
        (<authz:authorize ifAnyGranted="ROLE_SHOP_OWNER,ROLE_PAYROLL,ROLE_SHOP_MGR,ROLE_SHOP_CRUD,ROLE_SUPREME,ROLE_DSM">
        <a href="<c:url value="employee.html${URL_SESSION_ID}?id=${employee.id}"/>">
        <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME">
        Edit
        </authz:authorize>
        <authz:authorize ifNotGranted="ROLE_PAYROLL,ROLE_SUPREME">
        View
        </authz:authorize>
        Info
        </a>&nbsp;
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_SUPREME">
         <a href="<c:url value="employeeRoles.html${URL_SESSION_ID}?id=${employee.id}"/>">Edit Role</a>&nbsp;
         </authz:authorize>     
        <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME">
        <a href="<c:url value="employeeStatusChange.html${URL_SESSION_ID}?id=${employee.id}"/>">${ENABLED_STRING}</a>&nbsp;
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME">
        <c:if test="${employee.newEmployee && not empty employee.payrollId}">
        <a href="<c:url value="newEmployeeStatusChange.html${URL_SESSION_ID}?id=${employee.id}"/>">Mark Imported</a>&nbsp;
        </c:if>
        <c:if test="${employee.salariedEmployee}">
        <span style="color:red">(operator account)</span>&nbsp;
        </c:if>
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_SHOP_OWNER">
        <c:if test="${employee.newEmployee}">
              <a href="<c:url value="newEmployeePrint.html${URL_SESSION_ID}?id=${employee.id}"/>">Print</a>&nbsp;
        </c:if>
        </authz:authorize>                    
        )
        <c:if test="${employee.newEmployee}">
        <img src="images/icons/new.png" title="This is a new employee waiting for head office to assign ADP id."/>
        </c:if>
      </p>
      <div id="editData${employee.id}"></div>
    </c:forEach>
  </c:when>
  <c:otherwise>
      <c:if test="${SHOP.id != 0}">
    <h2>No Employee data available.</h2>
    <authz:authorize ifAnyGranted="ROLE_PAYROLL,ROLE_SUPREME">
    <p><a href="<c:url value="employee.html${URL_SESSION_ID}?shopId=${SHOP.id}"/>">Add Employee</a></p>
    </authz:authorize>
    </c:if>            
  </c:otherwise>
</c:choose>

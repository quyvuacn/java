<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./layout/admin.jsp" %>
<html>
<head>
    <title>Employee List</title>
</head>
<body>
    <div class="container">
        <form method="GET">
            <input type="text" name="search" placeholder="Search ..." value="${pageContext.request.getParameter("search")}">
            <input type="submit" value="Search">
        </form>
    </div>
    <div class="container">
        <h3>Employee Details</h3>

        <c:if test="${not empty employees}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Full Name</th>
                <th>Birthday</th>
                <th>Address</th>
                <th>Position</th>
                <th>Department</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="employee" items="${employees.items}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.fullName}</td>
                    <td>${employee.birthday}</td>
                    <td>${employee.address}</td>
                    <td>${employee.position}</td>
                    <td>${employee.department}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

        <c:if test="${empty employees}">
            <p>No employees found.</p>
        </c:if>
    </div>
</body>
</html>

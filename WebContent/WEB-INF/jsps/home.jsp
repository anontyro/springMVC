<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Homepage</title>
</head>
<body>
Welcome to the wonderful new server new stuff
<br>
<!-- <%= session.getAttribute("name") %> -->

<!--  expession langauge used with $ and {NAME } with the name from the controller 
${name} -->

<c:out value="${name}"></c:out>
<br>

<sql:query var="rs" dataSource="jdbc/spring">
select * from offers
</sql:query>

<c:forEach var="row" items="${rs.rows }" >
id: ${row.id }<br>
name: ${row.name }<br>
text: ${row.text }<br>
</c:forEach>

</body>
</html>
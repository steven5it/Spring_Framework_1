<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>Session Page</title>
</head>

<body>
	<form action="/" method="POST">
		Type: <input type="text" name="type"/>
		<br/>
		Project: <input type="text" name="project"/><br/> 
		Year: <input type="text" name="year"/><br/> 
		<input type="submit" name="action" value="Submit"/><br />
		<br/>
	</form>
	
	<h3>History: </h3>
	<c:forEach items="${model.history}" var="hist">
		<li>${hist} </li>
	</c:forEach>

	<form action="/closeSession" method="POST">
		<input type="submit" name="action" value="Close Session"/>
		<br/>
	</form>
	
	<h3>Data: </h3>
	<h5>Submitted link: <c:out value="${model.eavesdropLink}" /></h5>
		<c:forEach items="${model.links}" var="link">
			<li>${link} </li>
		</c:forEach>

</body>
</html>
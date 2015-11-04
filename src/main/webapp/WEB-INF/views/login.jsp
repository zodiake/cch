<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<body>
	<form action="/member" method="post">
		<label for="name">name:</label>
		<input id="name" name="name"/>
		<label for="password">password:</label>
		<input id="password" name="password"/>
		<input type="submit"/>
	</form>
</body>

</html>
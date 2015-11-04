<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<body>
	<form:form modelAttribute="user" action="/signin">
		<label for="name">name:</label>
		<form:input path="name"/>
		<label for="password">password:</label>
		<form:input path="password"/>
		<input type="submit"/>
	</form:form>
</body>

</html>
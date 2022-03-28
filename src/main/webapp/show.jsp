<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bookapp</title>
<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}
</style>
</head>
<body>

	<table>
		<thead>
			<tr>
				<th>id</th>
				<th>title</th>
				<th>author</th>
				<th>price</th>
				<th>update</th>
				<th>delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${books}" var="book">
				<tr>
					<td>${book.id}</td>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.price}</td>
					<td><a
						href="BookController.do?action=updateBook&id=<c:out value="${book.id}"/>">update</a></td>
					<td><a
						href="BookController.do?action=delBook&id=<c:out value="${book.id}"/>">delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="BookController.do?action=addbook">add new book</a>

	<br />
	<br />

</body>
</html>
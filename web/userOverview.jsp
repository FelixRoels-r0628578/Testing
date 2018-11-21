<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="User Overview" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="User Overview" />
		</jsp:include>
		
		<main>
			<%@include file="partial/errors.jsp" %>
			
			<table>
				<thead>
					<tr>
						<th>E-mail</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Role</th>
						<th>Delete</th>
						<th>Update</th>
						<th>Check password</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users}" >
						<tr>
							<td><c:out value="${user.person.email}"/></td>
							<td><c:out value="${user.person.firstName}"/></td>
							<td><c:out value="${user.person.lastName}"/></td>
							<td><c:out value="${user.role.name}"/></td>
							<td><a href="Controller?action=userConfirmDelete&id=<c:out value="${user.id}"/>">Delete</a></td>
							<td><a href="Controller?action=userFormUpdate&id=<c:out value="${user.id}"/>">Update</a></td>
							<td><a href="Controller?action=userCheckPasswordForm&id=<c:out value="${user.id}"/>">Check</a></td>
						</tr>
					</c:forEach>
				</tbody>
				<caption>User Overview</caption>
			</table>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="userOverview" />
		</jsp:include>
	</div>
</body>
</html>
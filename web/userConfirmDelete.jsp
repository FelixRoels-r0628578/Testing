<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Confirm Delete User" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Confirm Delete User" />
		</jsp:include>
		
		<main>
			<p>You are about to delete user "<c:out value="${user.person.firstName}"/> <c:out value="${user.person.lastName}"/>". Are you sure?</p>
			<form action="Controller?action=userDelete" method="post">
				<input type="hidden" name="id" value="<c:out value="${user.id}"/>" />
				<input id="deleteUser" type="submit" value="Delete User"/>
				<a href="Controller?action=userOverview">Cancel</a>
			</form>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="userConfirmDelete" />
		</jsp:include>
	</div>
</body>
</html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Home" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Home" />
		</jsp:include>
		
		<main>
			<%@include file="partial/errors.jsp" %>
			
			<c:choose>
				<c:when test="${sessionScope.user != null}">
					<h3>Welcome, ${sessionScope.user.person.firstName}.</h3>
					<a href="Controller?action=userLogout" id="logOut">Log out</a>
				</c:when>
				<c:otherwise> 
					<form method="post" action="Controller?action=userLogin" novalidate="novalidate">
						<!-- novalidate in order to be able to run tests correctly -->
						<p>
							<label for="id">User ID</label>
							<input type="text" id="id" name="id" required
								value="<c:out value="${user.id != null ? user.id : ''}"/>">
						</p>
						<p>
							<label for="password">Password</label>
							<input type="password" id="password" name="password" required>
						</p>
						<p>
							<input type="submit" id="logIn" value="Log in">
						</p>
					</form>
				</c:otherwise>
			</c:choose>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="home" />
		</jsp:include>

	</div>
</body>
</html>
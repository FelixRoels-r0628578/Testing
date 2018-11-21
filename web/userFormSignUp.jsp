<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Sign Up" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Sign Up" />
		</jsp:include>
		<main>
			<%@include file="partial/errors.jsp" %>
			
			<form method="post" action="Controller?action=userSignUp" novalidate="novalidate">
				<!-- novalidate in order to be able to run tests correctly -->
				<p>
					<label for="id">User id</label>
					<input type="text" id="id" name="id" required
						value="<c:out value="${user.id != null ? user.id : ''}"/>">
				</p>
				<p>
					<label for="firstName">First Name</label>
					<input type="text" id="firstName" name="firstName" required 
						value="<c:out value="${user.person.firstName != null ? user.person.firstName : ''}"/>">
				</p>
				<p>
					<label for="lastName">Last Name</label>
					<input type="text" id="lastName" name="lastName" required 
						value="<c:out value="${user.person.lastName != null ? user.person.lastName : ''}"/>">
				</p>
				<p>
					<label for="email">Email</label>
					<input type="email" id="email" name="email" required 
						value="<c:out value="${user.person.email != null ? user.person.email : ''}"/>">
				</p>
				<p>
					<label for="password">Password</label>
					<input type="password" id="password" name="password" required>
				</p>
				<p>
					<label for="role">Role</label>
					<select id="role" name="role" required>
						<c:forEach var="role" items="${roles}">
							<option value="${role}" ${sessionScope.user.role eq role ? 'selected' : ''}>${role.name}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<input type="submit" id="signUp" value="Sign Up">
				</p>
			</form>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="userFormSignUp" />
		</jsp:include>
	</div>
</body>
</html>



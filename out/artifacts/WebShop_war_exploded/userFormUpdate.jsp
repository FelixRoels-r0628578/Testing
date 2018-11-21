<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Update User" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Update User" />
		</jsp:include>
		<main>
			<%@include file="partial/errors.jsp" %>
			
			<form method="post" action="Controller?action=userUpdate&id=<c:out value="${user.id}"/>" novalidate="novalidate">
				<!-- novalidate in order to be able to run tests correctly -->
				<p>
					<label for="id">User id</label>
					<input type="text" id="id" name="id" required
						value="<c:out value="${user.id}"/>" disabled>
					<input type="hidden" id="id" name="id" required value="<c:out value="${user.id}"/>">
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
				<c:if test="${sessionScope.user.role.name eq 'Administrator' && param.id != sessionScope.user.id}">
					<p>
						<label for="role">Role</label>
						<select id="role" name="role" required>
							<c:forEach var="role" items="${roles}">
								<option value="${role}" ${user.role eq role ? 'selected' : ''}>${role.name}</option>
							</c:forEach>
						</select>
					</p>
				</c:if>
				<p>
					<input type="submit" id="updateUser" value="Update User">
				</p>
			</form>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="userFormUpdate" />
		</jsp:include>
	</div>
</body>
</html>



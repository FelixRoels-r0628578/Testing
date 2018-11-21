<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Check Password" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Check Password" />
		</jsp:include>
		
		<main>
			<%@include file="partial/errors.jsp" %>
			<form action="Controller?action=userCheckPassword" method="post" novalidate>
				<label for="password">Fill out your password:</label>
				<input type="password" id="password" name="password" required />
				
				<input type="hidden" id="id" name="id" value="<c:out value="${param.id != null ? param.id : id}" />" />
				
				<input type="submit" id="checkPassword" value="Check" />
			</form>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="userCheckPasswordForm" />
		</jsp:include>
	</div>
</body>
</html>
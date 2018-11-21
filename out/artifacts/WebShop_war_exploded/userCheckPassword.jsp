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
			<p id="confirmation">The password is <c:if test="${!correct}">NOT </c:if>correct.</p>
			
			<p>
				<a href="Controller?action=userCheckPasswordForm&id=<c:out value="${id}"/>">Try again</a>
				<a href="Controller?action=userOverview">Back to user overview</a>
			</p>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="userCheckPassword" />
		</jsp:include>
		
	</div>
</body>
</html>
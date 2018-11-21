<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Order Confirmation" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Order Confirmation" />
		</jsp:include>
		<main>
			<%@include file="partial/errors.jsp" %>
			
			<p>Payment received.</p>
			<p>Thank you for your order!</p>
			
			<br>
			
			<p>
				<a href="Controller?action=productOverview">&raquo; Back to the product overview</a>
			</p>
			<p>
				<a href="Controller?action=shopCartOverview">&raquo; Shopping cart (${sessionScope.cart eq null ? 0 : sessionScope.cart.numberOfProductOrdersInCart})</a>
			</p>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="shopOrderConfirmation" />
		</jsp:include>
	</div>
</body>
</html>
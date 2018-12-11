<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Order Overview" />
</jsp:include>

<body>
<div id="container">
	<jsp:include page="partial/header.jsp">
		<jsp:param name="title" value="Order Overview" />
	</jsp:include>
	<main>
		<%@include file="partial/errors.jsp" %>

		<c:choose>
			<c:when test="${sessionScope.cart.productsOrdered eq null || sessionScope.cart.numberOfProductOrdersInCart == 0}">
				<p>There are no orders in your cart yet.</p>
			</c:when>
			<c:otherwise>
				<table>
					<thead>
					<tr>
						<th>ID</th>
						<th>Description</th>
						<th>Price/Unit</th>
						<th>Quantity</th>
						<th>Total Price</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="order" items="${sessionScope.cart.productsOrdered}" >
						<tr>
							<td><c:out value="${order.product.id}"/></td>
							<td><c:out value="${order.product.description}"/></td>
							<td><c:out value="${order.product.price}"/></td>
							<td><c:out value="${order.quantity}"/></td>
							<td><c:out value="${order.totalPrice}"></c:out></td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td class="text-align-right">Total:</td>
						<td>${sessionScope.cart.totalPrice}</td>
					</tr>
					</tfoot>
					<caption>Order Overview</caption>
				</table>
			</c:otherwise>
		</c:choose>
		<p>The order will be delivered on:</p>
		<p>Straat: <c:out value="${adres.straat}"/> </p>
		<p>Nummer:  <c:out value="${adres.nummer}"/> </p>
		<p>Bus: <c:out value="${adres.bus}"/> </p>
		<p>Postcode: <c:out value="${adres.postcode}"/> </p>
		<p>Plaats: <c:out value="${adres.plaats}"/> </p>




		<form action="Controller?action=shopOrderConfirmation" method="post" novalidate>
			<input type="submit" value="Confirm" class="inline-form" />
		</form>

		<p>
			<a href="Controller?action=productOverview">&raquo; Back to the product overview</a>
		</p>
		<p>
			<a href="Controller?action=shopCartOverview">&raquo; Shopping cart (${sessionScope.cart eq null ? 0 : sessionScope.cart.numberOfProductOrdersInCart})</a>
		</p>
	</main>

	<jsp:include page="partial/footer.jsp">
		<jsp:param name="currentPage" value="shopOrderOverview" />
	</jsp:include>
</div>
</body>
</html>
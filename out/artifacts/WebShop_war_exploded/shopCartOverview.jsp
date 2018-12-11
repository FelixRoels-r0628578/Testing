<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Cart Overview" />
</jsp:include>

<body>
<div id="container">
	<jsp:include page="partial/header.jsp">
		<jsp:param name="title" value="Cart Overview" />
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
						<th></th>
						<th>Total Price</th>
						<th></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="order" items="${sessionScope.cart.productsOrdered}" >
						<tr>
							<td><c:out value="${order.product.id}"/></td>
							<td><c:out value="${order.product.description}"/></td>
							<td><c:out value="${order.product.price}"/></td>
							<td>
								<form action="Controller?action=shopCartUpdate" class="inline-form" method="post" id="shopCartUpdate-<c:out value="${order.product.id}"/>" novalidate></form>
								<input type="number" class="inline-form" name="quantity" form="shopCartUpdate-<c:out value="${order.product.id}"/>" value="<c:out value="${order.quantity}"/>">
							</td>
							<td>
								<button type="submit" name="id" value="<c:out value="${order.product.id}"/>" form="shopCartUpdate-<c:out value="${order.product.id}"/>">Change</button>
							</td>
							<td>${order.totalPrice}</td>
							<td><a href="Controller?action=shopCartDelete&id=<c:out value="${order.product.id}"/>">X</a></td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td class="text-align-right">Total:</td>
						<td>${sessionScope.cart.totalPrice}</td>
						<td></td>
					</tr>
					</tfoot>
					<caption>Cart Overview</caption>
				</table>
			</c:otherwise>
		</c:choose>

		<%--Leveringsadres form--%>

		<%@include file="partial/errors.jsp" %>
		<form action="Controller?action=shopOrderOverview" method="post" novalidate>
			<p>
				<label for="straat">Straat </label>
				<input type="text" id="straat" name="straat" required
					   value="<c:out value="${adres.straat != null ? adres.straat : ''}"/>">
			</p>
			<p>
				<label for="nummer">Huisnummer </label>
				<input type="text"   id="nummer" name="nummer" required
					   value="<c:out value="${adres.nummer != null ? adres.nummer : ''}"/>" min="1">
			</p>
			<p>
				<label for="bus">Bus </label>
				<input type="number"  min="1" id="bus" name="bus"
					   value="<c:out value="${adres.bus != null ? adres.bus : ''}"/>">
			</p>
			<p>
				<label for="postcode"  >Postcode </label>
				<input type="number" id="postcode" name="postcode" required
					   value="<c:out value="${adres.postcode != null ? adres.postcode : ''}"/>" min="1000" max="9999">
			</p>
			<p>
				<label for="plaats">Plaats </label>
				<input type="text" id="plaats" name="plaats" required
					   value="<c:out value="${adres.plaats != null ? adres.plaats : ''}"/>">
			</p>



			<input type="submit" value="Pay" class="inline-form"/>
		</form>

		<p>
			<a href="Controller?action=productOverview">&raquo; Back to the product overview</a>
		</p>
	</main>

	<jsp:include page="partial/footer.jsp">
		<jsp:param name="currentPage" value="shopCartOverview" />
	</jsp:include>
</div>
</body>
</html>
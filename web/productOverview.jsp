<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Product Overview" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Product Overview" />
		</jsp:include>
		<main>
			<%@include file="partial/errors.jsp" %>
			
			<c:if test="${sessionScope.user.role eq 'ADMINISTRATOR' }">
				<p>
					<a href="Controller?action=productFormAdd">&raquo; Add a new product</a>
				</p>
			</c:if>
			<p>
				<a href="Controller?action=shopCartOverview">&raquo; Shopping cart (${sessionScope.cart eq null ? 0 : sessionScope.cart.numberOfProductOrdersInCart})</a>
			</p>
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Description</th>
							<th>Price</th>
							<th>Quantity</th>
							<th></th>
							<c:if test="${sessionScope.user.role eq 'ADMINISTRATOR' }">
								<th>Delete</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="product" items="${products}" >
							<tr>
								<c:choose>
									<c:when test="${sessionScope.user.role eq 'ADMINISTRATOR' }">
										<td><a href="Controller?action=productFormUpdate&id=<c:out value="${product.id}"/>"><c:out value="${product.id}"/></a></td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${product.id}"/></td>
									</c:otherwise>
								</c:choose>
								<td><c:out value="${product.description}"/></td>
								<td><c:out value="${product.price}"/></td>
								<td>
									<form action="Controller?action=shopCartAdd" class="inline-form" method="post" id="shopCartAdd-<c:out value="${product.id}"/>" novalidate></form>
									<input type="number" class="inline-form" name="quantity" value="1" form="shopCartAdd-<c:out value="${product.id}"/>">
								</td>
								<td>
									<button type="submit" name="id" value="<c:out value="${product.id}"/>" form="shopCartAdd-<c:out value="${product.id}"/>">Add To Cart</button>
								</td>
								<c:if test="${sessionScope.user.role eq 'ADMINISTRATOR' }">
									<td><a href="Controller?action=productConfirmDelete&id=<c:out value="${product.id}"/>">Delete</a></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
					<caption>Product Overview</caption>
				</table>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="productOverview" />
		</jsp:include>
	</div>
</body>
</html>
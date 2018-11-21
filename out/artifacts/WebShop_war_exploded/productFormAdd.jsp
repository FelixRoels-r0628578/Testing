<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<jsp:include page="partial/head.jsp">
	<jsp:param name="title" value="Add Product" />
</jsp:include>

<body>
	<div id="container">
		<jsp:include page="partial/header.jsp">
			<jsp:param name="title" value="Add Product" />
		</jsp:include>
		<main>
			<a href="Controller?action=productOverview">Back to product overview</a>
			
			<%@include file="partial/errors.jsp" %>
			
			<form method="post" action="Controller?action=productAdd" novalidate="novalidate">
				<!-- novalidate in order to be able to run tests correctly -->
				<p>
					<label for="id">Product ID</label>
					<input type="text" id="id" name="id" required 
						value="<c:out value="${product.id != null ? product.id : ''}"/>">
				</p>
				<p>
					<label for="description">Description</label>
					<input type="text" id="description" name="description" required 
						value="<c:out value="${product.description != null ? product.description : ''}"/>" >
				</p>
				<p>
					<label for="price">Price</label>
					<input type="text" id="price" name="price" required 
						value="<c:out value="${product.price != null ? product.price : ''}"/>" >
				</p>
				<p>
					<input type="submit" id="addProduct" value="Add Product">
				</p>
			</form>
		</main>
		
		<jsp:include page="partial/footer.jsp">
			<jsp:param name="currentPage" value="productFormAdd" />
		</jsp:include>
	</div>
</body>
</html>



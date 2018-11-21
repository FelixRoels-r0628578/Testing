<c:if test="${errors != null}">
	<div class="alert-danger">
		<ul>
			<c:forEach items="${errors}" var="e">
				<li><c:out value="${e.value}"/></li>
			</c:forEach>
		</ul>
	</div>
</c:if>
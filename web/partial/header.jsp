<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
	<h1>
		<span>Web shop</span>
	</h1>
	<nav>
		<ul>
			<li ${param.title eq 'Home' ? 'id=\"actual\"' : ''}><a href="Controller">Home</a></li>
			<c:if test="${sessionScope.user.role eq 'CUSTOMER' || sessionScope.user.role eq 'ADMINISTRATOR'}">
				<li ${param.title eq 'Product Overview' ? 'id=\"actual\"' : ''}><a href="Controller?action=productOverview">Product Overview</a></li>
			</c:if>
			<c:if test="${sessionScope.user.role eq 'ADMINISTRATOR'}">
				<li ${param.title eq 'User Overview' ? 'id=\"actual\"' : ''}><a href="Controller?action=userOverview">User Overview</a></li>
			</c:if>
			<li ${param.title eq 'Sign Up' ? 'id=\"actual\"' : ''}><a href="Controller?action=userFormSignUp">Sign up</a></li>
		</ul>
	</nav>
	<h2>${param.title}</h2>

</header>
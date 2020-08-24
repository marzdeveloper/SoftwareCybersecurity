<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('DIRETTORE')" var="isDirettore" />

<link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet' />
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>
<h2>
	${title}
	<button onclick="console.log('cambiar vista')" type="button" class="btn btn-primary btn-circle"><i class="fa fa-plus"></i>Measure</button>
</h2>
<c:forEach var="m" items="${jobs}">
	<div class="journal-div border border-primary rounded-lg">
		<h4>Job ${m.getKey()}</h4>
		<div class="j-left">
			<div class="container">
				<div class="carousel slide" id="main-carousel-${m.getKey()}">
					<ol class="carousel-indicators">
						<c:forEach var="i1" items="${m.value}">
							<c:forEach var="i2" items="${i1.value}" varStatus="loop">
								<li data-target="#main-carousel-${m.getKey()}" data-slide-to="${loop.index}" class="active"></li>
							</c:forEach>
						</c:forEach>
					</ol><!-- /.carousel-indicators -->
						
					<div class="carousel-inner">
						<c:forEach var="m2" items="${m.value}">
							<c:forEach var="img" items="${m2.value}" varStatus="loop">
								<c:choose>
								    <c:when test="${loop.index == 0}">
								        <div class="carousel-item active">
											<img class="d-block img-fluid" src="../uploads/images/${img}" alt="${img}">
										</div>
								    </c:when>    
								    <c:otherwise>
								        <div class="carousel-item">
											<img class="d-block img-fluid" src="../uploads/images/${img}" alt="${img}">
										</div>
								    </c:otherwise>
								</c:choose>
							</c:forEach>
						</c:forEach>
					</div><!-- /.carousel-inner -->
						
					<a href="#main-carousel-${m.getKey()}" class="carousel-control-prev" data-slide="prev">
						<span class="carousel-control-prev-icon"></span>
						<span class="sr-only" aria-hidden="true">Prev</span>
					</a>
					<a href="#main-carousel-${m.getKey()}" class="carousel-control-next" data-slide="next">
						<span class="carousel-control-next-icon"></span>
						<span class="sr-only" aria-hidden="true">Next</span>
					</a>
				</div><!-- /.carousel -->
			</div><!-- /.container -->
		</div>
		<div class="j-right">
			<iframe src="../uploads/measures/example.pdf#toolbar=0" width="100%" height="100%"></iframe>
		</div>
	</div>
	<br />
	<br />
	<br />
</c:forEach>

<style>
	.journal-div {
		height: 500px;
		width: 100%;
		padding: 10px;
	}

	.j-left {
		float: left;
		height-max: 100%;
		width: 50%;
	}
	
	.j-right {
		float: right;
		width: 50%;
		height: 88%;
		background-color: orange;
	}
	
	.carousel-item {
		height: 420px !important;
		max-height: 420px !important;
	}
</style>
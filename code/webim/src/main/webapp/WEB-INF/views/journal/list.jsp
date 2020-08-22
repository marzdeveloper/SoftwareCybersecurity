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
<c:forEach var = "i" begin = "0" end = "1">
	<div class="journal-div border border-primary rounded-lg">
		<h4>Job ${i}</h4>
		<div class="j-left">
			<div class="container">
				<div class="carousel slide" id="main-carousel-${i}">
					<ol class="carousel-indicators">
						<li data-target="#main-carousel-${i}" data-slide-to="0" class="active"></li>
						<li data-target="#main-carousel-${i}" data-slide-to="1"></li>
						<li data-target="#main-carousel-${i}" data-slide-to="2"></li>
						<li data-target="#main-carousel-${i}" data-slide-to="3"></li>
					</ol><!-- /.carousel-indicators -->
						
					<div class="carousel-inner">
						<div class="carousel-item active">
							<img class="d-block img-fluid" src="https://s19.postimg.cc/qzj5uncgj/slide1.jpg" alt="">
						</div>
						<div class="carousel-item">
							<img class="d-block img-fluid" src="https://s19.postimg.cc/lmubh3h0j/slide2.jpg" alt="">
						</div>
						<div class="carousel-item">
							<img class="d-block img-fluid" src="https://s19.postimg.cc/99hh9lr5v/slide3.jpg" alt="">
						</div>
						<div class="carousel-item">
							<img src="https://s19.postimg.cc/nenabzsnn/slide4.jpg" alt="" class="d-block img-fluid">
						</div>
					</div><!-- /.carousel-inner -->
						
					<a href="#main-carousel-${i}" class="carousel-control-prev" data-slide="prev">
						<span class="carousel-control-prev-icon"></span>
						<span class="sr-only" aria-hidden="true">Prev</span>
					</a>
					<a href="#main-carousel-${i}" class="carousel-control-next" data-slide="next">
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
		height: 450px;
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
</style>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>

<!-- Modal -->
<div class="modal-header">
	<h5 class="modal-title" id="viewModalLabel">${job.measure}</h5>
	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<div class="journal-div border border-primary rounded-lg">
		<div class="j-left">
			<div class="container">
				<div class="carousel slide" id="main-carousel-0">
					<ol class="carousel-indicators">
						<c:forEach var="img" items="${job.images}" varStatus="loop">
							<li data-target="#main-carousel-0" data-slide-to="${loop.index}" class="active"></li>
						</c:forEach>
					</ol><!-- /.carousel-indicators -->
						
					<div class="carousel-inner">
						<c:forEach var="img" items="${job.images}" varStatus="loop">
							<c:choose>
							    <c:when test="${loop.index == 0}">
							        <div class="carousel-item active">
										<img class="d-block img-fluid j-modal-img" src="../uploads/images/${img}" alt="${img}">
										<button onclick="getMap('${img}')" type="button" class="btn btn-info btn-circle btn-relative-gps" data-toggle="modal" data-target="#mapModal"><i class="fa fa-eye"></i></button>
									</div>
							    </c:when>
							    <c:otherwise>
							        <div class="carousel-item">
										<img class="d-block img-fluid j-modal-img" src="../uploads/images/${img}" alt="${img}">
										<button onclick="getMap('${img}')" type="button" class="btn btn-info btn-circle btn-relative-gps" data-toggle="modal" data-target="#mapModal"><i class="fa fa-eye"></i></button>
									</div>
							    </c:otherwise>
							</c:choose>
						</c:forEach>
					</div><!-- /.carousel-inner -->
					
					<a href="#main-carousel-0" class="carousel-control-prev" data-slide="prev">
						<span class="carousel-control-prev-icon"></span>
						<span class="sr-only" aria-hidden="true">Prev</span>
					</a>
					<a href="#main-carousel-0" class="carousel-control-next" data-slide="next">
						<span class="carousel-control-next-icon"></span>
						<span class="sr-only" aria-hidden="true">Next</span>
					</a>
				</div><!-- /.carousel -->
			</div><!-- /.container -->
		</div>
		<div class="j-right">
			<iframe src="../uploads/measures/${job.measure}#toolbar=0" width="100%" height="100%"></iframe>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>

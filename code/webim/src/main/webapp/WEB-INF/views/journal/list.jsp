<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('DIRETTORE')" var="isDirettore" />

<link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet' />
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>
<h2>
	${title}
	<a href="/journal/newJob" type="button" class="btn btn-primary btn-circle"><i class="fa fa-plus"></i>Measure</a>
</h2>
<c:forEach var="j" items="${jobs}">
	<div class="journal-div border border-primary rounded-lg">
		<h4>Job ${j.getJobID() + 1} - ${j.getDate()}</h4>
		<div class="j-left">
			<div class="container">
				<div class="carousel slide" id="main-carousel-${j.getJobID() + 1 }">
					<ol class="carousel-indicators">
						<c:forEach var="i" items="${j.getImages()}" varStatus="loop">
							<li data-target="#main-carousel-${j.getJobID() + 1}" data-slide-to="${loop.index}" class="active"></li>
						</c:forEach>
					</ol><!-- /.carousel-indicators -->
						
					<div class="carousel-inner">
						<c:forEach var="img" items="${j.getImages()}" varStatus="loop">
							<c:choose>
								  <c:when test="${loop.index == 0}">
								       <div class="carousel-item active">
										<img class="d-block img-fluid" src="../uploads/images/${img}" alt="${img}">
										<button onclick="getMap('${img}')" type="button" class="btn btn-info btn-circle btn-relative-gps" data-toggle="modal" data-target="#mapModal"><i class="fa fa-eye"></i></button>
									</div>
								   </c:when>    
								   <c:otherwise>
								       <div class="carousel-item">
										<img class="d-block img-fluid" src="../uploads/images/${img}" alt="${img}">
										<button onclick="getMap('${img}')" type="button" class="btn btn-info btn-circle btn-relative-gps" data-toggle="modal" data-target="#mapModal"><i class="fa fa-eye"></i></button>
									</div>
								   </c:otherwise>
							</c:choose>
						</c:forEach>
					</div><!-- /.carousel-inner -->
					
					<a href="#main-carousel-${j.getJobID() + 1}" class="carousel-control-prev" data-slide="prev">
						<span class="carousel-control-prev-icon"></span>
						<span class="sr-only" aria-hidden="true">Prev</span>
					</a>
					<a href="#main-carousel-${j.getJobID() + 1}" class="carousel-control-next" data-slide="next">
						<span class="carousel-control-next-icon"></span>
						<span class="sr-only" aria-hidden="true">Next</span>
					</a>
				</div><!-- /.carousel -->
			</div><!-- /.container -->
		</div>
		<div class="j-right">
			<iframe src="../uploads/measures/${j.getMeasure()}#toolbar=0" width="100%" height="100%"></iframe>
		</div>
	</div>
	<br />
	<br />
	<br />
</c:forEach>

<!-- Modal Map -->
<div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="mapModalLabel" aria-hidden="true">
  	<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    	<div class="modal-content modal-content-map"></div>
  	</div>
</div>

<script type="text/javascript">
	var quale_link = 1;
	$(document).ready( function () {
	    $('#tableImage').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false, "targets": [4, 5] }
	    	  ]
	    });
	    
	    $("input:file").change(function(objEvent) {
	        var file = $(this)[0].files[0];
	        var nameFile = "Choose file";
	        if (file) {
	        	nameFile = file.name;
	        }
	        $('#upload-label').text(nameFile);
	    });
	} );

    function getMap(name) {
    	$(".modal-content-map").empty();
    	$.get("<c:url value="/journal/"/>"+name+"/getMap", function(data, status){
   	    	$(".modal-content-map").append(data);
   	    });
    }
    
    document.addEventListener("contextmenu", function (e) {
        e.preventDefault();
    }, false);
</script>
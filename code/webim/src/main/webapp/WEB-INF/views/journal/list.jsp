<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('DIRETTORE')" var="isDirettore" />

<link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet' />
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>
<h2>
	${title}
</h2>
<div>
   	<form action="<c:url value="/journal/createJournal" />" method="post">
        <div class="form-group">
            <label for="images">Select Images</label>
            <select id="images" name="images" multiple class="form-control">
                <option value="image1">Image1</option>
                <option value="image2">Image2</option>
                <option value="image3">Image3</option>
                <option value="image4">Image4</option>
                <option value="image5">Image5</option>
                <option value="image6">Image6</option>
                <option value="image7">Image7</option>
            </select>
        </div>
        <div class="form-group">
            <label for="measure">Measure</label>
            <input type="text" name="measure" id="measure" class="form-control"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
    </form>
</div>
     	
<!-- Modal Image -->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="viewModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
    	<div class="modal-content modal-content-image"></div>
	</div>
</div>

<!-- Modal Measure -->
<div class="modal fade" id="measureModal" tabindex="-1" role="dialog" aria-labelledby="measureModalLabel" aria-hidden="true">
  	<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    	<div class="modal-content modal-content-measure"></div>
  	</div>
</div>

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
	    		{ "orderable": false, "targets": [3, 4] }
	    	  ]
	    });
	} );

    function getDetails(id) {
    	$(".modal-content-image").empty();
       	$.get("<c:url value="/image/getDetails/"/>"+id, function(data, status){
   	    	$(".modal-content-image").append(data);
   	    });
    }
    
    function getMeasure(id) {
    	$(".modal-content-measure").empty();
       	$.get("<c:url value="/image/getMeasure/"/>"+id, function(data, status){
   	    	$(".modal-content-measure").append(data);
   	    });
    }
    
    
    function getMap(id) {
    	$(".modal-content-map").empty();
       	$.get("<c:url value="/image/getMap/"/>"+id, function(data, status){
   	    	$(".modal-content-map").append(data);
   	    });
    }

    function measureImages() {
    	var selected = [];
    	$('.form-check-input:checked').each(function() {
    	    selected.push($(this).attr('value'));
    	});
    	
    	if (selected.length >= 6) {
    		$.ajax({
                type: "POST",
                url: "<c:url value="/image/measureImages"/>",
                data: { images: selected },
                success: function (response) {
                	showAlertMsg(response);
                },
                error: function (response) {
                }
            });
    	} else {
    		var json = {"success":false, "msg":"Please select 6 images minimum"}
    		showAlertMsg(json);
    	}
    }

    function deleteData(id) {
        $.ajax({
            type: "POST",
            url: "<c:url value="/image/deleteData"/>",
            data: { id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
    
    document.addEventListener("contextmenu", function (e) {
           e.preventDefault();
       }, false);
</script>
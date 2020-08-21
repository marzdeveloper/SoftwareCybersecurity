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
   	<table id="tableImage" class="table table-striped table-bordered" style="width:100%">
   		<thead>
            <tr>
                <th>Name</th>
                <th>Date</th>
                <th>GPS</th>
                <th style="text-align:center;">Measures <button onclick="setMeasure()" type="button" class="btn btn-primary btn-circle"><i class="fa fa-plus"></i>Measure</button></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
	        <c:forEach items="${images}" var="i">
	        	<tr>
	                <td><button onclick="getDetails(${i.image_id})" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#viewModal"><i class="fa fa-eye"></i></button>${i.name}</td>
	                <td>${i.data_caricamento}</td>
	                <td><button onclick="getMap(${i.image_id})" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#mapModal"><i class="fa fa-eye"></i></button>${i.GPS}</td>
                	<c:if test="${not empty i.measure_id.measure_id}">
		                <td style="text-align:center;">
                			<button onclick="getMeasure(${i.measure_id.measure_id})" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#measureModal"><i class="fa fa-eye"></i></button>
	               		</td>
               		</c:if>
               		<c:if test="${empty i.measure_id.measure_id}">
	               		<td for="check_${i.image_id}" style="text-align:center;"><span class="form-check"><input class="form-check-input" type="checkbox" value="${i.image_id}" id="check_${i.image_id}" /></span></td>
               		</c:if>
	                <td style="text-align:center;"><button onclick="deleteData(${i.image_id})" type="button" class="btn btn-danger btn-circle"><i class="fa fa-trash"></i></button></td>
	            </tr>
			</c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <th>Name</th>
                <th>Date</th>
                <th>GPS</th>
                <th>Measures</th>
                <th></th>
            </tr>
        </tfoot>
    </table>
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

<!-- Modal Upload Measure -->
<div class="modal fade" id="upMeasureModal" tabindex="-1" role="dialog" aria-labelledby="upMeasureModalLabel" aria-hidden="true">
  	<div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    	<div class="modal-content modal-content-up-measure">
    		<div class="modal-header">
			  	<h5 class="modal-title" id="upMeasureModalLabel">Associate Measures</h5>
			  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			    	<span aria-hidden="true">&times;</span>
			  	</button>
			</div>
			
			<div class="modal-body">
	            <form id="formUploadMeasure" enctype="multipart/form-data">
	                <div class="input-group mb-3 px-2 py-2 rounded-pill bg-white shadow-sm">
		                <input id="upload" type="file" name="measureFile" class="form-control border-0 custom-file-input">
		                <label id="upload-label" for="upload" class="font-weight-light text-muted">Choose file</label>
		                <div class="input-group-append">
		                    <label for="upload" class="btn btn-light m-0 rounded-pill px-4"> <i class="fa fa-close mr-2 text-muted"></i><small class="text-uppercase font-weight-bold text-muted">Choose file</small></label>
	                	<button onclick="measureImages()" type="button" class="btn btn-primary">Upload</button>
		                </div>
		            </div>
	            </form>
			</div>
			<div class="modal-footer">
			 	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
    	</div>
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
	    
	    $("input:file").change(function(objEvent) {
	        var file = $(this)[0].files[0];
	        var nameFile = "Choose file";
	        if (file) {
	        	nameFile = file.name;
	        }
	        $('#upload-label').text(nameFile);
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
    
    function setMeasure() {
    	var selected = [];
    	$('.form-check-input:checked').each(function() {
    	    selected.push($(this).attr('value'));
    	});
    	
    	if (selected.length > 5) {
    		$('#upMeasureModal').modal('show');
    	} else {
    		var json = {"success":false, "msg":"Please select 6 images minimum"}
    		showAlertMsg(json);
    	}
    }

    function measureImages() {
    	var selected = [];
    	$('.form-check-input:checked').each(function() {
    	    selected.push($(this).attr('value'));
    	});
    	
    	var objFile = $("input:file")[0].files[0];
    	var form = new FormData();
    	form.append('measure', objFile)
    	form.append('images', selected)
    	
    	if (selected.length > 5) {
    		$.ajax({
                url: "<c:url value="/image/measureImages"/>",
    			data: form,
                cache: false,
    		    contentType: false,
    		    processData: false,
    		    method: 'POST',
                type: "POST",
                success: function (response) {
                	$('#upMeasureModal').modal('hide');
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
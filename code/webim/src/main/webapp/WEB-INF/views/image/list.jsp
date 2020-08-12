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
                <th></th>
                <th style="text-align:center;">Measures <button onclick="measureImages()" type="button" class="btn btn-primary btn-circle"><i class="fa fa-plus"></i>Measure</button></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
	        <c:forEach items="${images}" var="i">
	        	<tr>
	                <td>${i.name}</td>
	                <td>${i.data_caricamento}</td>
	                <td>${i.GPS}</td>
	                <td style="text-align:center;"><button onclick="getDetails(${i.image_id})" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#viewModal"><i class="fa fa-eye"></i></button></td>
                	<c:if test="${not empty i.measure_id.measure_id}">
		                <td style="text-align:center;">
                			<button onclick="getMeasure(${i.measure_id.measure_id})" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#measureModal"><i class="fa fa-eye"></i></button>
	               		</td>
               		</c:if>
               		<c:if test="${empty i.measure_id.measure_id}">
	               		<td style="text-align:center;"><span class="form-check"><input class="form-check-input" type="checkbox" value="${i.image_id}" id="check_${i.image_id}" /></span></td>
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
                <th></th>
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

<script type="text/javascript">
	var quale_link = 1;
	$(document).ready( function () {
	    $('#tableImage').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false, "targets": [3, 4, 5] }
	    	  ]
	    });
	} );

    function getDetails(id) {
    	$(".modal-content-image").empty();
       	$.get("<c:url value="/${title}/getDetails/"/>"+id, function(data, status){
   	    	$(".modal-content-image").append(data);
   	    });
    }
    
    function getMeasure(id) {
    	$(".modal-content-measure").empty();
       	$.get("<c:url value="/${title}/getMeasure/"/>"+id, function(data, status){
   	    	$(".modal-content-measure").append(data);
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
                url: "<c:url value="/${title}/measureImages"/>",
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
            url: "<c:url value="/${title}/deleteData"/>",
            data: { id: id },
            success: function (response) {
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
</script>
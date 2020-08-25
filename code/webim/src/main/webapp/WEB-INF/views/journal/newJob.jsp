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
                <th>Measure</th>
                <th>Images</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
	        <c:forEach items="${jobs}" var="j">
	        	<tr>
	                <td style="vertical-align:middle">${j.measure}</td>
	                <td style="vertical-align:middle">
						<c:forEach items="${j.images}" var="i">
			                <p>${i}</p> 
						</c:forEach>
	                </td>
	                <td style="vertical-align:middle; text-align:center"><button onclick="getDetails('${j.measure}')" type="button" class="btn btn-info btn-circle" data-toggle="modal" data-target="#viewModal"><i class="fa fa-eye"></i></button></td>
	                <td style="vertical-align:middle; text-align:center"><button onclick="createJob('${j.measure}')" type="button" class="btn btn-primary btn-circle"><i class="fa fa-plus"></i></button></td>
	            </tr>
			</c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <th>Measure</th>
                <th>Images</th>
                <th></th>
                <th></th>
            </tr>
        </tfoot>
    </table>
</div>

<!-- Modal Image -->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog" aria-labelledby="viewModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-xl" role="document">
    	<div class="modal-content modal-content-job"></div>
	</div>
</div>

<script type="text/javascript">
	var quale_link = 1;
	$(document).ready( function () {
	    $('#tableImage').DataTable({
	    	"columnDefs": [
	    		{ "orderable": false, "targets": [2, 3] }
	    	  ]
	    });
	} );

    function getDetails(name) {
    	$(".modal-content-job").empty();
       	$.get("<c:url value="/journal/"/>"+name + "/getDetails", function(data, status){
   	    	$(".modal-content-job").append(data);
   	    });
    }

    function createJob(name) {
    	if (name) {
    		$.ajax({
                url: "<c:url value="/journal/createJournal"/>",
    			data: { measure: name },
    		    method: 'POST',
                type: "POST",
                success: function (response) {
                	showAlertMsg(response);
                },
                error: function (response) {
                }
            });
    	} else {
    		var json = {"success":false, "msg":"Error selecting measure"}
    		showAlertMsg(json);
    	}
    }
    
    document.addEventListener("contextmenu", function (e) {
           e.preventDefault();
       }, false);
</script>
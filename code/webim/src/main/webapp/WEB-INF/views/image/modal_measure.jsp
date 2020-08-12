<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="measureModalLabel">${device.brand} ${device.model} - ${device.serialNumber}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>
<div class="modal-body">
	<div id="formContent">
	   	<div class="form-group">
	    	<label for="dev-brand">Brand</label>
	    	<input type="text" class="form-control" value="${device.brand}" id="dev-brand" placeholder="">
	  	</div>
   		<div class="form-group">
	    	<label for="dev-doc">Document</label>
	    	<input type="text" class="form-control" value="${device.document}" id="dev-doc" placeholder="">
	  	</div>
     	<div class="form-group">
	    	<label for="dev-model">Model</label>
	    	<input type="text" class="form-control" value="${device.model}" id="dev-model" placeholder="">
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-reason">Reason</label>
	    	<textarea class="form-control" value="${device.reason}" id="dev-reason" rows="3"></textarea>
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-checkIn">CheckIn</label>
	  		<input class="form-control" data-date-format="yyyy/mm/dd" value="${device.checkIn}" id="dev-checkIn" class="datepicker">
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-checkOut">CheckOut</label>
	  		<input class="form-control" data-date-format="yyyy/mm/dd" value="${device.checkOut}" id="dev-checkOut" class="datepicker">
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-serial">Serial Number</label>
	    	<input type="text" class="form-control" value="${device.serialNumber}" id="dev-serial" placeholder="">
	  	</div>
	  	<div class="form-group">
	    	<label for="dev-sender">Sender</label>
		  	<select class="form-control" id="dev-sender">
		      	<option value="">No sender assigned</option>
		      	<c:if test="${fn:length(senders) > 0}">
		      		<c:forEach items="${senders}" var="s">
		      			<c:choose>
					    	<c:when test="${s[2] == '1'}">
					    		<option value="${s[0]}" selected>${s[1]}</option>
					    	</c:when>    
					    	<c:otherwise>
					    		<option value="${s[0]}">${s[1]}</option>
					    	</c:otherwise>
					  	</c:choose>
				  	</c:forEach>
		      	</c:if>
		    </select>
	  	</div>   	
	</div>
</div>
<div class="modal-footer">
 	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
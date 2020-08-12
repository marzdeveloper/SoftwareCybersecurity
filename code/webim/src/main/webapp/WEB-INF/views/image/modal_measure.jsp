<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!-- Modal -->
<div class="modal-header">
  	<h5 class="modal-title" id="measureModalLabel">${name}</h5>
  	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
  	</button>
</div>

<div class="modal-body">
	<div class="modal-iframe" style="width:100%; height:75vh;">
		<iframe src="../uploads/measures/${name}#toolbar=0" width="100%" height="100%"></iframe>
	</div>
</div>
<div class="modal-footer">
 	<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
</div>
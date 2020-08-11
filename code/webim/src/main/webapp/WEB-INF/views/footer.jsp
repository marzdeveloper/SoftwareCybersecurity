<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="text-align:center"></div>
<script type="text/javascript">
	$(document).ready( function () {
	    var alert = ${alertMsg} + "";
	    var type = ${typeMsg} + "";
	    if (alert) {
	    	if (type > 0) {
			    var $alertMsg1 = $('<div id="alert_error" class="alert alert-success alert-flotante temp_alert">' + alert + '</div>');	    		
	    	} else {
	    		var $alertMsg1 = $('<div id="alert_error" class="alert alert-danger alert-flotante temp_alert">' + alert + '</div>');
	    	}
	    	$('.msg_alert').append($alertMsg1);
	    	setTimeout(function() {
	    		$alertMsg1.fadeOut( "slow", function() {
					$alertMsg1.remove();
	    		  });
	    	}, 3000);
		}
		
	    $("#sidebar").mCustomScrollbar({
	        theme: "minimal"
	    });

	    $('#sidebarCollapse').on('click', function () {
	        $('#sidebar, #content').toggleClass('active');
	        $('.collapse.in').toggleClass('in');
	        $('a[aria-expanded=true]').attr('aria-expanded', 'false');
	    });
	} );

	function showAlertMsg(response) {
		if(response.success) {
			window.location.replace(window.location.origin + window.location.pathname + '?msg="' + response.msg + '"' + '&resp=1');
		} else {
			var $alertMsg = $('<div id="alert_error" class="alert alert-danger alert-flotante temp_alert">' + response.msg + '</div>');
			$('.msg_alert').append($alertMsg);
			setTimeout(function() {
				$alertMsg.fadeOut( "slow", function() {
					$alertMsg.remove();
				});
			}, 3000);
		}
	}

	$('#newModal').on('hidden.bs.modal', function (e) {
		$(".modal-content-new").empty();
	})
	
	$('#editModal').on('hidden.bs.modal', function (e) {
		$(".modal-content-edit").empty();
	})
	
	$('#linkModal').on('hidden.bs.modal', function (e) {
		$(".modal-content-link").empty();
	})
</script>
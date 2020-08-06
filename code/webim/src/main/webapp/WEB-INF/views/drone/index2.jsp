<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin" />

<link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet' />
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>
<h2>
	${title}
	<c:if test="${isAdmin}"><button onclick="showNew()" type="button" class="btn btn-primary btn-circle" data-toggle="modal" data-target="#newModal"><i class="fa fa-plus"></i></button></c:if>
</h2>

<div class="container py-5">
    
    <header class="text-white text-center">
        <h1 class="display-4">Upload your images</h1>
    </header>

    <div class="row py-4">
        <div class="col-lg-6 mx-auto">
        	
        	<form method="post" enctype="multipart/form-data" action="/drone/uploadImage">
        		<input type="file" name="imageFiles" multiple/>
        		<input type="submit" value="Upload" />
        	</form>
        
            <!-- Upload image input-->
            <div class="input-group mb-3 px-2 py-2 rounded-pill bg-white shadow-sm">
                <input id="upload" type="file" multiple class="form-control border-0">
                <label id="upload-label" for="upload" class="font-weight-light text-muted">Choose file</label>
                <div class="input-group-append">
                	<button onclick="sendImages()" type="button" class="btn btn-primary btn-circle"><i class="fa fa-close"></i></button>
                    <label for="upload" class="btn btn-light m-0 rounded-pill px-4"> <i class="fa fa-close mr-2 text-muted"></i><small class="text-uppercase font-weight-bold text-muted">Choose file</small></label>
                </div>
            </div>

            <!-- Uploaded image area-->
            <p class="font-italic text-white text-center">The image uploaded will be rendered inside the box below.</p>
            <div id="imagesResultBox" class="image-area mt-4"></div>
        </div>
    </div>
</div>

<style>
	/*
	*
	* ==========================================
	* CUSTOM UTIL CLASSES
	* ==========================================
	*
	*/
	#upload {
	    opacity: 0;
	}
	
	#upload-label {
	    position: absolute;
	    top: 50%;
	    left: 1rem;
	    transform: translateY(-50%);
	}
	
	.image-area {
	    border: 2px dashed rgba(255, 255, 255, 0.7);
	    padding: 1rem;
	    position: relative;
	}
	
	.image-area::before {
	    color: #fff;
	    font-weight: bold;
	    text-transform: uppercase;
	    position: absolute;
	    top: 50%;
	    left: 50%;
	    transform: translate(-50%, -50%);
	    font-size: 0.8rem;
	    z-index: 1;
	}
	
	.image-area img {
	    z-index: 2;
	    position: relative;
	}
	
	/*
	*
	* ==========================================
	* FOR DEMO PURPOSES
	* ==========================================
	*
	*/
	body {
	    min-height: 100vh;
	    background-color: #757f9a;
	    background-image: linear-gradient(147deg, #757f9a 0%, #d7dde8 100%);
	}
	
	.box-img {
	  position: relative;
	  padding-bottom: 15px;
	}
	
	.btn-circle { 
	  border-radius: 50%;
	  opacity: 0.3;
	  position: absolute;
	  top: 0px;
	  right: 0px;
	  z-index: 5;
	}
	
	.btn-circle:hover {
	  opacity: 1;
	}
</style>

<script>
	var img_id = 0;
	var list_images = {};
	var input = document.getElementById( 'upload' );
	
	/*  ==========================================
	    SHOW UPLOADED IMAGE
	* ========================================== */
	$(document).ready( function () {
	  if(window.File && window.FileList && window.FileReader)
	  {
	    var filesInput = document.getElementById("upload");
	    filesInput.addEventListener("change", function(e) {
		    
	      var files = e.target.files; //FileList object
	
	      for(var i = 0; i< files.length; i++)
	      {
	        var file = files[i];
	
	        //Only pics
	        if(!file.type.match('image'))
	          continue;
	        
	        var picReader = new FileReader();
	        picReader.addEventListener("load", function(e){
	        	img_id++;
	          	var img = img_id + '&%&' + e.target.result + "&%&" + "name"
	        	list_images[img_id] = img;
	        
	        	$('#imagesResultBox')
	          	.append('<div id="imageResult_' + img_id + '" class="box-img"><img src="' + e.target.result + '" class="img-fluid rounded shadow-sm mx-auto d-block imageResult"><button class="btn btn-circle" onclick="removeImage(' + img_id + ')"><i class="fa fa-close"></i></button></div>')
	        });
	
	        //Read the image
	        picReader.readAsDataURL(file);
	      }                               
	
	    });
	  }
	  else
	  {
	    console.log("Your browser does not support File API");
	  }
	});
	
	function removeImage(id) {
		$("#imageResult_" + id).remove();
		delete list_images[id];
	}

	function sendImages() {
        console.log("hola");
        console.log("list_images: ");
        console.log(list_images);

        var array = [];
        $.each(list_images, function(i, v) {
        	array.push(v);
        });
        console.log(array);
        
    	$.ajax({
            type: "POST",
            url: "<c:url value="/drone/uploadImages"/>",
            data: { images: array },
            success: function (response) {
                console.log(response);
            	showAlertMsg(response);
            },
            error: function (response) {
            }
        });
    }
</script>

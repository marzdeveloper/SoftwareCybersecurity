<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet' />
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>

<body>
<div class="container">
    <h2>${title}</h2>
    <br /><br />
    <div class="form-group">
         <a href="<c:url value="/journal" />"><button type="submit" class="btn btn-primary">Gestione Blockchain</button></a>
   	</div>
    <br /><br />
    <div class="form-group">
         <a href="<c:url value="/image" />"><button type="submit" class="btn btn-primary">Gestione Immagini</button></a>
    </div>
</div>
</body>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="true"%>

<sec:authorize access="hasRole('DIRETTORE')" var="isDirettore" />
<sec:authorize access="hasRole('DRONE')" var="isDrone" />

<!-- Sidebar  -->
<nav id="sidebar">
    <div class="sidebar-header logo-home">
        <h3>WEBIM</h3>
    </div>
    <ul class="list-unstyled components">
    	<c:if test="${isDirettore}">
	       	<li>
            	<a href="<c:url value="/journal" />">Gestione blockchain</a>
        	</li>
        	<li>
            	<a href="<c:url value="/image" />">Gestione immagini</a>
        	</li>
	    </c:if>
	</ul>
</nav>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet' />
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" ></script>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <title>Quorum Message Board</title>
</head>
<body>
<div class="container">
    <h2>Message Board</h2>
    <br /><br />
        <form action="<c:url value="/threads" />" method="post">
            <div class="form-group">
                <label for="threadParticipants">Select Thread Participants</label>
                <select id="threadParticipants" name="threadParticipants" multiple class="form-control">
                    <option value="node1">Node1</option>
                    <option value="node2">Node2</option>
                    <option value="node3">Node3</option>
                    <option value="node4">Node4</option>
                    <option value="node5">Node5</option>
                    <option value="node6">Node6</option>
                    <option value="node7">Node7</option>
                </select>
            </div>
            <div class="form-group">
                <label for="message">Your message</label>
                <input type="text" name="message" id="message" class="form-control"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Start Thread</button>
            </div>
        </form>
    <br /><br />
    <h2>My Threads</h2>
    <c:forEach items="${threadModels}" var="threadModel">
            <h6>${threadModel.getKey().toUpperCase()}</h6>
        <c:forEach items="${threadModel.getValue().getThreadMessages()}" var="threadMessageMap">
                <c:forEach items="${threadMessageMap}" var="threadMessage">
                	<ul>
                		<li>${threadMessage.getKey()} : ${threadMessage.getValue()}</li>
                	</ul>
                </c:forEach>
        </c:forEach>
    </c:forEach>
        <form action="<c:url value="/updateThreads" />" method="post">
            <input type="hidden" name="contractAddress" th:value="${threadModel.getValue().getContractAddress()}" />
            <input type="hidden" name="threadParticipants" th:value="${threadModel.getKey()}" />
            <div class="form-group">
                <label for="message">New Message</label>
                <input type="text" name="newMessage" id="newMessage" class="form-control"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Update Thread</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
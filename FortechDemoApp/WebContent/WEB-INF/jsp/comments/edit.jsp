<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="../../stylesheets/main.css"></link>
<title>Edit Comment</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <div class="row">
        <h1 style="text-align: center;">Edit Comment</h1>
        <div style="width: 30%; margin: 25px auto;">
            <form:form method="POST" action="${contextName}/comments/editComment">
            	<div class="form-group" hidden>
                    <form:input class="form-control" type="text" path="id" placeholder="id" value="${comment.getId()}"/>
                </div>
                <div class="form-group">
                    <form:input class="form-control" type="text" path="text" placeholder="text" value="${comment.getText()}"/>
                </div>
                <div class="form-group">
                    <button class="btn btn-lg btn-primary btn-block">Submit!</button>
                </div>
            </form:form>
            <a href="<%= contextName %>/campgrounds/details?campgroundId=${comment.getCampground().getId()}">Go Back</a>
        </div>
    </div>
</div>
</body>
</html>
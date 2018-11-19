<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="../../stylesheets/main.css"></link>
<title>Campgrounds</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <header class="jumbotron">
        <div class=container>
            <h1>Welcome to FortechDemoApp</h1>
            <p>View out hand-picked campgrounds from all over the world</p>
            <p>
                <a class="btn btn-primary btn-large" href="<%= contextName %>/campgrounds/create">Add New Campground</a>
            </p>
        </div>
    </header>
    
    <div class="row text-center" style="display:flex; flex-wrap: wrap;">
        <c:forEach var="campground" items="${campgrounds}" >
            <div class="col-md-3 col-sm-6">
                <div class="thumbnail">
                    <img src="${campground.getImage()}"></img>
                    <div class="caption">
                        <h4>${campground.getName()}</h4>
                    </div>
                    <p>
                        <a href="<%= contextName %>/campgrounds/details?campgroundId=${campground.getId()}" class="btn btn-primary">More Info</a>
                    </p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
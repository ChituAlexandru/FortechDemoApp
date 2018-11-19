<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${campground.getName()}</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="../../stylesheets/main.css"></link>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-3">
            <p class="lead">YelpCamp</p>
            <div class="list-group">
                <li class="list-group-item active">Info 1</li>
                <li class="list-group-item">Info 2</li>
                <li class="list-group-item">Info 3</li>
            </div>
        </div>
        <div class="col-md-9">
            <div class="thumbnail">
                <img class="img-responsive" src="${campground.getImage()}"></img>
                <div class="caption-full">
                    <h4 class="pull-right">$9.00/Night</h4>
                    <h4><a>${campground.getName()}</a></h4>
                    <p>${campground.getDescription()}</p>
                    <p>
                        <em>Submitted By ${campground.getAuthor().getUsername()}</em>
                    </p>
                    <c:if test = "${loggedUser.getUsername() == null }">
                        <a class="btn btn-xs btn-warning" href="<%= contextName %>/campgrounds/edit?campgroundId=${campground.getId()}">Edit</a>
                        <form id="delete-form" action="<%= contextName %>/campgrounds/delete?campgroundId=${campground.getId()}" method="POST">
                            <button class="btn btn-xs btn-danger">Delete</button>
                        </form>
                    </c:if>
                </div>
            </div>
            <div class="well">
                <div class="text-right">
                    <a class="btn btn-success" href="<%= contextName %>/comments/create?campgroundId=${campground.getId()}">Add New Comment</a>
                </div>
                <hr></hr>
                <c:forEach var="comment" items="${campground.getComments()}" >
                    <div class="row">
                        <div class="col-md-12">
                            <strong>${comment.getCommentAuthor().getUsername()}</strong>
                            <span class="pull-right">10 days ago</span>
                            <p>
                                ${comment.getText()}
                            </p>
                            <c:if test = "${loggedUser.getUsername() == null }">
                            <a class="btn btn-xs btn-warning" 
                                href="<%= contextName %>/comments/edit?commentId=${comment.getId()}">
                                Edit
                            </a>
                            <form id="delete-form" action="<%= contextName %>/comments/delete?commentId=${comment.getId()}" method="POST">
                                <input type="submit" class="btn btn-xs btn-danger" value="Delete">
                            </form>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
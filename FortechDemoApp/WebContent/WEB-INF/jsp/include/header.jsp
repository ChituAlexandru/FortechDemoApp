<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FortechDemoApp</title>
<% String contextName = request.getContextPath(); %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="../../stylesheets/main.css"></link>
</head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="<%= contextName %>/campgrounds/show">FortechDemoApp</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <c:if test = "${loggedUser.getUsername() == null }">
                            <li><a href="/login">Login</a></li>
                            <li><a href="/register">Sign Up</a></li>
                        </c:if>
                        <c:if test = "${loggedUser.getUsername() != null }">
                            <li><a href="#">Signed In As ${loggedUser.getUsername()}</a></li>
                            <li><a href="/logout">Logout</a></li>
                        </c:if>
                    </ul> 
                </div>
            </div>
        </nav>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns:sitemesh="http://www.sitemesh.org/schema/sitemesh-config">
    <head>
        <title><sitemesh:write property='title' /></title>

        <link href="<c:url value="/res/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
    </head>

    <body>
        <sitemesh:write property='body' />

        <script src="<c:url value="/res/angular/angular.min.js" />"></script>
        <script src="<c:url value="/res/angular-bootstrap/ui-bootstrap.min.js" />"></script>
    </body>
</html>

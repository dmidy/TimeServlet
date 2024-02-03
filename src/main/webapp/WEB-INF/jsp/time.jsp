<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Current Time</title>
</head>
<body>
    <h1>Current server time: <%=request.getAttribute("currentTime")%> <%=request.getAttribute("timezone")%></h1>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Invalid Timezone</title>
</head>
<body>
    <h1>Error: Invalid Timezone</h1>
    <p>The timezone parameter provided ("<%= request.getAttribute("invalidTimezone") %>") is invalid. Please provide a valid timezone.</p>
</body>
</html>
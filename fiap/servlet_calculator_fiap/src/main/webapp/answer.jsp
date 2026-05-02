<jsp:useBean id="answer" scope="request" type="java.lang.Integer"/>
<%--
  Created by IntelliJ IDEA.
  User: marcu
  Date: 26/02/2025
  Time: 06:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Calc Result</title>
</head>
<body>
    <h1>Operation Result</h1>
    <p> <%= request.getAttribute("answer") %> </p>

</body>
</html>

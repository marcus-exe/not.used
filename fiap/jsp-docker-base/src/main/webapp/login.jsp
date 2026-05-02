<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
    <form action="login" method="post">
        <label for="username">Username v1:</label>
        <input type="text" id="username" name="username"><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"><br>
        <input type="submit" value="Login">
    </form>
    <c:if test="${param.error != null}">
        <p style="color:red;">Invalid username or password</p>
    </c:if>
</body>
</html>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image" href="./assets/images/logo.png" />
    <link href="./resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="index.css">
    <title>Agnello</title>
  </head>

  <body>
    <jsp:include page="components/header/header.jsp"></jsp:include>
    <main>
      <jsp:include page="components/banner/banner.jsp"></jsp:include>
      <jsp:include page="components/categories/categories.jsp"></jsp:include>
    </main>
    <footer>
   	  <jsp:include page="components/footer/footer.jsp"></jsp:include>
    </footer>





    <script src="./resources/js/bootstrap.bundle.min.js"></script>
  </body>

  </html>
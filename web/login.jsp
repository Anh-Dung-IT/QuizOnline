
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>        
        <form action="Login" method="POST" class="form-row mx-auto" style="width: 350px;">
            <p class="h3 text-center col-12 mb-4 mt-lg-5">Login</p>

            <input type="text" name="email" placeholder="Email" class="form-control col-12 mb-3">
            <input type="password" name="password" placeholder="Password" class="form-control col-12 mb-3">
            <div class="col-12 text-danger text-center mb-4">${requestScope.LOGIN_FAIL}</div>

            <input type="submit" value="Login" class="btn btn-primary col-12 mb-2">
            <input type="reset" value="Reset" class="btn btn-secondary col-12 mb-2">
            
            <c:url var="register" value="register-page" />
            <a href="${register}" class="btn btn-outline-info col-12">Register Now</a>
        </form>
    </body>
</html>

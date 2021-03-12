
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>        
        <form action="Register" method="POST" class="form-row mx-auto mt-lg-5" style="width: 400px;">
            <p class="h3 text-center col-12">Register</p>

            <div class="form-group col-12">
                <label for="">Email</label>
                <input type="text" name="email" value="${requestScope.ACCOUNT.email}" placeholder="Email" required maxlength="100" class="form-control">
                <div class="text-danger">${requestScope.ERROR.emailNotMatch}</div>
                <div class="text-danger">${requestScope.ERROR.emailExist}</div>
            </div>

            <div class="form-group col-12">
                <label for="">Name</label>
                <input type="text" name="name" value="${requestScope.ACCOUNT.name}" placeholder="Name" required maxlength="100" class="form-control">
            </div>

            <div class="form-group col-12">
                <label for="">Password</label>
                <input type="password" name="password" value="${requestScope.ACCOUNT.password}" placeholder="password" required maxlength="30" class="form-control col-12">
                <div class="text-danger">${requestScope.ERROR.passwordConfirmError}</div>
            </div>

            <div class="form-group col-12">
                <label for="">Confirm Password</label>
                <input type="password" name="confirm" placeholder="Confirm password" required maxlength="30" class="form-control col-12">
            </div>

            <div class="form-group col-12">
                <input type="submit" value="Register" class="btn btn-success col-12">
            </div>

            <div class="form-group col-12">
                <input type="reset" value="Reset" class="btn btn-secondary col-12">
            </div>            

            <div class="form-group col-12">
                <c:url var="login" value="login-page" />
                <a href="${login}" class="text-center col-12 nav-link">I already have an account, login now</a>
            </div>            
        </form>
    </body>
</html>

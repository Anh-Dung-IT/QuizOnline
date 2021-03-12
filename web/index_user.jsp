
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Online</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>

    <body>
        <div class="row justify-content-between mx-5">
            <!-- ============= side-nav =============== -->
            <nav class="navbar navbar-dark bg-secondary flex-column justify-content-start col-3" style="height: 100vh;">
                <h1 class="navbar-brand h1" style="font-size: 35px;">Quiz Online</h1>

                <div class="navbar-text" style="font-size: 25px;">Welcome: ${sessionScope.ACCOUNT.name}</div>
    
                <c:url var="home" value="User-Page" />
                <a href="${home}" class="nav-link" style="font-size: 20px; color: #fff;">Home</a>

                <c:url var="logout" value="Logout"/>
                <a href="${logout}" class="nav-link" style="font-size: 20px; color: #fff;">Log out</a>
            </nav>
    
            <div class="col-9 mt-4">
                <!-- ============ history button =============== -->
                <c:url var="history" value="History-Quiz" />
                <a href="${history}" class="btn btn-primary ml-3">History Quiz</a>
        
                <!-- ================ list quiz ================= -->
                <div class="row justify-content-center">
                    <c:if test="${not empty requestScope.LIST_QUIZ}">
                        <c:set var="sub" value="${requestScope.LIST_SUBJECT}"/>
            
                        <c:forEach var="quiz" items="${requestScope.LIST_QUIZ}">
                            <form action="Prepare-Quiz" method="POST" class="card col-5 m-3">
                                <input type="hidden" name="subjectID" value="${sub.get(quiz.quizID).subjectID}">
                                <input type="hidden" name="totalQuestion" value="${quiz.totalQuestion}">
                                <input type="hidden" name="limitedMin" value="${quiz.limited_Min}" />
                                <input type="hidden" name="quizID" value="${quiz.quizID}" />

                                <div class="card-header">${sub.get(quiz.quizID).subjectCode} - ${sub.get(quiz.quizID).name}</div>
                                <div class="card-body">
                                    <div>Duration: ${quiz.limited_Min} minutes</div>                    
                                    <div>Number of questions: ${quiz.totalQuestion}</div>
                                </div>
                                
                                <input type="submit" value="Take Quiz" class="btn btn-outline-primary card-footer" onclick="return confirm('Do you want take this quiz?')">
                            </form>
                        </c:forEach>
                    </c:if>
            
                    <c:if test="${empty requestScope.LIST_QUIZ}">
                        <div>No quiz available</div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>

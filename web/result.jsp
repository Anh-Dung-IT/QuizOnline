
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <p class="h3 text-center my-5">Result</p>

            <c:url var="home" value="User-Page" />
            <a href="${home}" class="btn btn-primary mb-5" style="font-size: 20px;">Home</a>

            <c:set var="history_quiz" value="${requestScope.HISTORY_QUIZ}"/>

            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">Correct</th>
                        <th scope="col">Total Question</th>
                        <th scope="col">Point</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${history_quiz.numCorrect}</td>
                        <td>${history_quiz.totalQuestion}</td>
                        <td>${(10 / (history_quiz.totalQuestion)) * history_quiz.numCorrect}</td>
                    </tr>
                </tbody>
            </table>
            <!--     
                        <div>Correct: ${history_quiz.numCorrect}</div>
                        <div>Total Question: ${history_quiz.totalQuestion}</div>
                        <div>Point: ${(10 / (history_quiz.totalQuestion)) * history_quiz.numCorrect}</div> -->
        </div>
    </body>
</html>

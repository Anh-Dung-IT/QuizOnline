
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <p class="h3 text-center">Quiz</p>

            <input type="hidden" value="${requestScope.LIMITED_MIN}" id="duration">
            <p>Total Question: ${sessionScope.TOTAL_QUESTION}</p>
            <p>Duration: ${requestScope.LIMITED_MIN} minutes</p>
            <p id="time"></p>
    
            <form action="Quiz-Submit" method="POST" id="formQuiz" class="card">
                <c:forEach var="question" items="${sessionScope.LIST_QUESTION}" varStatus="counter">
                    <div class="card-body">
                        <div class="card-title">Question ${counter.count}: ${question.questionContent}</div>
                        <c:forEach var="answer" items="${requestScope.LIST_ANSWER.get(question.questionID)}">
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                        <input type="radio" name="${question.questionID}" id="${answer.answerID}" value="${answer.answerID}">
                                    </div>
                                </div>
                                <textarea cols="100" rows="2" class="form-control" readonly>${answer.answerContent}</textarea>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
    
                <div class="form-group">
                    <input type="submit" value="Submit" class="btn btn-primary mb-5">
                </div>
            </form>
        </div>
    </body>

    <script>
        function countDown(time, element) {
            let timer = time;
            let minutes, seconds;

            let x = setInterval(() => {
                minutes = parseInt(timer / 60, 10);
                seconds = parseInt(timer % 60, 10);

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                element.textContent = "Time remaining: " + minutes + ":" + seconds;

                if (--timer < 0) {
                    clearInterval(x);

                    document.getElementById('formQuiz').submit();
                }
            }, 1000);
        }

        window.onload = function () {
            let duration = document.getElementById('duration').value;
            let minutes = parseInt(duration * 60, 10);
            let element = document.getElementById('time');

            countDown(minutes, element);
        }
    </script>
</html>

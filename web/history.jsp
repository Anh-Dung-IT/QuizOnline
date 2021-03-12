
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>

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

            <!-- =========== table + paging =============== -->
            <div class="col-9 mt-4">
                <form action="History-Quiz" method="POST" class="input-group">
                    <select name="subjectID" class="custom-select">
                        <option value="All">All</option>

                        <c:forEach var="subject" items="${requestScope.LIST_SUBJECT}">
                            <c:if test="${requestScope.SUBJECT_ID == subject.key}">
                                <option value="${subject.key}" selected>${subject.value.subjectCode} - ${subject.value.name}</option>
                            </c:if>

                            <c:if test="${requestScope.SUBJECT_ID != subject.key}">
                                <option value="${subject.key}">${subject.value.subjectCode} - ${subject.value.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>

                    <input type="submit" value="Search" class="btn btn-primary input-group-append">
                </form>

                <c:set var="SID" value="${requestScope.LIST_QUIZ_SUBJECTID}"/>
                <c:set var="LS" value="${requestScope.LIST_SUBJECT}"/>

                <!-- ========== table history ================= -->
                <table class="table table-hover">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Subject</th>
                            <th scope="col">Correct</th>
                            <th scope="col">Total Question</th>
                            <th scope="col">Point</th>
                            <th scope="col">Date</th>s
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="history_quiz" items="${requestScope.LIST_HISTORY}" varStatus="counter">
                            <tr>
                                <th scope="col">${counter.count}</th>
                                <td>${LS.get(SID.get(history_quiz.quizID)).subjectCode} - ${LS.get(SID.get(history_quiz.quizID)).name}</td>
                                <td>${history_quiz.numCorrect}</td>
                                <td>${history_quiz.totalQuestion}</td>
                                <td>${(10 / (history_quiz.totalQuestion)) * history_quiz.numCorrect}</td>
                                <td>${history_quiz.createDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- ============== paging ================ -->
                <c:if test="${not empty requestScope.LIST_HISTORY}">
                    <div class="pagination justify-content-center">
                        <c:url var="previous" value="History-Quiz">
                            <c:param name="currentPage" value="${requestScope.CURRENT_PAGE - 1}" />
                            <c:param name="subjectID" value="${requestScope.SUBJECT_ID}"/>
                        </c:url>
                        <a href="${previous}" class="page-item page-link">Previous</a>
    
                        <div class="page-item page-link">${requestScope.CURRENT_PAGE} / ${requestScope.MAX_PAGE}</div>
    
                        <c:url var="next" value="History-Quiz">
                            <c:param name="currentPage" value="${requestScope.CURRENT_PAGE + 1}" />
                            <c:param name="subjectID" value="${requestScope.SUBJECT_ID}"/>
                        </c:url>
                        <a href="${next}" class="page-item page-link">Next</a>
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>

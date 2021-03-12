
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <div class="row justify-content-between mx-5">
            <nav class="navbar navbar-dark bg-secondary flex-column justify-content-start col-3" style="height: 100vh;">
                <h1 class="navbar-brand h1" style="font-size: 35px;">Quiz Online</h1>

                <div class="navbar-text" style="font-size: 25px;">Welcome: ${sessionScope.ACCOUNT.name}</div>

                <c:url var="home" value="Search" />
                <a href="${home}" class="nav-link" style="font-size: 20px; color: #fff;">Home</a>

                <c:url var="logout" value="Logout"/>
                <a href="${logout}" class="nav-link" style="font-size: 20px; color: #fff;">Log out</a>
            </nav>

            <div class="col-9">
                <!-- ============= form search ================= -->
                <form action="Search" method="POST" class="form-row align-items-end mt-3">
                    <!-- ============ question content -->
                    <div class="form-group col-4">
                        <label for="">Question Content</label>
                        <input type="text" name="questionContent" value="${requestScope.SEARCH_VALUE}" placeholder="Question Content..." class="form-control">
                    </div>

                    <!-- ================ status ================= -->
                    <div class="form-group col-2">
                        <label for="">Status</label>
                        <select name="status" class="form-control custom-select">
                            <option value="All">All</option>

                            <c:if test="${requestScope.STATUS == true}">
                                <option value="true" selected>Active</option>
                                <option value="false">In-Active</option>
                            </c:if>

                            <c:if test="${requestScope.STATUS == false}">
                                <option value="true">Active</option>
                                <option value="false" selected>In-Active</option>
                            </c:if>

                            <c:if test="${requestScope.STATUS == null}">
                                <option value="true">Active</option>
                                <option value="false">In-Active</option>
                            </c:if>
                        </select>
                    </div>

                    <!-- ============== subject ================ -->
                    <div class="form-group col-4">
                        <label for="">Subject</label>
                        <select name="subjectID" class="form-control custom-select">
                            <option value="All">All</option>

                            <c:forEach var="subject" items="${requestScope.LIST_SUBJECT}">
                                <c:if test="${requestScope.SUBJECTID == subject.key}">
                                    <option value="${subject.key}" selected>${subject.value.subjectCode} - ${subject.value.name}</option>
                                </c:if>

                                <c:if test="${requestScope.SUBJECTID != subject.key}">
                                    <option value="${subject.key}">${subject.value.subjectCode} - ${subject.value.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- =========== button ============== -->
                    <div class="form-group col-2">
                        <input type="submit" value="Search" class="btn btn-primary">
                    </div>
                </form>

                <!-- =================== add new button ================== -->
                <c:url var="create" value="Form-Question" >
                    <c:param name="btAction" value="Create-Question" />
                </c:url>
                <a href="${create}" class="btn btn-outline-danger mt-3">Add New Question</a>

                <c:if test="${not empty requestScope.LIST_QUESTION}">
                    <c:forEach var="question" items="${requestScope.LIST_QUESTION}">
                        <!-- ================== Question =============== -->
                        <form action="Status-Question" method="POST" class="card my-4">
                            <div><input type="hidden" name="questionID" value="${question.questionID}"></div>

                            <c:set var="subject" value="${requestScope.LIST_SUBJECT.get(question.subjectID)}"/>
                            <div class="card-header">${subject.subjectCode} - ${subject.name}</div>

                            <div class="card-body">
                                <div class="card-title">${question.questionContent}</div>

                                <c:set var="answers" value="${requestScope.LIST_ANSWER.get(question.questionID)}"/>

                                <c:forEach var="answer" items="${answers}">
                                    <c:if test="${answer.isCorrect == true}">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <div class="input-group-text">
                                                    <input type="radio" name="answer" id="${answer.answerID}" disabled checked>
                                                </div>
                                            </div>
                                            <textarea cols="100" rows="2" class="form-control" readonly>${answer.answerContent}</textarea>
                                        </div>
                                    </c:if>

                                    <c:if test="${answer.isCorrect == false}">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <div class="input-group-text">
                                                    <input type="radio" name="answer" id="${answer.answerID}" disabled>
                                                </div>
                                            </div>
                                            <textarea cols="100" rows="2" class="form-control" readonly>${answer.answerContent}</textarea>
                                        </div>
                                    </c:if>
                                </c:forEach>

                                <div class="card-text">Create Date: ${question.createDate}</div>

                                <c:if test="${question.status == true}">
                                    <div class="card-text">Status: Active</div>
                                </c:if>
                                <c:if test="${question.status == false}">
                                    <div class="card-text">Status: In-Active</div>
                                </c:if>

                            </div>

                            <div class="form-row card-footer">
                                <div class="form-group col-1">
                                    <c:url var="edit" value="Form-Question">
                                        <c:param name="btAction" value="Edit-Question" />
                                        <c:param name="questionID" value="${question.questionID}"/>
                                    </c:url>
                                    <a href="${edit}" class="btn btn-primary col-12">Edit</a>
                                </div>

                                <div class="form-group col-2">
                                    <c:if test="${question.status == true}">
                                        <input type="submit" name="btAction" value="In-Active" class="btn btn-secondary col-12" onclick="return confirm('Do you want in-active this question?')">
                                    </c:if>
                                    <c:if test="${question.status == false}">
                                        <input type="submit" name="btAction" value="Active" class="btn btn-success col-12" onclick="return confirm('Do you want active this question?')">
                                    </c:if>
                                </div>
                            </div>
                        </form>
                    </c:forEach>

                    <!-- ================= paging ================== -->
                    <div class="pagination justify-content-center mb-4">
                        <c:url var="previous" value="Search">
                            <c:param name="currentPage" value="${requestScope.CURRENT_PAGE - 1}" />
                            <c:param name="questionContent" value="${requestScope.SEARCH_VALUE}"/>
                            <c:param name="status" value="${requestScope.STATUS}"/>
                            <c:param name="subjectID" value="${requestScope.SUBJECTID}"/>
                        </c:url>
                        <a href="${previous}" class="page-item page-link">Previous</a>

                        <div class="page-item page-link">${requestScope.CURRENT_PAGE} / ${requestScope.MAX_PAGE}</div>

                        <c:url var="next" value="Search">
                            <c:param name="currentPage" value="${requestScope.CURRENT_PAGE + 1}" />
                            <c:param name="questionContent" value="${requestScope.SEARCH_VALUE}"/>
                            <c:param name="status" value="${requestScope.STATUS}"/>
                            <c:param name="subjectID" value="${requestScope.SUBJECTID}"/>
                        </c:url>
                        <a href="${next}" class="page-item page-link">Next</a>
                    </div>
                </c:if>

                <c:if test="${empty requestScope.LIST_QUESTION}">
                    <div class="text-center mt-3">No search results</div>
                </c:if>
            </div>
        </div>
    </body>
</html>

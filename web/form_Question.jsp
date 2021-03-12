
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Question Form</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <p class="h3 text-center my-3">Question form</p>

            <c:url var="home" value="Search" />
            <a href="${home}" class="btn btn-primary mb-4" style="font-size: 20px;">Home</a>
    
            <form action="${requestScope.ACTION}" method="POST">
                <!-- ==== question content ==== -->
                <div class="form-group">
                    <label for="">Question Content</label>            
                    <textarea cols="100" rows="5" name="txtQuestionContent" placeholder="Question content..." maxlength="5000" required class="form-control">${requestScope.QUESTION.questionContent}</textarea>
                </div>
    
                <!-- ========== edit ================= -->
                <c:if test="${requestScope.QUESTION != null}">
                    <input type="hidden" name="questionID" value="${requestScope.QUESTION.questionID}" />
    
                    <c:forEach var="ans" items="${requestScope.LIST_ANSWER}" varStatus="counter">
    
                        <input type="hidden" name="answerID" value="${ans.answerID}" />
    
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <c:if test="${ans.isCorrect == true}">
                                        <input type="radio" name="answer" value="${counter.count}" checked>
                                    </c:if>
    
                                    <c:if test="${ans.isCorrect == false}">
                                        <input type="radio" name="answer" value="${counter.count}">
                                    </c:if>
                                </div>
                            </div>
                            <textarea cols="100" rows="2" name="txtAnswerContent" maxlength="5000" placeholder="Answer" required class="form-control">${ans.answerContent}</textarea>
                        </div>
                    </c:forEach>
    
                    <!-- ====== subject ====== -->
                    <div class="form-group">
                        <select name="subjectIDPara" class="form-control custom-select">
                            <c:forEach var="subject" items="${requestScope.LIST_SUBJECT}">
                                <c:if test="${requestScope.QUESTION.subjectID == subject.key}">
                                    <option value="${subject.key}" selected>${subject.value.subjectCode} - ${subject.value.name}</option>
                                </c:if>
                                <c:if test="${requestScope.QUESTION.subjectID != subject.key}">
                                    <option value="${subject.key}">${subject.value.subjectCode} - ${subject.value.name}</option>
                                </c:if>                        
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
    
                <!-- ============== create new =============== -->
                <c:if test="${requestScope.QUESTION == null}">
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="answer" value="1" checked>
                            </div>
                        </div>
                        <textarea cols="100" rows="2" name="txtAnswerContent" maxlength="5000" placeholder="Answer 1" required class="form-control"></textarea>
                    </div>
    
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="answer" value="2">
                            </div>
                        </div>
                        <textarea cols="100" rows="2" name="txtAnswerContent" maxlength="5000" placeholder="Answer 2" required class="form-control"></textarea>
                    </div>
    
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="answer" value="3">
                            </div>
                        </div>
                        <textarea cols="100" rows="2" name="txtAnswerContent" maxlength="5000" placeholder="Answer 3" required class="form-control"></textarea>
                    </div>
    
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <div class="input-group-text">
                                <input type="radio" name="answer" value="4">
                            </div>
                        </div>
                        <textarea cols="100" rows="2" name="txtAnswerContent" maxlength="5000" placeholder="Answer 4" required class="form-control"></textarea>
                    </div>
    
                    <!-- ====== subject ====== -->
                    <div class="form-group">
                        <select name="subjectIDPara" class="form-control custom-select">
                            <c:forEach var="subject" items="${requestScope.LIST_SUBJECT}">
                                <option value="${subject.key}">${subject.value.subjectCode} - ${subject.value.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
    
                <!-- ============ button =================== -->
                <div class="form-group">
                    <input type="submit" value="Save" class="btn btn-primary" onclick="return confirm('Do you want to save ?')">
                    <input type="reset" value="Reset" class="btn btn-secondary">
                </div>            
            </form>
        </div>
    </body>
</html>

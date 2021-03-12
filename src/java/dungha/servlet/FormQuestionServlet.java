package dungha.servlet;

import dungha.account.Account;
import dungha.answer.Answer;
import dungha.answer.AnswerDAO;
import dungha.question.Question;
import dungha.question.QuestionDAO;
import dungha.subject.Subject;
import dungha.subject.SubjectDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebServlet(name = "FormQuestionServlet", urlPatterns = {"/FormQuestionServlet"})
public class FormQuestionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(FormQuestionServlet.class);

    private static final String FORM_QUESTION = "form_Question.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        List<Answer> listAnswers = null;
        Question question = null;

        SubjectDAO subjectDAO = new SubjectDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        String btAction = request.getParameter("btAction");

        HttpSession session = request.getSession(false);
        if (session != null) {
            account = (Account) session.getAttribute("ACCOUNT");
        }

        try {
            if (account == null) {
                request.setAttribute("LOGIN_FAIL", "You must log in before continuing to work");

                url = LOGIN_PAGE;
            } else {
                Map<Integer, Subject> listSubject = subjectDAO.getAll();

                if (btAction.equals("Edit-Question")) {
                    String questionIDPara = request.getParameter("questionID");
                    int questionID = Integer.parseInt(questionIDPara);

                    question = questionDAO.getByID(questionID);

                    if (question != null) {
                        listAnswers = answerDAO.getByQuestionID(questionID);

                        for (Answer ans : listAnswers) {
                            if (ans.getAnswerContent().contains("<")) {
                                String buff = ans.getAnswerContent();
                                buff = buff.replace("<", "&lsaquo;");
                                ans.setAnswerContent(buff);
                            }
                        }
                    }
                }

                request.setAttribute("LIST_SUBJECT", listSubject);
                request.setAttribute("LIST_ANSWER", listAnswers);
                request.setAttribute("QUESTION", question);
                request.setAttribute("ACTION", btAction);

                if (listSubject.isEmpty()) {
                    request.setAttribute("ERROR", "No subject, please try again");

                    url = ERROR;
                } else {
                    url = FORM_QUESTION;
                }
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        } catch (NamingException e) {
            LOGGER.info(e.getMessage());
        } catch (Exception e) {
            request.setAttribute("ERROR", "Something wrong happened, please try again");
            url = ERROR;
            LOGGER.info(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

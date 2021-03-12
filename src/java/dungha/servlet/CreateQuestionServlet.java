package dungha.servlet;

import dungha.account.Account;
import dungha.answer.Answer;
import dungha.answer.AnswerDAO;
import dungha.question.Question;
import dungha.question.QuestionDAO;
import dungha.utils.Assets;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebServlet(name = "CreateQuestionServlet", urlPatterns = {"/CreateQuestionServlet"})
public class CreateQuestionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateQuestionServlet.class);

    private static final String SEARCH = "SearchServlet";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        QuestionDAO questionDAO = new QuestionDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        String questionContent = request.getParameter("txtQuestionContent").trim();
        String answerPara = request.getParameter("answer").trim();
        String[] answerContent = request.getParameterValues("txtAnswerContent");
        String subjectIDPara = request.getParameter("subjectIDPara").trim();

        HttpSession session = request.getSession(false);
        if (session != null) {
            account = (Account) session.getAttribute("ACCOUNT");
        }

        try {
            if (account == null) {
                request.setAttribute("LOGIN_FAIL", "You must log in before continuing to work");

                url = LOGIN_PAGE;
            } else {
                int subjectID = Integer.parseInt(subjectIDPara);

                Question question = new Question();

                question.setSubjectID(subjectID);
                question.setQuestionContent(questionContent);
                question.setCreateDate(new Date(new java.util.Date().getTime()));
                question.setStatus(Assets.VALID);

                if (questionDAO.insert(question)) {
                    int questionID = questionDAO.getLastID();
                    int numAnswerCorrect = Integer.parseInt(answerPara);

                    for (int i = 0; i < answerContent.length; i++) {
                        Answer answer = new Answer();

                        answer.setQuestionID(questionID);
                        answer.setAnswerContent(answerContent[i].trim());
                        answer.setIsCorrect((i + 1) == numAnswerCorrect);
                        answer.setStatus(Assets.VALID);

                        answerDAO.insert(answer);
                    }
                }

                url = SEARCH;
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

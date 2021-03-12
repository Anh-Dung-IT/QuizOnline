package dungha.servlet;

import dungha.account.Account;
import dungha.answer.Answer;
import dungha.answer.AnswerDAO;
import dungha.question.Question;
import dungha.question.QuestionDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebServlet(name = "EditQuestionServlet", urlPatterns = {"/EditQuestionServlet"})
public class EditQuestionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditQuestionServlet.class);

    private static final String SEARCH = "SearchServlet";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;

        QuestionDAO questionDAO = new QuestionDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        String questionIDPara = request.getParameter("questionID");
        String questionContent = request.getParameter("txtQuestionContent").trim();
        String answerPara = request.getParameter("answer").trim();
        String[] answerIDPara = request.getParameterValues("answerID");
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
                int questionID = Integer.parseInt(questionIDPara);

                Question question = new Question();

                question.setQuestionID(questionID);
                question.setSubjectID(subjectID);
                question.setQuestionContent(questionContent);

                if (questionDAO.update(question)) {
                    int numAnswerCorrect = Integer.parseInt(answerPara);

                    for (int i = 0; i < answerContent.length; i++) {
                        Answer answer = new Answer();

                        answer.setAnswerID(Integer.parseInt(answerIDPara[i]));
                        answer.setAnswerContent(answerContent[i].trim());
                        answer.setIsCorrect((i + 1) == numAnswerCorrect);

                        answerDAO.update(answer);
                    }

                    url = SEARCH;
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

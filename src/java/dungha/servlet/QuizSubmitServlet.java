package dungha.servlet;

import dungha.account.Account;
import dungha.answer.AnswerDAO;
import dungha.historyquiz.HistoryQuiz;
import dungha.historyquiz.HistoryQuizDAO;
import dungha.question.Question;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebServlet(name = "QuizSubmitServlet", urlPatterns = {"/QuizSubmitServlet"})
public class QuizSubmitServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(QuizSubmitServlet.class);

    private static final String RESULT = "result.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        List<Question> listQuestion = null;
        int numAnswerCorrect = 0;

        AnswerDAO answerDAO = new AnswerDAO();
        HistoryQuizDAO historyQuizDAO = new HistoryQuizDAO();

        HttpSession session = request.getSession(false);

        if (session != null) {
            account = (Account) session.getAttribute("ACCOUNT");
        }

        try {
            if (account == null) {
                request.setAttribute("LOGIN_FAIL", "You must log in before continuing to work");

                url = LOGIN_PAGE;
            } else {
                listQuestion = (List<Question>) session.getAttribute("LIST_QUESTION");
                int totalQuestion = (int) session.getAttribute("TOTAL_QUESTION");
                int quizID = (int) session.getAttribute("QUIZ_ID");

                for (Question question : listQuestion) {
                    String answerIDPara = request.getParameter(question.getQuestionID() + "");
                    if (answerIDPara != null) {
                        int answerID = Integer.parseInt(answerIDPara);
                        numAnswerCorrect = answerDAO.checkAnswerCorrect(answerID) ? ++numAnswerCorrect : numAnswerCorrect;
                    }
                }

                HistoryQuiz historyQuiz = new HistoryQuiz();

                historyQuiz.setEmail(account.getEmail());
                historyQuiz.setQuizID(quizID);
                historyQuiz.setNumCorrect(numAnswerCorrect);
                historyQuiz.setTotalQuestion(totalQuestion);
                historyQuiz.setCreateDate(new Date(new java.util.Date().getTime()));

                historyQuizDAO.insert(historyQuiz);

                url = RESULT;

                request.setAttribute("HISTORY_QUIZ", historyQuiz);
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

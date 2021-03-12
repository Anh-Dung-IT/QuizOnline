package dungha.servlet;

import dungha.account.Account;
import dungha.answer.Answer;
import dungha.question.Question;
import dungha.question.QuestionDAO;
import dungha.utils.MyTools;
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

@WebServlet(name = "PrepareQuizServlet", urlPatterns = {"/PrepareQuizServlet"})
public class PrepareQuizServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PrepareQuizServlet.class);

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";
    private static final String QUIZ = "quiz.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        Map<Integer, List<Answer>> listAnswer = null;

        QuestionDAO questionDAO = new QuestionDAO();

        String subjectIDPara = request.getParameter("subjectID");
        String totalQuestionPara = request.getParameter("totalQuestion");
        String limitedMinPara = request.getParameter("limitedMin");
        String quizIDPara = request.getParameter("quizID");

        HttpSession session = request.getSession(false);
        if (session != null) {
            account = (Account) session.getAttribute("ACCOUNT");
        }

        try {
            int subjectID = Integer.parseInt(subjectIDPara);
            int totalQuestion = Integer.parseInt(totalQuestionPara);
            int limitedMin = Integer.parseInt(limitedMinPara);
            int quizID = Integer.parseInt(quizIDPara);

            if (account == null) {
                request.setAttribute("LOGIN_FAIL", "You must log in before continuing to work");

                url = LOGIN_PAGE;
            } else {
                List<Question> listQuestion = questionDAO.getRandom(totalQuestionPara, subjectID);

                if (listQuestion.size() != totalQuestion) {
                    request.setAttribute("ERROR", "Something wrong happened, please try again");

                    url = ERROR;
                } else {
                    listAnswer = MyTools.getListAnswer(listQuestion);

                    MyTools.replaceString(listQuestion);

                    session.setAttribute("LIST_QUESTION", listQuestion);
                    session.setAttribute("TOTAL_QUESTION", totalQuestion);
                    session.setAttribute("QUIZ_ID", quizID);

                    request.setAttribute("LIST_ANSWER", listAnswer);
                    request.setAttribute("LIMITED_MIN", limitedMin);

                    url = QUIZ;
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

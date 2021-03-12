package dungha.servlet;

import dungha.account.Account;
import dungha.quiz.Quiz;
import dungha.quiz.QuizDAO;
import dungha.subject.Subject;
import dungha.subject.SubjectDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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

@WebServlet(name = "UserPageServlet", urlPatterns = {"/UserPageServlet"})
public class UserPageServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserPageServlet.class);

    private static final String USER_PAGE_VIEW = "index_user.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        List<Quiz> listQuiz;
        Map<Integer, Subject> listSubject;

        QuizDAO quizDAO = new QuizDAO();

        HttpSession session = request.getSession(false);
        if (session != null) {
            account = (Account) session.getAttribute("ACCOUNT");
        }

        try {
            if (account == null) {
                request.setAttribute("LOGIN_FAIL", "You must log in before continuing to work");

                url = LOGIN_PAGE;
            } else {
                listQuiz = quizDAO.getQuizAvailable();
                listSubject = getListSubject(listQuiz);

                request.setAttribute("LIST_QUIZ", listQuiz);
                request.setAttribute("LIST_SUBJECT", listSubject);

                url = USER_PAGE_VIEW;
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

    private Map<Integer, Subject> getListSubject(List<Quiz> listQuiz) throws SQLException, NamingException {

        Map<Integer, Subject> listSubject = new HashMap<>();

        SubjectDAO subjectDAO = new SubjectDAO();

        for (Quiz quiz : listQuiz) {
            Subject subject = subjectDAO.getByID(quiz.getSubjectID());

            listSubject.put(quiz.getQuizID(), subject);
        }

        return listSubject;
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

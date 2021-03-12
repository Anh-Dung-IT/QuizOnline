package dungha.servlet;

import dungha.account.Account;
import dungha.historyquiz.HistoryQuiz;
import dungha.historyquiz.HistoryQuizDAO;
import dungha.quiz.Quiz;
import dungha.quiz.QuizDAO;
import dungha.subject.Subject;
import dungha.subject.SubjectDAO;
import dungha.utils.Assets;
import dungha.utils.MyTools;
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

@WebServlet(name = "HistoryQuizServlet", urlPatterns = {"/HistoryQuizServlet"})
public class HistoryQuizServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(HistoryQuizServlet.class);

    private static final String HISTORY = "history.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";

    private static final String ALL = "All";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        Map<Integer, Subject> listSubject = null;
        Map<Integer, Integer> listQuizSubjectID = new HashMap<>();;
        int maxPage;
        int currentPage;
        List<HistoryQuiz> listHistory;

        HistoryQuizDAO historyQuizDAO = new HistoryQuizDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        QuizDAO quizDAO = new QuizDAO();

        String subjectIDPara = request.getParameter("subjectID");
        String currentPagePara = request.getParameter("currentPage");

        HttpSession session = request.getSession(false);
        if (session != null) {
            account = (Account) session.getAttribute("ACCOUNT");
        }

        try {
            if (account == null) {
                request.setAttribute("LOGIN_FAIL", "You must log in before continuing to work");

                url = LOGIN_PAGE;
            } else {
                listSubject = subjectDAO.getAll();

                if (subjectIDPara == null || subjectIDPara.isEmpty()) {
                    subjectIDPara = ALL;
                }

                if (subjectIDPara.equals(ALL)) {
                    int totalRecord = historyQuizDAO.getTotal(account.getEmail());
                    maxPage = MyTools.getMaxPage(totalRecord, Assets.NUMBER_RECORD_IN_PAGE);
                    currentPage = MyTools.getCurrentPage(currentPagePara, maxPage);
                    int offSet = MyTools.getOffSet(currentPage, Assets.NUMBER_RECORD_IN_PAGE);

                    listHistory = historyQuizDAO.search(account.getEmail(), offSet, Assets.NUMBER_RECORD_IN_PAGE);
                } else {
                    int subjectID = Integer.parseInt(subjectIDPara);

                    int totalRecord = historyQuizDAO.getTotal(account.getEmail(), subjectID);
                    maxPage = MyTools.getMaxPage(totalRecord, Assets.NUMBER_RECORD_IN_PAGE);
                    currentPage = MyTools.getCurrentPage(currentPagePara, maxPage);
                    int offSet = MyTools.getOffSet(currentPage, Assets.NUMBER_RECORD_IN_PAGE);

                    listHistory = historyQuizDAO.search(account.getEmail(), subjectID, offSet, Assets.NUMBER_RECORD_IN_PAGE);

                    request.setAttribute("SUBJECT_ID", subjectID);
                }

                for (HistoryQuiz historyQuiz : listHistory) {
                    Quiz quiz = quizDAO.getByID(historyQuiz.getQuizID());

                    if (!listQuizSubjectID.containsKey(quiz.getQuizID())) {
                        listQuizSubjectID.put(quiz.getQuizID(), quiz.getSubjectID());
                    }
                }

                request.setAttribute("LIST_SUBJECT", listSubject);
                request.setAttribute("LIST_HISTORY", listHistory);
                request.setAttribute("LIST_QUIZ_SUBJECTID", listQuizSubjectID);
                request.setAttribute("MAX_PAGE", maxPage);
                request.setAttribute("CURRENT_PAGE", currentPage);

                if (listSubject.isEmpty()) {
                    request.setAttribute("ERROR", "Something wrong happened, please try again");

                    url = ERROR;
                } else {
                    url = HISTORY;
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

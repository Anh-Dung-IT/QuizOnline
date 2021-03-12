package dungha.servlet;

import dungha.account.Account;
import dungha.answer.Answer;
import dungha.question.Question;
import dungha.question.QuestionDAO;
import dungha.subject.Subject;
import dungha.subject.SubjectDAO;
import dungha.utils.Assets;
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

@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class);

    private static final String ADMIN_PAGE_VIEW = "index_admin.jsp";
    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    private static final String ALL = "All";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        Map<Integer, List<Answer>> listAnswer;
        List<Question> listQuestion;
        Map<Integer, Subject> listSubject;
        int maxPage = 0;
        int currentPage = 0;

        SubjectDAO subjectDAO = new SubjectDAO();
        QuestionDAO questionDAO = new QuestionDAO();

        String questionContent = request.getParameter("questionContent");
        String statusPara = request.getParameter("status");
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

                if (!listSubject.isEmpty()) {
                    url = ADMIN_PAGE_VIEW;

                    if (questionContent == null) {
                        questionContent = "";
                    }
                    if (statusPara == null || statusPara.isEmpty()) {
                        statusPara = ALL;
                    }
                    if (subjectIDPara == null || subjectIDPara.isEmpty()) {
                        subjectIDPara = ALL;
                    }

                    if (!statusPara.equals(ALL) && !subjectIDPara.equals(ALL)) {
                        boolean status = MyTools.parseStatus(statusPara);
                        int subjectID = Integer.parseInt(subjectIDPara);

                        int totalRecord = questionDAO.getTotalRecord(questionContent, status, subjectID);
                        maxPage = MyTools.getMaxPage(totalRecord, Assets.NUMBER_RECORD_IN_PAGE);
                        currentPage = MyTools.getCurrentPage(currentPagePara, maxPage);
                        int offSet = MyTools.getOffSet(currentPage, Assets.NUMBER_RECORD_IN_PAGE);

                        listQuestion = questionDAO.search(questionContent, status, subjectID, offSet, Assets.NUMBER_RECORD_IN_PAGE);

                        request.setAttribute("STATUS", status);
                        request.setAttribute("SUBJECTID", subjectID);
                    } else if (!statusPara.equals(ALL)) {
                        boolean status = MyTools.parseStatus(statusPara);

                        int totalRecord = questionDAO.getTotalRecordHasStatus(questionContent, status);
                        maxPage = MyTools.getMaxPage(totalRecord, Assets.NUMBER_RECORD_IN_PAGE);
                        currentPage = MyTools.getCurrentPage(currentPagePara, maxPage);
                        int offSet = MyTools.getOffSet(currentPage, Assets.NUMBER_RECORD_IN_PAGE);

                        listQuestion = questionDAO.searchByStatus(questionContent, status, offSet, Assets.NUMBER_RECORD_IN_PAGE);

                        request.setAttribute("STATUS", status);
                    } else if (!subjectIDPara.equals(ALL)) {
                        int subjectID = Integer.parseInt(subjectIDPara);

                        int totalRecord = questionDAO.getTotalRecordHasSubjectID(questionContent, subjectID);
                        maxPage = MyTools.getMaxPage(totalRecord, Assets.NUMBER_RECORD_IN_PAGE);
                        currentPage = MyTools.getCurrentPage(currentPagePara, maxPage);
                        int offSet = MyTools.getOffSet(currentPage, Assets.NUMBER_RECORD_IN_PAGE);

                        listQuestion = questionDAO.searchBySubject(questionContent, subjectID, offSet, Assets.NUMBER_RECORD_IN_PAGE);

                        request.setAttribute("SUBJECTID", subjectID);
                    } else {
                        int totalRecord = questionDAO.getTotalRecord(questionContent);
                        maxPage = MyTools.getMaxPage(totalRecord, Assets.NUMBER_RECORD_IN_PAGE);
                        currentPage = MyTools.getCurrentPage(currentPagePara, maxPage);
                        int offSet = MyTools.getOffSet(currentPage, Assets.NUMBER_RECORD_IN_PAGE);

                        listQuestion = questionDAO.search(questionContent, offSet, Assets.NUMBER_RECORD_IN_PAGE);
                    }

                    listAnswer = MyTools.getListAnswer(listQuestion);

                    MyTools.replaceString(listQuestion);

                    request.setAttribute("LIST_SUBJECT", listSubject);
                    request.setAttribute("LIST_QUESTION", listQuestion);
                    request.setAttribute("SEARCH_VALUE", questionContent);
                    request.setAttribute("MAX_PAGE", maxPage);
                    request.setAttribute("CURRENT_PAGE", currentPage);
                    request.setAttribute("LIST_ANSWER", listAnswer);
                }

                if (listSubject.isEmpty()) {
                    request.setAttribute("ERROR", "Something wrong happened, please try again");

                    url = ERROR;
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

package dungha.servlet;

import dungha.account.Account;
import dungha.question.QuestionDAO;
import dungha.utils.Assets;
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

@WebServlet(name = "StatusQuestionServlet", urlPatterns = {"/StatusQuestionServlet"})
public class StatusQuestionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(StatusQuestionServlet.class);

    private static final String SEARCH = "SearchServlet";
    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        Account account = null;
        QuestionDAO questionDAO = new QuestionDAO();

        String btAction = request.getParameter("btAction");
        String questionIDPara = request.getParameter("questionID");

        HttpSession session = request.getSession(false);
        if (session != null) {
            account = (Account) session.getAttribute("ACCOUNT");
        }

        try {
            if (account == null) {
                request.setAttribute("LOGIN_FAIL", "You must log in before continuing to work");

                url = LOGIN_PAGE;
            } else {
                int questionID = Integer.parseInt(questionIDPara);

                if (btAction.equals("Active")) {
                    questionDAO.changeStatus(questionID, Assets.VALID);
                } else if (btAction.equals("In-Active")) {
                    questionDAO.changeStatus(questionID, Assets.IN_VALID);
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

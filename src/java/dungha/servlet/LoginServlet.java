package dungha.servlet;

import dungha.account.Account;
import dungha.account.AccountDAO;
import dungha.utils.MyTools;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    private static final String USER_PAGE = "UserPageServlet";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String SEARCH = "SearchServlet";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        AccountDAO accountDAO = new AccountDAO();
        Account account;

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            if (email == null) {
                email = "";
            }
            if (password == null) {
                password = "";
            }
            String passEncode = MyTools.encodePassword(password);

            account = accountDAO.checkLogin(email, passEncode);

            if (account != null) {
                HttpSession session = request.getSession();
                session.setAttribute("ACCOUNT", account);

                url = account.isIsAdmin() ? SEARCH : USER_PAGE;
            } else {
                request.setAttribute("LOGIN_FAIL", "Email or password invalid");
                url = LOGIN_PAGE;
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info(e.getMessage());
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

package dungha.servlet;

import dungha.account.Account;
import dungha.account.AccountDAO;
import dungha.account.AccountError;
import dungha.utils.Assets;
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
import org.apache.log4j.Logger;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);

    private static final String REGISTER_PAGE = "register.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String url = null;

        Account account = new Account();
        AccountDAO accountDAO = new AccountDAO();

        String email = request.getParameter("email").trim();
        String name = request.getParameter("name").trim();
        String password = request.getParameter("password").trim();

        try {
            AccountError error = checkValid(request);

            if (error != null) {
                url = REGISTER_PAGE;

                account.setEmail(email);
                account.setName(name);
                account.setPassword(password);

                request.setAttribute("ACCOUNT", account);
                request.setAttribute("ERROR", error);
            } else {
                url = LOGIN_PAGE;

                String passEncode = MyTools.encodePassword(password);

                account.setEmail(email);
                account.setPassword(passEncode);
                account.setName(name);
                account.setIsAdmin(Assets.IN_VALID);
                account.setStatus(Assets.VALID);

                accountDAO.insert(account);
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

    private AccountError checkValid(HttpServletRequest request) throws SQLException, NamingException {

        AccountError accountError = new AccountError();
        boolean error = false;

        AccountDAO accountDAO = new AccountDAO();

        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        String confirm = request.getParameter("confirm").trim();

        if (!email.matches(".+@gmail.com$")) {
            accountError.setEmailNotMatch("Email must be in xxx@gmail.com format");
            error = true;
        }

        if (!password.equals(confirm)) {
            accountError.setPasswordConfirmError("Password confirmation is incorrect");
            error = true;
        }

        if (accountDAO.checkEmailExist(email)) {
            accountError.setEmailExist("Email registered in the system");
            error = true;
        }

        if (error) {
            return accountError;
        } else {
            return null;
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

package dungha.filter;

import dungha.account.Account;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "MyFilter", urlPatterns = {"/*"})
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String url = null;
        Account account = null;

        String requestURI = req.getRequestURI();
        String resource = requestURI.substring(requestURI.lastIndexOf('/') + 1);

        try {
            Map<String, String> urlMapping = (Map<String, String>) request.getServletContext().getAttribute("URL_MAPPING");
            Map<String, String> urlAdmin = (Map<String, String>) request.getServletContext().getAttribute("URL_ADMIN");
            Map<String, String> urlUser = (Map<String, String>) request.getServletContext().getAttribute("URL_USER");

            HttpSession session = req.getSession(false);
            if (session != null) {
                account = (Account) session.getAttribute("ACCOUNT");
            }

            if (account == null) {
                url = urlMapping.get(resource);

                url = url == null ? urlMapping.get("login-page") : url;
            } else if (account.isIsAdmin()) {
                url = urlAdmin.get(resource);

                url = url == null ? urlAdmin.get("Search") : url;
            } else {
                url = urlUser.get(resource);

                url = url == null ? urlUser.get("User-Page") : url;
            }
        } finally {
            if (url != null) {
                RequestDispatcher rd = req.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        Map<String, String> urlMapping = new HashMap<>();
        Map<String, String> urlAdmin = new HashMap<>();
        Map<String, String> urlUser = new HashMap<>();

        urlMapping.put("", "login.jsp");
        urlMapping.put("login-page", "login.jsp");
        urlMapping.put("register-page", "register.jsp");
        urlMapping.put("Register", "RegisterServlet");
        urlMapping.put("Login", "LoginServlet");

        urlAdmin.put("Search", "SearchServlet");
        urlAdmin.put("Status-Question", "StatusQuestionServlet");
        urlAdmin.put("Form-Question", "FormQuestionServlet");
        urlAdmin.put("Create-Question", "CreateQuestionServlet");
        urlAdmin.put("Edit-Question", "EditQuestionServlet");
        urlAdmin.put("Logout", "LogoutServlet");

        urlUser.put("User-Page", "UserPageServlet");
        urlUser.put("Prepare-Quiz", "PrepareQuizServlet");
        urlUser.put("Quiz-Submit", "QuizSubmitServlet");
        urlUser.put("History-Quiz", "HistoryQuizServlet");
        urlUser.put("Logout", "LogoutServlet");

        filterConfig.getServletContext().setAttribute("URL_MAPPING", urlMapping);
        filterConfig.getServletContext().setAttribute("URL_ADMIN", urlAdmin);
        filterConfig.getServletContext().setAttribute("URL_USER", urlUser);
    }

    @Override
    public void destroy() {
    }
}

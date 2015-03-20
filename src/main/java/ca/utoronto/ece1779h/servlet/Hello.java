package ca.utoronto.ece1779h.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ca.utoronto.ece1779h.authentication.Authentication;

public class Hello extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = Authentication.authenticate();

        String loginBar = Authentication.getLoginBar();

        resp.setContentType("text/html");
        resp.getWriter().println(loginBar);
        resp.getWriter().println("Hello, this is a testing servlet.");

    }
}

package ca.utoronto.ece1779h.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ca.utoronto.ece1779h.Constants;
import ca.utoronto.ece1779h.calendar.GCalendarService;
import com.google.appengine.api.users.User;
import ca.utoronto.ece1779h.calendar.Utils;

import ca.utoronto.ece1779h.authentication.Authentication;

public class Hello extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = Authentication.authenticate();

        String loginBar = Authentication.getLoginBar();

        List<String> calendarNames = new LinkedList<String>();

        if (user != null) {
            if (Utils.hasCredentials()) {
                calendarNames = GCalendarService.getCalendars();
            } else {
                String urlRedirect = GCalendarService.getURLForAuth("/");
                resp.sendRedirect(urlRedirect);
                return;
            }
        }

        resp.setContentType("text/html");
        resp.getWriter().println(loginBar);
        resp.getWriter().println("Hello, this is a testing servlet.<br>");
        resp.getWriter().println("App name: " + Constants.getAppId() + "<br>");
        for (String item : calendarNames) {
            resp.getWriter().println(item + "<br>");
        }

    }
}

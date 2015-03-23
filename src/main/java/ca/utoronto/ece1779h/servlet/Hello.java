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
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println(loginBar);
        resp.getWriter().println("</head>");
        resp.getWriter().println("<body>");
		resp.getWriter().println("<link rel=\"stylesheet\" href=\"stylesheets/mystyle.css\">");
        resp.getWriter().println(flip_container(
        							"Hello, this is a testing servlet.<br>",
        							"App name: " + Constants.getAppId() + "<br>"));

        for (String item : calendarNames) {
            resp.getWriter().println(item + "<br>");
        }

        resp.getWriter().println("</body>");
        resp.getWriter().println("</html>");
    }

    public String flip_container(String front, String back){
    	String html = "";

    	html += "<div class=\"flip-container\" ontouchstart=\"this.classList.toggle('active');\">\n";
    	html += "\t<div class=\"flipper\">\n";
    	html += "\t\t<div class=\"front\">\n";

    	html += "\t\t\t<p>" + front + "<br></p>\n";

		html += "\t\t</div>\n";
		html += "\t\t<div class=\"back\">\n";

		html += "\t\t\t<p>" + back + "<br></p>\n";

		html += "\t\t</div>\n";
		html += "\t</div>\n";
		html += "</div>\n";

		return html;
    }
}

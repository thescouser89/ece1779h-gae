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

public class NewStack extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = Authentication.authenticate();

        resp.setContentType("text/html");

        final String stack_name = req.getParameter("stack");
        if(stack_name == null){
            newStack(req, resp);
        } 
        else {
            //TODO: Verify if stack exists for user.
            newCard(req, resp, stack_name);
        }
        
    }

    private void newStack(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        //TODO: Check if stack already exists. If not, create it.

        String loginBar = Authentication.getLoginBar();

        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println(loginBar);

        resp.getWriter().println("</head>");
        resp.getWriter().println("<body>");
        //resp.getWriter().println("<link rel=\"stylesheet\" href=\"stylesheets/mystyle.css\">");

        String newStackForm = 
                "<form action='' method='get'>" +
                "<label for='stack'>" +
                "Please enter a stack name: " +
                "</label><br>" +
                "<input name='stack' id='stack' type='text' size='50' " +
                "value='' /><br>" +
                "<input type='submit' value='Set' />" +
                "</form>";
        resp.getWriter().println(newStackForm);

        resp.getWriter().println("</body>");
        resp.getWriter().println("</html>");
    }

    private void newCard(HttpServletRequest req, HttpServletResponse resp, String stack_name)
            throws IOException {
        String loginBar = Authentication.getLoginBar();
        
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println(loginBar);

        resp.getWriter().println("</head>");
        resp.getWriter().println("<body>");
        //resp.getWriter().println("<link rel=\"stylesheet\" href=\"stylesheets/mystyle.css\">");

        String newCardForm = 
                "<form action='/card' method='post'>" +
                "<label for='question'>" +
                "Question:" +
                "</label><br>" +
                "<input name='question' id='question' type='text' size='50' " +
                "value='' /><br>" +
                "<label for='answer'>" +
                "Answer:" +
                "</label><br>" +
                "<input name='answer' id='answer' type='text' size='50' " +
                "value='' /><br>" +
                "<input type='submit' value='Set' />" +
                "</form>";
        resp.getWriter().println(newCardForm);

        resp.getWriter().println("</body>");
        resp.getWriter().println("</html>");
    }
}

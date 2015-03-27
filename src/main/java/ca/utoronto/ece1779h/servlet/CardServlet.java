//CardServlet.java

package ca.utoronto.ece1779h.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ca.utoronto.ece1779h.model.*;

@SuppressWarnings("serial")
public class CardServlet extends HttpServlet {
    public void doPost(HttpServletRequest req,
                       HttpServletResponse resp)
        throws IOException {
        //UserService userService = UserServiceFactory.getUserService();
        //User user = userService.getCurrentUser();

        //Stack currentStack = UserData.getCurrentStackForUser(user);

        String question = req.getParameter("question");
        String answer = req.getParameter("answer");

        //TODO: Add code to create a new card in memory.
        Flashcard card = new Flashcard();
        card.setQuestion(question);
        card.setAnswer(answer);

        // FOR DEBUG PURPOSES ONLY. SHOULD JUST REDIRECT.
        resp.setContentType("text/html");
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println("New card created.<br>");

        resp.getWriter().println("Question: " + question + "<br>");
        resp.getWriter().println("Answer: " + answer + "<br>");

        resp.getWriter().println("</head>");
        //resp.sendRedirect("/");

        // FOLLOWING LINE DEACTIVATED FOR DEBUG
        //resp.sendRedirect(req.getHeader("referer"));
    }
    
    public void doGet(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
    
}
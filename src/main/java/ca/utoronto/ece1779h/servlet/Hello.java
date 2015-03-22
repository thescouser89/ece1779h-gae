package ca.utoronto.ece1779h.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ca.utoronto.ece1779h.Constants;
import ca.utoronto.ece1779h.calendar.GCalendarService;
import ca.utoronto.ece1779h.model.Flashcard;
import ca.utoronto.ece1779h.model.StackCard;
import com.google.appengine.api.users.User;
import ca.utoronto.ece1779h.calendar.Utils;

import ca.utoronto.ece1779h.authentication.Authentication;

public class Hello extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = Authentication.authenticate();

        String loginBar = Authentication.getLoginBar();

        resp.setContentType("text/html");
        resp.getWriter().println(loginBar);
        resp.getWriter().println("Hello, this is a testing servlet.<br>");
        resp.getWriter().println("App name: " + Constants.getAppId() + "<br>");
        
        StackCard haha = new StackCard();
        haha.setName("heroku");
        haha.save();

        Flashcard card = new Flashcard();
        card.setQuestion("WHo are you?");
        card.setQuestion("I AM GROOOT");
        card.setStackCard(haha.getKey());
        card.save();
        resp.getWriter().println(card);
    }
}

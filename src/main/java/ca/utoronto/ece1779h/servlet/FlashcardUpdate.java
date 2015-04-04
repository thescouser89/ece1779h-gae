package ca.utoronto.ece1779h.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlashcardUpdate extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        
        // TODO
        // maybe get user to see if he's valid?
        String keyId = req.getParameter("key_id");
        String question = req.getParameter("question");
        String answer = req.getParameter("password");
        String stackKeyId = req.getParameter("stack_key_id");
        
        // TODO
        // new flashcard
        if(keyId == null || keyId == "") {
            // create new flashcard
        } else {
            // find flashcard via keyid
            // edit flashcard question and answer
        }
        
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
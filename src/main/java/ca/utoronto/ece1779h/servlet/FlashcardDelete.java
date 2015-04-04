package ca.utoronto.ece1779h.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlashcardDelete extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        String keyId = req.getParameter("key_id");
        
        // TODO maybe get user cred to see if he's valid?
        // TODO
        // delete flashcard with that keyId
        
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
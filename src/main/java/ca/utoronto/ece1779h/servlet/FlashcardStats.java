package ca.utoronto.ece1779h.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlashcardStats extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        
        //String keyId = req.getParameter("id");
        
        // TODO: Set # of rights and wrongs for keyID
        
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
package ca.utoronto.ece1779h.servlet;

import ca.utoronto.ece1779h.model.Flashcard;
import ca.utoronto.ece1779h.model.FlashcardHelper;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlashcardDelete extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        String keyFlash = req.getParameter("keyFlash");
        Key keykeyFlash = KeyFactory.stringToKey(keyFlash);
        FlashcardHelper.deleteFlashcard(keykeyFlash);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
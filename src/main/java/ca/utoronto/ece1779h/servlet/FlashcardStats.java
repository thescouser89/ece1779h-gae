package ca.utoronto.ece1779h.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.Integer;

import ca.utoronto.ece1779h.model.Flashcard;
import ca.utoronto.ece1779h.model.FlashcardHelper;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class FlashcardStats extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        
        //String keyId = req.getParameter("id");
        
        // TODO: Set # of rights and wrongs for keyID
        String keyFlash = req.getParameter("id");
        Key keykeyFlash = KeyFactory.stringToKey(keyFlash);
        Flashcard current = FlashcardHelper.getFlashcard(keykeyFlash);

        //try{
        //	int right = Integer.parseInt(req.getParameter("right"));
        //	int wrong = Integer.parseInt(req.getParameter("wrong"));
        //	
        //	current.setNumberRights(right);
        //	current.setNumberWrongs(wrong);
        //	
        //	current.save();
        //} catch (NumberFormatException e) {}

        String answer = req.getParameter("answer");
        if (answer.equals("right")) {
        	current.incrementRights();
        } else if (answer.equals("wrong")) {
        	current.incrementWrongs();
        }
        //current.save();

        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    	resp.setContentType("application/json");
        resp.getWriter().write("{\"keyFlash\": \"" + 
                KeyFactory.keyToString(keykeyFlash) + "\"}");
    }
}
package ca.utoronto.ece1779h.servlet;

import ca.utoronto.ece1779h.model.Flashcard;
import ca.utoronto.ece1779h.model.FlashcardHelper;
import ca.utoronto.ece1779h.model.Stackcard;
import ca.utoronto.ece1779h.model.StackcardHelper;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlashcardUpdate extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        
        String keyStack = req.getParameter("keyStack");
        String question = req.getParameter("question");
        String answer = req.getParameter("answer");
        String keyFlash = req.getParameter("keyFlash");
        
        Flashcard flash;
        
        // create new flashcard
        if(keyFlash == null || keyFlash.equals("")) {
            Key keyKeyStack = KeyFactory.stringToKey(keyStack);
            Stackcard stack = StackcardHelper.getStackcard(keyKeyStack);
            
            flash = stack.newFlashcard();
        } else {
            // edit existing flashcard
            Key keyKeyFlash = KeyFactory.stringToKey(keyFlash);
            flash = FlashcardHelper.getFlashcard(keyKeyFlash);
        }
        flash.setQuestion(question);
        flash.setAnswer(answer);
        flash.save();

        // set the response, mime-type, and generate some funky json as reply
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.setContentType("application/json");
        resp.getWriter().write("{\"keyFlash\": \"" + 
                KeyFactory.keyToString(flash.getKey()) + "\"}");
    }
}
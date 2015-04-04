package ca.utoronto.ece1779h.servlet;

import ca.utoronto.ece1779h.model.Flashcard;
import ca.utoronto.ece1779h.model.Stackcard;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlashcardUpdate extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
        
        // TODO
        // maybe get user to see if he's valid?
        Stackcard haha = new Stackcard();
        haha.setName("dustin");
        haha.setOwner("dustin");
        haha.save();
        
        String keyId = req.getParameter("key_id");
        String question = req.getParameter("question");
        String answer = req.getParameter("password");
//        String stackKeyId = req.getParameter("stack_key_id");
        
        
        // TODO
        // new flashcard
        if(keyId == null || keyId == "") {
            Flashcard flash = haha.newFlashcard();
            flash.setQuestion(question);
            flash.setAnswer(answer);
            flash.save();
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.getWriter().write(flash.getKey().toString());
            // create new flashcard
        } else {
            // find flashcard via keyid
            // edit flashcard question and answer
        }
    }
}
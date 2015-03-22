package ca.utoronto.ece1779h.model;

import com.google.appengine.api.datastore.Key;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.Override;

@Entity
public class Flashcard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

    @Column(length = 10000)
    private String question;

    @Column(length = 10000)
    private String answer;

    public Key getStackCard() {
        return stackCard;
    }

    public void setStackCard(Key stackCard) {
        this.stackCard = stackCard;
    }

    private Key stackCard;

    public Key getKey() {
        return this.key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Flashcard)) {
            return false;
        }
        Flashcard other = (Flashcard) obj;
        if (key != null) {
            if (!key.equals(other.key)) {
                return false;
            }
        }
        return true;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (key != null)
            result += "key: " + key;
        if (question != null && !question.trim().isEmpty())
            result += ", question: " + question;
        if (answer != null && !answer.trim().isEmpty())
            result += ", answer: " + answer;
        return result;
    }

    public void save() {
        EntityManager em = EMF.get().createEntityManager();
        try {
            em.merge(this);
        } finally {
            em.close();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((question == null) ? 0 : question.hashCode());
        return result;
    }
}
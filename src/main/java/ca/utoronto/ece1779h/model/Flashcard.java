package ca.utoronto.ece1779h.model;

import com.google.appengine.api.datastore.Key;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.Override;
import javax.persistence.Column;

@Entity
public class Flashcard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

    @Column(length = 10000)
    private String question;

    @Column(length = 10000)
    private String answer;

    @Column
    private int numberRights;

    @Column
    private int numberWrongs;

    public Key getStackcardKey() {
        return stackcard;
    }

    public void setStackcardKey(Key stackcard) {
        this.stackcard = stackcard;
    }

    /**
     * Warning: not tested! ping Dustin if that doesn't work
     * @return
     */
    public Stackcard stackcard() {
        EntityManager em = EMF.get().createEntityManager();
        try {
            Query q = em.createQuery("find stackcard from Stackcard stackcard where stackcard.key = :key");
            return (Stackcard) q.setParameter("key", getStackcardKey()).getSingleResult();
        } catch (Exception e) {
            throw e;
        }
    }


    @Column(nullable = false, updatable = false)
    private Key stackcard;

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

    public int getNumberRights() {
        return numberRights;
    }

    public int getNumberWrongs() {
        return numberWrongs;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (question != null && !question.trim().isEmpty())
            result += "question: " + question;
        if (answer != null && !answer.trim().isEmpty())
            result += ", answer: " + answer;
        result += ", numberRights: " + numberRights;
        result += ", numberWrongs: " + numberWrongs;
        return result;
    }

    public int incrementRights() {
        numberRights++;
        save();
        return numberRights;
    }

    public int incrementWrongs() {
        numberWrongs++;
        save();
        return numberWrongs;
    }
}
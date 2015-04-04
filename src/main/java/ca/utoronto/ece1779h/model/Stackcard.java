package ca.utoronto.ece1779h.model;

import com.google.appengine.api.datastore.Key;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.Override;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

@Entity
public class Stackcard implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

    @Column
    private String name;

    @Column(length = 10000, nullable = false, updatable = false)
    private String owner;

    public static List<Stackcard> getStacks(String owner) {
        EntityManager em = EMF.get().createEntityManager();
        try {
            List<Stackcard> stacks;
            Query q = em.createQuery("select stackcard from Stackcard stackcard where stackcard.owner = :owner");
            stacks = new ArrayList<Stackcard>(q.setParameter("owner", owner).getResultList());
            return stacks;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * We know which flashcard belong to a stack because each flashcard must
     * a key parameter that says to which stackcard it belongs.
     * @return
     */
    public List<Flashcard> getFlashcards() {
        EntityManager em = EMF.get().createEntityManager();
        try {
            List<Flashcard> flashcards;
            Query q = em.createQuery("select flash from Flashcard flash where flash.stackcard = :key");
            flashcards = new ArrayList<Flashcard>(q.setParameter("key", this.getKey()).getResultList());
            return flashcards;
        } catch (Exception e) {
            throw e;
        }
    }

    public Flashcard newFlashcard() {
        Flashcard flash = new Flashcard();
        flash.setStackcardKey(this.getKey());
        return flash;
    }

    public Key getKey() {
        return this.key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Stackcard)) {
            return false;
        }
        Stackcard other = (Stackcard) obj;
        if (key != null) {
            if (!key.equals(other.key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public void save() {
		StackcardHelper.removeFromCache(key);
        EntityManager em = EMF.get().createEntityManager();
        try {
            em.merge(this);
        } finally {
            em.close();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (name != null && !name.trim().isEmpty())
            result += "name: " + name;
        if (owner != null && !owner.trim().isEmpty())
            result += ", owner: " + owner;
        return result;
    }

    // to implement
    public void getStats() {};
    public void clearStats() {};
}
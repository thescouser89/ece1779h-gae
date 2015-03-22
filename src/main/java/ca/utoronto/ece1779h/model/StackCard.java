package ca.utoronto.ece1779h.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.Override;

import javax.persistence.Column;

@Entity
public class StackCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

    @Column
    private String name;

    public Key getKey() {
        return this.key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StackCard)) {
            return false;
        }
        StackCard other = (StackCard) obj;
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

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (key != null)
            result += "key: " + key;
        if (name != null && !name.trim().isEmpty())
            result += ", name: " + name;
        return result;
    }
}
package ca.utoronto.ece1779h.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
    private static final EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("transactions-optional");

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}

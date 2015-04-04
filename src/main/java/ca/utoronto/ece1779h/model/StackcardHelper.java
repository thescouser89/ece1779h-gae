package ca.utoronto.ece1779h.model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.*;

import javax.persistence.*;

public class StackcardHelper {

	// Finds and returns Stackcard with given key. If not found, returns null.
    public static Stackcard getStackcard(Key key){
	
		// First check memcache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		try {
			if (memcache.contains(key)){
				return (Stackcard) memcache.get(key);
			}
		} catch (MemcacheServiceException e){
		
		}
		
		// Not in cache; check datastore
		EntityManager em = EMF.get().createEntityManager();
		Stackcard fc = null;
		try {
			fc = em.find(Stackcard.class, key);
		} finally {
			em.close();
		}
		
		// If found, store it in the cache
		if (fc != null){
			memcache.put(key, fc);
		}
	
		return fc;
	}
	
	public static void deleteStackcard(Key key){
	
		// if in memcache, delete it from there
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		try {
			if (memcache.contains(key)){
				memcache.delete(key);
			}
		} catch (MemcacheServiceException e){
		
		}
		
		// Delete from datastore
		EntityManager em = EMF.get().createEntityManager();
		try {
			if (em.find(Stackcard.class, key) != null){
				em.getTransaction().begin();
				em.remove(em.find(Stackcard.class, key) );
				em.getTransaction().commit();
			}
		} finally {
			em.close();
		}
		
		return;
	}
	
	public static void removeFromCache(Key key) {
		// if in memcache, delete it from there
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		try {
			if (memcache.contains(key)){
				memcache.delete(key);
			}
		} catch (MemcacheServiceException e){
		
		}
	}
	
	public static Stackcard createStackcard(String name, String owner){
		EntityManager em = EMF.get().createEntityManager();
		Stackcard fc = null;
		try {
			fc = new Stackcard();
			fc.setOwner(owner);
			fc.setName(name);
			em.getTransaction().begin();
			em.persist(fc);
			em.getTransaction().commit();
		} finally {
			em.close();
			return fc;
		}
	}
	
	// public static Stackcard createStackcard(Key stackcard, String question, String answer){
		// return createStackcard(stackcard, question, answer, 0, 0);
	// }
}
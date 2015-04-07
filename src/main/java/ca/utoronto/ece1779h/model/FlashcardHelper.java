package ca.utoronto.ece1779h.model;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.*;
import java.util.List;

import javax.persistence.*;

public class FlashcardHelper {

	// Finds and returns flashcard with given key. If not found, returns null.
    public static Flashcard getFlashcard(Key key){
	
		// First check memcache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService("FC");
		try {
			if (memcache.contains(key)){
				return (Flashcard) memcache.get(key);
			}
		} catch (MemcacheServiceException e){
		
		}
		
		// Not in cache; check datastore
		EntityManager em = EMF.get().createEntityManager();
		Flashcard fc = null;
		try {
			fc = em.find(Flashcard.class, key);
		} catch (Exception e) {
		
		} finally {
			em.close();
		}
		
		// If found, store it in the cache
		if (fc != null){
			memcache.put(key, fc);
		}
	
		return fc;
	}
	
	public static List<Flashcard> getFlashcardsFromStack (Stackcard stack){
		// First check memcache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService("FCS");
		try {
			if (memcache.contains(stack.getKey())){
				return (List<Flashcard>) memcache.get(stack.getKey());
			}
		} catch (MemcacheServiceException e){
		
		}
		
		// If not in memcache, get from datastore (but first put it into memcache)
		List<Flashcard> fcs = stack.getFlashcards();
		memcache.put(stack.getKey(),fcs);
		return fcs;
	}
	
	public static void deleteFlashcard(Key key){
	
		// if in memcache, delete it from there
		removeFromCache(key);
		
		// Delete from datastore
		EntityManager em = EMF.get().createEntityManager();
		try {
			if (em.find(Flashcard.class, key) != null){
				em.getTransaction().begin();
				em.remove(em.find(Flashcard.class, key) );
				em.getTransaction().commit();
			}
		} finally {
			em.close();
		}
	
		return;
	}
	
	public static void removeFromCache(Key key) {
		// if the stack to which this fc belongs was cached, remove it
		Flashcard fc = getFlashcard(key);
		if (fc != null){
			MemcacheService stackmemcache = MemcacheServiceFactory.getMemcacheService("FCS");
			try {
				if (stackmemcache.contains(fc.getStackcardKey())){
					stackmemcache.delete(fc.getStackcardKey());
				}
			} catch (MemcacheServiceException e){
			
			}
		}
	
		// if in memcache, delete it from there
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService("FC");
		try {
			if (memcache.contains(key)){
				memcache.delete(key);
			}
		} catch (MemcacheServiceException e){
		
		}
	}
	
	public static void removeStackFromCache(Key key) {
		// if in memcache, delete it from there
		MemcacheService stackmemcache = MemcacheServiceFactory.getMemcacheService("FCS");
		try {
			if (stackmemcache.contains(key)){
				stackmemcache.delete(key);
			}
		} catch (MemcacheServiceException e){
		
		}
	}
	
	public static Flashcard createFlashcard(Key stackcard, String question, String answer, int numberRights, int numberWrongs){
		EntityManager em = EMF.get().createEntityManager();
		Flashcard fc = null;
		try {
			fc = new Flashcard();
			fc.setStackcardKey(stackcard);
			fc.setQuestion(question);
			fc.setAnswer(answer);
			em.getTransaction().begin();
			em.persist(fc);
			em.getTransaction().commit();
		} finally {
			em.close();
			return fc;
		}
	}
	
	// public static Flashcard createFlashcard(Key stackcard, String question, String answer){
		// return createFlashCard(stackcard, question, answer, 0, 0);
	// }
}
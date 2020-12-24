package org.uqbarproject.jpa.java8.extras;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author gprieto
 * @author flbulgarelli
 */
public class PerThreadEntityManagers {

   private static EntityManagerFactory emf;

   private static ThreadLocal<EntityManager> threadLocal;

   static {
      try {
         emf = Persistence.createEntityManagerFactory("db");
         threadLocal = new ThreadLocal<>();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Returns the entity manager attached to the current thread, if any and is open. 
    * Otherwise, it creates a new one and attaches it. 
    * 
    * @throws IllegalStateException if {@link #shutdown()} has been already called
    */
   public static EntityManager get() {
     if (emf == null) {
       throw new IllegalStateException("Can not get an entity manager after shutdown"); 
     }
     return getEntityManager(); 
   }

   /**
    * @deprecated use {@link #get()} instead
    */
   @Deprecated
   public static EntityManager getEntityManager() {
      EntityManager manager = threadLocal.get();
      if (manager == null || !manager.isOpen()) {
         manager = emf.createEntityManager();
         threadLocal.set(manager);
      }
      return manager;
   }
   
   /**
    * Tells whether an entity manager is attached
    * to the current thread 
    */
   public static boolean isAttached() {
      return threadLocal.get() != null; 
   }
   
   /**
    * @deprecated use {@link #dispose()}
    */
   @Deprecated
   public static void closeEntityManager() {
     EntityManager em = threadLocal.get();
     threadLocal.set(null);
     em.close();        
  }

   /**
    * Closes and dereferences the currently attached entity
    * manager, if any 
    */
   public static void dispose() {
      EntityManager em = threadLocal.get();
      threadLocal.set(null);
      if (em != null) {
        em.close();        
      }
   }
   
   public synchronized static void shutdown() {
     emf.close();
     emf = null;
   }
}

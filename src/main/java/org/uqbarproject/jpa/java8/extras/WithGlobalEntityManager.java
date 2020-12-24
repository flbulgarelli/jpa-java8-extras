package org.uqbarproject.jpa.java8.extras;

import javax.persistence.EntityManager;

public interface WithGlobalEntityManager  extends WithEntityManager {
   
   default EntityManager entityManager() {
      return PerThreadEntityManagers.get();
   }
}

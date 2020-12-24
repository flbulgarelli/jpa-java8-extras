package org.uqbarproject.jpa.java8.extras;

import static org.junit.Assert.assertFalse;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PerThreadEntityManagersTest {

  @Before
  public void closeEntitityManager() {
    PerThreadEntityManagers.dispose();
  }

  @Test
  public void entitiyManagerIsNotInitiallyAttached() {
    assertFalse(PerThreadEntityManagers.isAttached());
  }
  
  @Test
  public void entitiyManagerIsAttachedOnDemand() {
    assertNotNull(PerThreadEntityManagers.get());
    assertTrue(PerThreadEntityManagers.isAttached());
  }
  
  @Test
  public void theSameEntityManagerIsConsistentlyReturned() {
    assertSame(PerThreadEntityManagers.get(), PerThreadEntityManagers.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfDisposed() {
    EntityManager e1 = PerThreadEntityManagers.get();
    
    PerThreadEntityManagers.dispose();
    
    assertNotSame(e1, PerThreadEntityManagers.get());
  }
  
  @Test
  public void aDifferentEntityManagerIsReturnedIfExternallyClosed() {
    EntityManager e1 = PerThreadEntityManagers.get();
    
    e1.close();
    
    assertNotSame(e1, PerThreadEntityManagers.get());
  }  
}

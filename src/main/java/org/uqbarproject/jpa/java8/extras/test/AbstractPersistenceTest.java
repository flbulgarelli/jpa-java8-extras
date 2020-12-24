package org.uqbarproject.jpa.java8.extras.test;

import org.junit.After;
import org.junit.Before;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public abstract class AbstractPersistenceTest implements
		TransactionalOps, EntityManagerOps {

	@Before
	public void setupPersistence() {
		beginTransaction();
	}

	@After
	public void tearDownPersistence() {
		rollbackTransaction();
	}

}

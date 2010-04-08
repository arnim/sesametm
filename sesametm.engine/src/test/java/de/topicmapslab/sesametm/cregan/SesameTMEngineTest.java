/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan;

import junit.framework.TestCase;

import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.sail.memory.MemoryStore;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

public class SesameTMEngineTest extends TestCase {

	@Test
	public final void testSetExpliciteRepository() throws Exception {
		String CONNECTION = "de.topicmapslab.sesametm.vocabularies.connection";
		TopicMapSystemFactory tmSysFactory = TopicMapSystemFactory
				.newInstance();
		assertNull(tmSysFactory.getProperty(CONNECTION));
		Repository myRepository = new SailRepository(new MemoryStore());
		myRepository.initialize();
		RepositoryConnection con = myRepository.getConnection();
		tmSysFactory.setProperty(CONNECTION, con);
		assertEquals(con, tmSysFactory.getProperty(CONNECTION));
	}
	
	
	@Test
	public final void testSetImpliciteRepository() throws Exception {
		String CONNECTION = "de.topicmapslab.sesametm.vocabularies.connection";
		TopicMapSystemFactory tmSysFactory = TopicMapSystemFactory
				.newInstance();
		assertNull(tmSysFactory.getProperty(CONNECTION));
		TopicMapSystem tmSys = tmSysFactory.newTopicMapSystem();
		assertEquals(SailRepositoryConnection.class, tmSys.getProperty(CONNECTION).getClass());
	}
	
	
	@Test
	public final void testGetTmByLocator() throws Exception {
		TopicMapSystemFactory tmSysFactory = TopicMapSystemFactory
				.newInstance();
		TopicMapSystem tmSys = tmSysFactory.newTopicMapSystem();
		TopicMap tm1 = tmSys.createTopicMap("http://www.ex.com/map1");
		tmSys.createTopicMap("http://www.ex.com/map2");
		assertEquals(tm1, tmSys.getTopicMap("http://www.ex.com/map1"));
		assertNull(tmSys.getTopicMap("http://www.ex.com/map3"));
		assertNotSame(tm1, tmSys.getTopicMap("http://www.ex.com/map2"));
	}


}

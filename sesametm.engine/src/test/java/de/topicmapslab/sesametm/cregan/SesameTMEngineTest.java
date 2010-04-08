/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan;

import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

import junit.framework.TestCase;

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

		TopicMapSystem tmSys = tmSysFactory.newTopicMapSystem();

		TopicMap tm = tmSys.createTopicMap("http://www.example.org/map1");
//		System.out.println(tm.getClass().toString());

	}

}

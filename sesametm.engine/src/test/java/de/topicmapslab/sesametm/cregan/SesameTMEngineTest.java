/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan;

import org.junit.Test;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

import junit.framework.TestCase;

public class SesameTMEngineTest extends TestCase {

	@Test
	public final void testRepository() throws Exception {
		TopicMapSystemFactory tmSysFactory = TopicMapSystemFactory.newInstance();
		TopicMapSystem tmSys = tmSysFactory.newTopicMapSystem();
		TopicMap tm = tmSys.createTopicMap("http://www.example.org/map1");
//		System.out.println(tm.getLocator().toExternalForm());


	}

}

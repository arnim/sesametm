/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan;

import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Test;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapix.io.XTM20TopicMapReader;
import org.tmapix.io.XTMTopicMapReader;

import de.topicmapslab.sesametm.topicmaps.Accessor;

public class SesameTMTMAPIxTest extends TestCase {

	
	@Test
	public final void testXTM20TopicMapReader() throws Exception {
		TopicMapSystemFactory tmSysFactory = TopicMapSystemFactory
				.newInstance();
		TopicMapSystem tmSys = tmSysFactory.newTopicMapSystem();
		TopicMap tm = tmSys.createTopicMap("http://www.ex.com/map1");
		Accessor accessor = new Accessor();
		InputStream stream = accessor.convertStringToInputStream("toytm3.xtm");	
		assertNotNull(stream);
		XTM20TopicMapReader reader = new XTM20TopicMapReader(tm, stream, "http://www.ex.com/some");
        reader.read();
        assertEquals(113, tm.getTopics().size());
	}
	
	
	@Test
	public final void testXTMTopicMapReader() throws Exception {
		TopicMapSystemFactory tmSysFactory = TopicMapSystemFactory
				.newInstance();
		TopicMapSystem tmSys = tmSysFactory.newTopicMapSystem();
		TopicMap tm = tmSys.createTopicMap("http://www.ex.com/map1");
		Accessor accessor = new Accessor();
		InputStream stream = accessor.convertStringToInputStream("toytm3.xtm");	
		assertNotNull(stream);
		XTMTopicMapReader reader = new XTMTopicMapReader(tm, stream, "http://www.ex.com/some");
        reader.read();
        assertEquals(113, tm.getTopics().size());
	}

}

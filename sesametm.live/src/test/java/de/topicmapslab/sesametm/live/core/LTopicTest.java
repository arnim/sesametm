/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.util.Set;

import org.junit.Test;
import org.tmapi.core.Locator;
import org.tmapi.core.Topic;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.LBaseCase;

/**
 * @author Arnim Bleier
 * 
 */
public class LTopicTest extends LBaseCase {

  public LTopicTest() {
    super("topicTest", "topicTest.mapping");
  }

  @Test
  public void testGetTypes() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic topic = _tm.getTopicBySubjectIdentifier(locator);
    Set<Topic> types = topic.getTypes();
    assertEquals(types.size(), 2);
  }

}

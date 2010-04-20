/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.core.LTopic;
import de.topicmapslab.sesametm.live.core.SparqlTopicMap;
import de.topicmapslab.sesametm.live.LTestCase;

/**
 * @author Arnim Bleier
 * 
 */
public class LTypeTest extends LTestCase {

  public LTypeTest() {
    super("typeTest", "typeTest.mapping");
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getConstructById(java.lang.String)}
   * .
   */
  @Test
  public void testGetNames() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Set<Name> names = lumpi.getNames();
    assertEquals(4, names.size());
    Iterator<Name> nIterator = names.iterator();
    while (nIterator.hasNext()) {
      Name name = nIterator.next();
      assertEquals(lumpi, name.getParent());
    }
  }

  @Test
  public void testGetScope() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Topic familyName = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/familyName"));
    Topic scope = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#nameScopingTopic"));
    Set<Name> names = lumpi.getNames();
    Iterator<Name> nIterator = names.iterator();
    assertEquals(4, names.size());
    while (nIterator.hasNext()) {
      Name name = nIterator.next();
      if (name.getType().equals(familyName)) {
        assertEquals(1, name.getScope().size());
        assertEquals(scope, name.getScope().iterator().next());
      }
    }
  }

  @Test
  public void testGetNamesByType() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Topic additionalName = new LTopic(new SesameLocator(
        "http://xmlns.com/foaf/0.1/additionalName"), (SparqlTopicMap) _tm);
    Set<Name> names = lumpi.getNames(additionalName);
    assertEquals(2, names.size());
    Iterator<Name> nIterator = names.iterator();
    while (nIterator.hasNext()) {
      Name name = nIterator.next();
      assertEquals(lumpi, name.getParent());
    }
    Topic familyName = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#nameTypeTopic"));
    names = lumpi.getNames(familyName);
    assertEquals(1, names.size());
    nIterator = names.iterator();
    while (nIterator.hasNext()) {
      Name name = nIterator.next();
      assertEquals("Doggi", name.getValue());
      assertEquals(lumpi, name.getParent());
      assertEquals(familyName, name.getType());
    }
  }

  @Test
  public void testGetOccurencesByType() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Topic occurnenceTypeTopic = _tm
        .getTopicBySubjectIdentifier(new SesameLocator(
            "http://www.example.com/things#occurnenceTypeTopic"));
    Set<Occurrence> occurences = lumpi.getOccurrences(occurnenceTypeTopic);
    assertEquals(1, occurences.size());
    Occurrence o = occurences.iterator().next();
    assertEquals(occurnenceTypeTopic, o.getType());
  }

}

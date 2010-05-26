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
import de.topicmapslab.sesametm.live.LBaseCase;

/**
 * @author Arnim Bleier
 * 
 */
public class LOccurenceTest extends LBaseCase {

  public LOccurenceTest() {
    super("occurenceTest", "occurenceTest.mapping");
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getConstructById(java.lang.String)}
   * .
   */
  @Test
  public void testGetOccurrences() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Set<Occurrence> occurences = lumpi.getOccurrences();
    assertEquals(4, occurences.size());
    Iterator<Occurrence> oIterator = occurences.iterator();
    while (oIterator.hasNext()) {
      Occurrence occurrence = oIterator.next();
      assertEquals(lumpi, occurrence.getParent());
    }
  }

  @Test
  public void testGetOccurrencesByType() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Topic age = new LTopic(new SesameLocator(
        "http://www.example.com/things#age"), (SparqlTopicMap) _tm);
    Set<Occurrence> occurences = lumpi.getOccurrences(age);
    assertEquals(2, occurences.size());
    Iterator<Occurrence> oIterator = occurences.iterator();
    while (oIterator.hasNext()) {
      Occurrence occurrence = oIterator.next();
      assertEquals(lumpi, occurrence.getParent());
    }
    Topic countryOfBirth = new LTopic(new SesameLocator(
        "http://www.example.com/things#countryOfBirth"), (SparqlTopicMap) _tm);
    occurences = lumpi.getOccurrences(countryOfBirth);
    assertEquals(1, occurences.size());
    oIterator = occurences.iterator();
    while (oIterator.hasNext()) {
      Occurrence occurrence = oIterator.next();
      assertEquals(
          new SesameLocator("http://www.w3.org/2001/XMLSchema#string"),
          occurrence.getDatatype());
      assertEquals("Hong Kong", occurrence.getValue());
      assertEquals(lumpi, occurrence.getParent());
      assertEquals(countryOfBirth, occurrence.getType());
    }
  }

  @Test
  public void testGetOccurencesWithType() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Topic age = new LTopic(new SesameLocator(
        "http://www.example.com/things#age"), (SparqlTopicMap) _tm);
    Set<Occurrence> occurences = lumpi.getOccurrences(age);
    assertEquals(2, occurences.size());
    Iterator<Occurrence> oIterator = occurences.iterator();
    while (oIterator.hasNext()) {
      Occurrence occurrence = oIterator.next();
      assertEquals(lumpi, occurrence.getParent());
    }
    Topic countryOfBirth = new LTopic(new SesameLocator(
        "http://www.example.com/things#countryOfBirth"), (SparqlTopicMap) _tm);
    occurences = lumpi.getOccurrences(countryOfBirth);
    assertEquals(1, occurences.size());
    oIterator = occurences.iterator();
    while (oIterator.hasNext()) {
      Occurrence occurrence = oIterator.next();
      assertEquals(
          new SesameLocator("http://www.w3.org/2001/XMLSchema#string"),
          occurrence.getDatatype());
      assertEquals("Hong Kong", occurrence.getValue());
      assertEquals(lumpi, occurrence.getParent());
      assertEquals(countryOfBirth, occurrence.getType());
    }
  }

  @Test
  public void testGetOccurrencesExceptions() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Topic countryOfBirth = new LTopic(new SesameLocator(
        "http://www.example.com/things#countryOfBirth"), (SparqlTopicMap) _tm);
    Set<Occurrence> occurences = lumpi.getOccurrences(countryOfBirth);
    Iterator<Occurrence> oIterator = occurences.iterator();
    while (oIterator.hasNext()) {
      Occurrence occurrence = oIterator.next();
      try {
        occurrence.decimalValue();
        fail("no NumberFormatException thrown!!");
      } catch (NumberFormatException e) {
      }
      try {
        occurrence.floatValue();
        fail("no NumberFormatException thrown!!");
      } catch (NumberFormatException e) {
      }
      try {
        occurrence.intValue();
        fail("no NumberFormatException thrown!!");
      } catch (NumberFormatException e) {
      }
      try {
        occurrence.integerValue();
        fail("no NumberFormatException thrown!!");
      } catch (NumberFormatException e) {
      }
      try {
        occurrence.longValue();
        fail("no NumberFormatException thrown!!");
      } catch (NumberFormatException e) {
      }
      try {
        occurrence.locatorValue();
        fail("no IllegalArgumentException thrown!!");
      } catch (IllegalArgumentException e) {
      }
    }
  }

  @Test
  public void testGetScope() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Topic countryOfBirth = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#countryOfBirth"));
    Topic scope = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#occurrenceScopingTopic"));
    Set<Occurrence> occurences = lumpi.getOccurrences();
    Iterator<Occurrence> oIterator = occurences.iterator();
    assertEquals(4, occurences.size());
    while (oIterator.hasNext()) {
      Occurrence occurrence = oIterator.next();
      if (occurrence.getType().equals(countryOfBirth)) {
        assertEquals(1, occurrence.getScope().size());
        assertEquals(scope, occurrence.getScope().iterator().next());
      } else {
        assertTrue(occurrence.getScope().isEmpty());
      }
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getConstructById(java.lang.String)}
   * .
   */
  @Test
  public void testGetNoNames() {
    Locator locator = new SesameLocator("http://www.example.com/things#Lumpi");
    Topic lumpi = _tm.getTopicBySubjectIdentifier(locator);
    Set<Name> names = lumpi.getNames();
    assertEquals(0, names.size());
  }

}

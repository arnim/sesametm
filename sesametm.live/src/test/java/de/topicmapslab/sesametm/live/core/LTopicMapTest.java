/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.Locator;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.utils.SesameTMUnsupportedOperationException;
import de.topicmapslab.sesametm.live.LBaseCase;

/**
 * @author Arnim Bleier
 * 
 */
public class LTopicMapTest extends LBaseCase {

  public LTopicMapTest() {
    super("topicMapTest", "topicMapTest.mapping");
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#addItemIdentifier(org.tmapi.core.Locator)}
   * .
   */
  @Test
  public void testAddItemIdentifier() {
    try {
      _tm.addItemIdentifier(_defaultLocator);
      fail("READ Only!!!");
    } catch (Exception e) {
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getId()}.
   */
  @Test
  public void testGetId() {
    assertEquals(_defaultLocator.getReference(), _tm.getId());
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getItemIdentifiers()}
   * .
   */
  @Test
  public void testGetItemIdentifiers() {
    Set<Locator> iis = _tm.getItemIdentifiers();
    assertEquals(iis.size(), 1);
    assertEquals(iis.iterator().next(), _defaultLocator);
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getParent()}.
   */
  @Test
  public void testGetParent() {
    assertNull(_tm.getParent());
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getTopicMap()}.
   */
  @Test
  public void testGetTopicMap() {
    assertEquals(_tm, _tm.getTopicMap());
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#remove()}.
   */
  @Test
  public void testRemove() {
    try {
      _tm.remove();
      fail("READ Only!!!");
    } catch (SesameTMUnsupportedOperationException e) {
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#removeItemIdentifier(org.tmapi.core.Locator)}
   * .
   */
  @Test
  public void testRemoveItemIdentifier() {
    try {
      _tm.removeItemIdentifier(null);
      fail("READ Only!!!");
    } catch (SesameTMUnsupportedOperationException e) {
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#createLocator(java.lang.String)}
   * .
   */
  @Test
  public void testCreateLocator() {
    String testLocator = "test:loc";
    Locator l = _tm.createLocator(testLocator);
    assertEquals(l.getReference(), testLocator);
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#createTopic()}.
   */
  @Test
  public void testCreateTopic() {
    try {
      _tm.createTopic();
      fail("READ Only!!!");
    } catch (SesameTMUnsupportedOperationException e) {
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getAssociations()}.
   */
  @Test
  public void testGetAssociations() {
    assertEquals(4, _tm.getAssociations().size());
    Iterator<Association> as = _tm.getAssociations().iterator();
    while (as.hasNext()) {
      Association association = as.next();
      assertEquals(2, association.getRoles().size());
      if (!association.getScope().isEmpty())
        assertEquals("http://www.example.com/things#assoScopingTopic",
            association.getScope().iterator().next().getSubjectIdentifiers()
                .iterator().next().getReference());
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.LTopic#getRolesPlayed()}.
   */
  @Test
  public void testTopicGetRolesPlayed() {
    Topic fritz = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Fritz"));
    Set<Role> roles = fritz.getRolesPlayed();
    assertEquals(4, roles.size());
    Iterator<Role> rolesIterator = roles.iterator();
    Topic knows = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/knows"));
    Topic pokeAssoTypeTopic = _tm
        .getTopicBySubjectIdentifier(new SesameLocator(
            "http://www.example.com/things#pokeAssoTypeTopic"));
    while (rolesIterator.hasNext()) {
      Role role = rolesIterator.next();
      assertEquals(fritz, role.getPlayer());
      if (!role.getParent().getType().equals(knows))
        assertEquals(pokeAssoTypeTopic, role.getParent().getType());
    }
    Topic lumpi = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi"));
    roles = lumpi.getRolesPlayed();
    assertEquals(4, roles.size());
    rolesIterator = roles.iterator();
    while (rolesIterator.hasNext()) {
      Role role = rolesIterator.next();
      assertEquals(lumpi, role.getPlayer());
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.LTopic#getRolesPlayed()}.
   */
  @Test
  public void testTopicGetRolesPlayedWithType() {
    Topic lumpi = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi"));
    Topic person = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Person"));
    Topic friend = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Friend"));
    Set<Role> roles = lumpi.getRolesPlayed(person);
    Iterator<Role> rolesIterator = roles.iterator();
    while (rolesIterator.hasNext()) {
      Role role = rolesIterator.next();
      assertEquals(person, role.getType());
    }
    roles = lumpi.getRolesPlayed(friend);
    rolesIterator = roles.iterator();
    while (rolesIterator.hasNext()) {
      Role role = rolesIterator.next();
      assertEquals(friend, role.getType());
    }
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getConstructById(java.lang.String)}
   * .
   */
  @Test
  public void testGetConstructById() {
    String idCorupted1 = "Stringdsdfa";
    String idCorupted2 = "http://xmlns.com/foaf/0.1/?constructType=Association?associationType=http://xmlns.com/foaf/0.1/knowsc?subjectPayer=http://www.example.com/things#Fritz?objectPayer=http://www.example.com/things#Jens";
    String id1 = "http://xmlns.com/foaf/0.1/?constructType=Association?associationType=http://xmlns.com/foaf/0.1/knows?subjectPayer=http://www.example.com/things#Fritz?objectPayer=http://www.example.com/things#Jens";
    Construct c1 = _tm.getConstructById(id1);
    assertEquals(id1, c1.getId());
    String id2 = "http://www.example.com/things#Lumpi";
    Construct c2 = _tm.getConstructById(id2);
    assertEquals(id2, c2.getId());
    Construct cc1 = _tm.getConstructById(idCorupted1);
    Construct cc2 = _tm.getConstructById(idCorupted2);
    assertNull(cc1);
    assertNull(cc2);
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getTopicBySubjectIdentifier(org.tmapi.core.Locator)}
   * .
   */
  @Test
  public void testGetTopicBySubjectIdentifier() {
    Locator locator1 = new SesameLocator("http://www.example.com/things#Lumpi");
    Locator locator2 = new SesameLocator(
        "http://www.example.com/things#LumpisSI");
    Topic lumpi1 = _tm.getTopicBySubjectIdentifier(locator1);
    Topic lumpi2 = _tm.getTopicBySubjectIdentifier(locator2);
    assertNotNull(lumpi2);
    assertEquals(lumpi1, lumpi2);
    Set<Locator> lumpisSIs = lumpi1.getSubjectIdentifiers();
    assertTrue(lumpisSIs.contains(locator1));
    assertTrue(lumpisSIs.contains(locator2));
    assertEquals(lumpisSIs.size(), 2);
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getTopicBySubjectLocator(org.tmapi.core.Locator)}
   * .
   */
  @Test
  public void testGetTopicBySubjectLocator() {
    Locator lumpiSI = new SesameLocator("http://www.example.com/things#Lumpi");
    Locator lumpiSL1 = new SesameLocator(
        "http://www.example.com/things#LumpisLS1");
    Locator lumpiSL2 = new SesameLocator(
        "http://www.example.com/things#LumpisLS2");
    Topic lumpiBySI = _tm.getTopicBySubjectIdentifier(lumpiSI);
    Topic lumpiBySL1 = _tm.getTopicBySubjectLocator(lumpiSL1);
    Topic lumpiBySL2 = _tm.getTopicBySubjectLocator(lumpiSL2);
    assertEquals(lumpiBySI, lumpiBySL1);
    assertEquals(lumpiBySI, lumpiBySL2);
    assertNull(_tm.getTopicBySubjectLocator(new SesameLocator(
        "http://www.example.com/things#notLumpisLocator")));
    Set<Locator> lumpisSLs = lumpiBySL2.getSubjectLocators();
    assertEquals(2, lumpisSLs.size());
    assertTrue(lumpisSLs.contains(lumpiSL1));
    assertTrue(lumpisSLs.contains(lumpiSL2));
  }

  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getTopics()}.
   */
  @Test
  public void testGetTopics() {
    Set<Topic> topics = _tm.getTopics();
    assertEquals(10, topics.size());
    Locator locatorLumpi = new SesameLocator(
        "http://www.example.com/things#Lumpi");
    assertTrue(topics.contains(_tm.getTopicBySubjectIdentifier(locatorLumpi)));
    Locator locatorHans = new SesameLocator(
        "http://www.example.com/things#Hans");
    assertTrue(topics.contains(_tm.getTopicBySubjectIdentifier(locatorHans)));
    Locator locatorFritz = new SesameLocator(
        "http://www.example.com/things#Fritz");
    assertTrue(topics.contains(_tm.getTopicBySubjectIdentifier(locatorFritz)));
    Locator locatorThing = new SesameLocator(
        "http://www.example.com/things#Thing");
    assertTrue(topics.contains(_tm.getTopicBySubjectIdentifier(locatorThing)));
    Locator locatorPerson = new SesameLocator(
        "http://xmlns.com/foaf/0.1/Person");
    assertTrue(topics.contains(_tm.getTopicBySubjectIdentifier(locatorPerson)));
    Locator locatorFriend = new SesameLocator(
        "http://xmlns.com/foaf/0.1/Friend");
    assertTrue(topics.contains(_tm.getTopicBySubjectIdentifier(locatorFriend)));
    Locator locatorKnows = new SesameLocator("http://xmlns.com/foaf/0.1/knows");
    assertTrue(topics.contains(_tm.getTopicBySubjectIdentifier(locatorKnows)));
    Locator locatorassoScopingTopic = new SesameLocator(
        "http://www.example.com/things#assoScopingTopic");
    assertTrue(topics.contains(_tm
        .getTopicBySubjectIdentifier(locatorassoScopingTopic)));

    Locator pokeAssoTypeTopic = new SesameLocator(
        "http://www.example.com/things#pokeAssoTypeTopic");
    assertTrue(topics.contains(_tm
        .getTopicBySubjectIdentifier(pokeAssoTypeTopic)));
  }

}

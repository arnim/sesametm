/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.tmapi.core.Association;
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystemFactory;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.core.LTopicMapSystem;
import de.topicmapslab.sesametm.vocabularies.PROPERTY;
import de.topicmapslab.sesametm.live.testn3content.TestCotentAccessor;

/**
 * @author Arnim Bleier
 * 
 */
public class LRemoteTest extends TestCase {

  private static String mappingFile = "dbpedia.rdf2tm.n3";
  private static String sparqlEndpoint = "http://dbpedia.org/sparql";
  private LTopicMapSystem _sys;
  private Locator _defaultLocator;
  private TopicMap _tm;
  
  @Override
  protected void setUp() throws Exception {
    TestCotentAccessor contentAccesor = new TestCotentAccessor();
    TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
    factory.setProperty(PROPERTY.READONLY, true);
    factory.setProperty(PROPERTY.MAPPINGVOCAB, contentAccesor
        .convertStringToInputStream(mappingFile));
    factory.setProperty(PROPERTY.SPARQLENDPOINT, sparqlEndpoint);
    _sys = (LTopicMapSystem) factory.newTopicMapSystem();
    _defaultLocator = _sys.createLocator("ha:lo");
    _tm = _sys.createTopicMap(_defaultLocator);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    _sys.close();
  }
  /**
   * Test method for
   * {@link de.topicmapslab.sesametm.live.core.SparqlTopicMap#getConstructById(java.lang.String)}
   * .
   */
  @Test
  public void testDBPedia() {
    Topic alexander = _tm.getTopicBySubjectIdentifier(new SesameLocator("http://dbpedia.org/resource/Alexander_Pushkin"));
    Topic foafName = _tm.getTopicBySubjectIdentifier(new SesameLocator("http://xmlns.com/foaf/0.1/name"));
    
    
    Set<Name> ans = alexander.getNames();
    assertEquals(2, ans.size());
    Iterator<Name> ni = ans.iterator();
    while (ni.hasNext()) {
      Name name = ni.next();
      assertTrue(name.getValue().toString().contains("Aleksandr Serge")); 
      assertEquals(foafName, name.getType());
      assertEquals(alexander, name.getParent());
    }
    Topic influenced = _tm.getTopicBySubjectIdentifier(new SesameLocator("http://dbpedia.org/property/influenced"));
    Topic model = _tm.getTopicBySubjectIdentifier(new SesameLocator("http://example.org/vocab#role-model"));
    Topic student = _tm.getTopicBySubjectIdentifier(new SesameLocator("http://example.org/vocab#student"));
    Set<Role> alexanderRolesPlayed = alexander.getRolesPlayed(model);
    Iterator<Role> alexanderRolesPlayedI = alexanderRolesPlayed.iterator();
    while (alexanderRolesPlayedI.hasNext()) { 
      Role role = alexanderRolesPlayedI.next();
      assertEquals(model, role.getType());
      Association a = role.getParent();
      assertEquals(influenced, a.getType());
      assertEquals(2, a.getRoles().size());
      Set<Topic> rt = role.getParent().getRoleTypes();
      assertEquals(2, rt.size());
      assertTrue(rt.contains(model));
      assertTrue(rt.contains(student));
      assertEquals(1, a.getRoles(model).size());
      assertEquals(1, a.getRoles(student).size());
      assertTrue(a.getRoles(model).iterator().next().getType().equals(model));
      assertTrue(a.getRoles(student).iterator().next().getType().equals(student));
      assertTrue(a.getRoles(model).iterator().next().getPlayer().equals(alexander));
    }
  }


}

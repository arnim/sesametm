/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.tmapi.core.Association;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.LBaseCase;

public class LTypeInstanceIndexTest extends LBaseCase {

  public LTypeInstanceIndexTest() {
    super("indexTest", "indexTest.mapping");
  }

  TypeInstanceIndex getIndex() {
    return _tm.getIndex(TypeInstanceIndex.class);
  }

  @Test
  public final void testGetAssociationTypes() {
    TypeInstanceIndex index = getIndex();
    Collection<Topic> result = index.getAssociationTypes();
    assertEquals(4, result.size());
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/knows"));
    assertTrue(result.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
    assertTrue(result.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/pokes"));
    assertTrue(result.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#predicate"));
    assertTrue(result.contains(t));
  }

  @Test
  public final void testGetAssociations() {
    TypeInstanceIndex index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/pokes"));
    Collection<Association> r = index.getAssociations(t);
    assertEquals(1, r.size());
    Association a = r.iterator().next();
    assertEquals(_tm, a.getTopicMap());
    assertEquals(_tm, a.getParent());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#pokeAssoTypeTopic"));
    assertEquals(t, a.getType());
    assertTrue(a.getScope().isEmpty());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/knows"));
    r = index.getAssociations(t);
    assertEquals(4, r.size());
    Iterator<Association> ai = r.iterator();
    Topic t_scope = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#assoScopingTopic"));
    while (ai.hasNext()) {
      Association association = ai.next();
      assertEquals(t, association.getType());
      assertEquals(_tm, association.getParent());
      assertEquals(_tm, association.getTopicMap());
      assertEquals(2, association.getRoles().size());
      assertEquals(1, association.getScope().size());
      assertEquals(t_scope, association.getScope().iterator().next());
    }
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/dips"));
    r = index.getAssociations(t);
    assertTrue(r.isEmpty());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/nix"));
    r = index.getAssociations(t);
    assertTrue(r.isEmpty());

  }

  @Test
  public final void testGetNameTypes() {
    TypeInstanceIndex index = getIndex();
    Collection<Topic> result = index.getNameTypes();
    assertEquals(3, result.size());
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/firstName"));
    assertTrue(result.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/additionalName"));
    assertTrue(result.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/familyName"));
    assertTrue(result.contains(t));
  }

  @Test
  public final void testGetNames() {
    TypeInstanceIndex index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/firstName"));
    Collection<Name> result = index.getNames(t);
    assertEquals(1, result.size());
    Name n = result.iterator().next();
    assertEquals("Lumperl", n.getValue());
    assertEquals(t, n.getType());
    assertEquals(_tm, n.getTopicMap());
    assertEquals(_tm, n.getParent().getParent());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi"));
    assertEquals(t, n.getParent());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#LumpisSI"));
    assertEquals(t, n.getParent());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/noName"));
    result = index.getNames(t);
    assertTrue(result.isEmpty());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/additionalName"));
    result = index.getNames(t);
    assertEquals(2, result.size());
    Iterator<Name> ri = result.iterator();
    HashSet<String> values = new HashSet<String>();
    while (ri.hasNext()) {
      Name name = ri.next();
      values.add(name.getValue());
      assertEquals(t, name.getType());
      assertEquals(name.getParent().getParent(), name.getType().getParent());
    }
    assertTrue(values.contains("Lump"));
    assertTrue(values.contains("Lupo"));
  }

  @Test
  public final void testGetOccurrenceTypes() {
    TypeInstanceIndex index = getIndex();
    Collection<Topic> result = index.getOccurrenceTypes();
    assertEquals(3, result.size());
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#countryOfBirth"));
    assertTrue(result.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#homepage"));
    assertTrue(result.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#age"));
    assertTrue(result.contains(t));
  }

  @Test
  public final void testGetOccurrences() {
    TypeInstanceIndex index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#homepage"));
    Collection<Occurrence> result = index.getOccurrences(t);
    assertEquals(1, result.size());
    Occurrence o = result.iterator().next();
    assertEquals("http://www.example.com/things#lumpisPage", o.getValue());
    assertEquals(new SesameLocator("http://www.example.com/things#lumpisPage"),
        o.locatorValue());
    assertEquals(new SesameLocator("http://www.w3.org/2001/XMLSchema#anyURI"),
        o.getDatatype());
    assertEquals(t, o.getType());
    assertEquals(_tm, o.getTopicMap());
    assertEquals(_tm, o.getParent().getParent());
  }

  @Test
  public final void testGetRoleTypes() {
    TypeInstanceIndex index = getIndex();
    Collection<Topic> resut = index.getRoleTypes();
    assertEquals(4, resut.size());
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Friend"));
    assertTrue(resut.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Person"));
    assertTrue(resut.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Starker"));
    assertTrue(resut.contains(t));
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Buddy"));
    assertTrue(resut.contains(t));
  }

  @Test
  public final void testGetRoles() {
    TypeInstanceIndex index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Person"));
    Collection<Role> roles = index.getRoles(t);
    Iterator<Role> rolesIterator = roles.iterator();
    assertEquals(4, roles.size());
    Set<Topic> ts = new HashSet<Topic>();
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Fritz")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#LumpisSI")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Hans")));
    while (rolesIterator.hasNext()) {
      Role role = rolesIterator.next();
      assertEquals(t, role.getType());
      ts.remove(role.getPlayer());
    }
    assertTrue(ts.isEmpty());
  }

  @Test
  public final void testGetTopicTypes() {
    TypeInstanceIndex index = getIndex();
    Collection<Topic> tTypes = index.getTopicTypes();
    assertEquals(3, tTypes.size());
    Iterator<Topic> iTypesIterator = tTypes.iterator();
    Set<Topic> ts = new HashSet<Topic>();
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Friend")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Thing")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Person")));
    while (iTypesIterator.hasNext()) {
      Topic topic = iTypesIterator.next();
      ts.remove(topic);
    }
    assertTrue(ts.isEmpty());
  }

  @Test
  public final void testGetTopicsTopic() {
    TypeInstanceIndex index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/Person"));
    Collection<Topic> tTypes = index.getTopics(t);
    Set<Topic> ts = new HashSet<Topic>();
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Fritz")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Hans")));
    Iterator<Topic> ti = tTypes.iterator();
    while (ti.hasNext()) {
      ts.remove(ti.next());
    }
    assertEquals(3, tTypes.size());
    assertTrue(ts.isEmpty());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Thing"));
    tTypes = index.getTopics(t);
    assertEquals(1, tTypes.size());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi"));
    assertEquals(t, tTypes.iterator().next());

  }

  @Test
  public final void testGetTopicsTopicArrayBoolean() {
    TypeInstanceIndex index = getIndex();
    Collection<Topic> tTypes = index.getTopics(new Topic[] { _tm
        .getTopicBySubjectIdentifier(new SesameLocator(
            "http://xmlns.com/foaf/0.1/Person")) }, false);
    Set<Topic> ts = new HashSet<Topic>();
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Fritz")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi")));
    ts.add(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Hans")));
    Iterator<Topic> ti = tTypes.iterator();
    while (ti.hasNext()) {
      ts.remove(ti.next());
    }
    assertEquals(3, tTypes.size());
    assertTrue(ts.isEmpty());
    assertTrue(tTypes.equals(index.getTopics(new Topic[] {
        _tm.getTopicBySubjectIdentifier(new SesameLocator(
            "http://xmlns.com/foaf/0.1/Person")),
        _tm.getTopicBySubjectIdentifier(new SesameLocator(
            "http://xmlns.com/foaf/0.1/Person")) }, false)));
    tTypes = index.getTopics(new Topic[] {
        _tm.getTopicBySubjectIdentifier(new SesameLocator(
            "http://xmlns.com/foaf/0.1/Person")),
        _tm.getTopicBySubjectIdentifier(new SesameLocator(
            "http://www.example.com/things#Thing")) }, true);
    assertEquals(1, tTypes.size());
    assertEquals(_tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi")), tTypes.iterator().next());
  }

}

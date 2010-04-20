/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;
import org.tmapi.core.Association;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;
import org.tmapi.index.ScopedIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.LTestCase;

public class LScopedIndexTest extends LTestCase {

  ScopedIndex index;

  public LScopedIndexTest() {
    super("indexTest", "indexTest.mapping");
  }

  ScopedIndex getIndex() {
    return _tm.getIndex(ScopedIndex.class);
  }

  @Test
  public final void testGetAssociationThemes() {
    index = getIndex();
    Collection<Topic> re = index.getAssociationThemes();
    assertEquals(1, re.size());
    Topic s = re.iterator().next();
    assertEquals(1, s.getSubjectIdentifiers().size());
    assertEquals("http://www.example.com/things#assoScopingTopic", s
        .getSubjectIdentifiers().iterator().next().toExternalForm());
  }

  @Test
  public final void testGetAssociationsTopic() {
    index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#assoScopingTopic"));
    Collection<Association> re = index.getAssociations(t);
    assertEquals(4, re.size());
    Iterator<Association> assoIterator = re.iterator();
    while (assoIterator.hasNext()) {
      Association association = assoIterator.next();
      assertEquals(_tm, association.getParent());
      assertEquals(1, association.getScope().size());
      assertEquals(t, association.getScope().iterator().next());
    }
  }

  @Test
  public final void testGetAssociationsTopicArrayBoolean() {
    index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#assoScopingTopic"));
    Collection<Association> re = index.getAssociations(new Topic[] { t }, true);
    assertTrue(re.equals(index.getAssociations(t)));
  }

  @Test
  public final void testGetNameThemes() {
    index = getIndex();
    Collection<Topic> re = index.getNameThemes();
    assertEquals(1, re.size());
    Topic s = re.iterator().next();
    assertEquals(1, s.getSubjectIdentifiers().size());
    assertEquals(new SesameLocator(
        "http://www.example.com/things#nameScopingTopic"), s
        .getSubjectIdentifiers().iterator().next());
  }

  @Test
  public final void testGetNamesTopic() {
    index = getIndex();
    Collection<Name> re = index.getNames(_tm
        .getTopicBySubjectIdentifier(new SesameLocator(
            "http://www.example.com/things#nameScopingTopic")));
    assertEquals(2, re.size());
    HashSet<String> valueSet = new HashSet<String>();
    valueSet.add("Lump");
    valueSet.add("Lupo");
    Iterator<Name> rei = re.iterator();
    while (rei.hasNext()) {
      assertTrue(valueSet.remove(rei.next().getValue()));
    }
    assertTrue(valueSet.isEmpty());
  }

  @Test
  public final void testGetNamesTopicArrayBoolean() {
    index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#nameScopingTopic"));
    Collection<Name> re = index.getNames(new Topic[] { t }, true);
    assertTrue(re.equals(index.getNames(t)));
  }

  @Test
  public final void testGetOccurrenceThemes() {
    index = getIndex();
    Collection<Topic> re = index.getOccurrenceThemes();
    assertEquals(1, re.size());
    Topic s = re.iterator().next();
    assertEquals(1, s.getSubjectIdentifiers().size());
    assertEquals("http://www.example.com/things#occurrenceScopingTopic", s
        .getSubjectIdentifiers().iterator().next().toExternalForm());
  }

  @Test
  public final void testGetOccurrencesTopic() {
    index = getIndex();
    Topic t = _tm
    .getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#occurrenceScopingTopic"));
    Collection<Occurrence> re = index.getOccurrences(t);
    assertEquals(1, re.size());
    Occurrence o = re.iterator().next();
    assertEquals(new SesameLocator("http://www.w3.org/2001/XMLSchema#string"), o.getDatatype());
    assertEquals("Hong Kong", o.getValue());
    assertEquals(1, o.getScope().size());
    assertEquals(t, o.getScope().iterator().next());
  }

  @Test
  public final void testGetOccurrencesTopicArrayBoolean() {
    index = getIndex();
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#occurrenceScopingTopic"));
    Collection<Occurrence> re = index.getOccurrences(new Topic[] { t }, true);
    assertTrue(re.equals(index.getOccurrences(t)));
  }

}

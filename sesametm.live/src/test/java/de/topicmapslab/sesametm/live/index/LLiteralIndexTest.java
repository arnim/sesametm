/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;
import org.tmapi.index.LiteralIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.LTestCase;

public class LLiteralIndexTest extends LTestCase {

  LiteralIndex index;

  public LLiteralIndexTest() {
    super("indexTest", "indexTest.mapping");
  }

  LiteralIndex getIndex() {
    return _tm.getIndex(LiteralIndex.class);
  }

  @Test
  public final void testGetNames() {
    index = getIndex();
    Collection<Name> re = index.getNames("Lump");
    assertEquals(1, re.size());
    Name n = re.iterator().next();
    assertEquals("Lump", n.getValue());
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi"));
    assertEquals(t, n.getParent());
    assertEquals(_tm, n.getParent().getParent());
    assertEquals(_tm, n.getParent().getTopicMap());
    assertEquals(_tm, n.getTopicMap());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://xmlns.com/foaf/0.1/additionalName"));
    assertEquals(t, n.getType());
    re = index.getNames("Lupo");
    assertEquals(1, re.size());
    Set<Topic> scopes = re.iterator().next().getScope();
    assertEquals(1, scopes.size());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#nameScopingTopic"));
    assertEquals(t, scopes.iterator().next());
  }

  @Test
  public final void testGetOccurrencesString() {
    index = getIndex();
    Collection<Occurrence> re = index.getOccurrences("Hong Kong");
    assertEquals(1, re.size());
    Occurrence o = re.iterator().next();
    assertEquals("Hong Kong", o.getValue());
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi"));
    assertEquals(t, o.getParent());
    assertEquals(_tm, o.getParent().getParent());
    assertEquals(_tm, o.getParent().getTopicMap());
    assertEquals(_tm, o.getTopicMap());
    assertEquals(new SesameLocator("http://www.w3.org/2001/XMLSchema#string"),
        o.getDatatype());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#countryOfBirth"));
    assertEquals(t, o.getType());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#occurrenceScopingTopic"));
    assertEquals(1, o.getScope().size());
    assertEquals(t, o.getScope().iterator().next());
  }

  @Test
  public final void testGetOccurrencesLocator() {
    index = getIndex();
    SesameLocator l = new SesameLocator(
        "http://www.example.com/things#lumpisPage");
    Collection<Occurrence> re = index.getOccurrences(l);
    assertEquals(1, re.size());
    Occurrence o = re.iterator().next();
    assertEquals(l, o.locatorValue());
    l = new SesameLocator("http://www.w3.org/2001/XMLSchema#anyURI");
    assertEquals(l, o.getDatatype());
  }

  @Test
  public final void testGetOccurrencesStringLocator() {
    index = getIndex();
    Collection<Occurrence> re = index.getOccurrences("2005-12-31",
        new SesameLocator("http://www.w3.org/2001/XMLSchema#date"));
    assertEquals(1, re.size());
    Occurrence o = re.iterator().next();
    assertEquals("2005-12-31", o.getValue());
    Topic t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#Lumpi"));
    assertEquals(t, o.getParent());
    assertEquals(_tm, o.getParent().getParent());
    assertEquals(_tm, o.getParent().getTopicMap());
    assertEquals(_tm, o.getTopicMap());
    assertEquals(new SesameLocator("http://www.w3.org/2001/XMLSchema#date"), o
        .getDatatype());
    t = _tm.getTopicBySubjectIdentifier(new SesameLocator(
        "http://www.example.com/things#age"));
    assertEquals(t, o.getType());
  }

}

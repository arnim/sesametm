/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.vocabulary.XMLSchema;
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Variant;
import org.tmapi.index.LiteralIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.cregan.core.CName;
import de.topicmapslab.sesametm.cregan.core.COccurrence;
import de.topicmapslab.sesametm.cregan.core.CTopicMap;
import de.topicmapslab.sesametm.cregan.core.CVariant;
import de.topicmapslab.sesametm.vocabularies.CREGAN;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CLiteralIndex extends CIndex implements LiteralIndex {

  public CLiteralIndex(CTopicMap tm) {
    super(tm);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getNames(java.lang.String)
   */
  @Override
  public Collection<Name> getNames(String value) {
    if (value == null)
      throw new IllegalArgumentException("getNames(null) is illegal");
    Collection<Name> result = new HashSet<Name>();
    Set<Resource> h1 = getNodes(value);
    Set<Resource> h2 = getNodes(CREGAN.NAME);
    h1.retainAll(h2);
    Iterator<Resource> h = h1.iterator();
    while (h.hasNext()) {
      result.add(new CName(sesameTopicMap, h.next()));
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getOccurrences(java.lang.String)
   */
  @Override
  public Collection<Occurrence> getOccurrences(String value) {
    if (value == null)
      throw new IllegalArgumentException("getOccurrences(null) is illegal");
    SesameLocator dt = new SesameLocator(XMLSchema.STRING.stringValue());
    Collection<Occurrence> result = new HashSet<Occurrence>();
    Set<Resource> h1 = getNodes(value);
    Set<Resource> h2 = getNodes(CREGAN.OCCURRENCE);
    h1.retainAll(h2);
    Iterator<Resource> h = h1.iterator();

    while (h.hasNext()) {
      COccurrence o = new COccurrence(sesameTopicMap, h.next());
      try {
        if (o.getDatatype().equals(dt))
          result.add(o);
      } catch (Exception e) {
      }

    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getOccurrences(org.tmapi.core.Locator)
   */
  @Override
  public Collection<Occurrence> getOccurrences(Locator value) {
    if (value == null)
      throw new IllegalArgumentException("getOccurrences(null) is illegal");
    Collection<Occurrence> result = new HashSet<Occurrence>();
    Iterator<Resource> h = getNodes(value).iterator();
    while (h.hasNext()) {
      result.add(new COccurrence(sesameTopicMap, h.next()));
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getOccurrences(java.lang.String,
   * org.tmapi.core.Locator)
   */
  @Override
  public Collection<Occurrence> getOccurrences(String value, Locator dt) {
    if (value == null)
      throw new IllegalArgumentException("getOccurrences(null, dt) is illegal");
    if (dt == null)
      throw new IllegalArgumentException(
          "getOccurrences(\"value\", null) is illegal");
    if (((SesameLocator) dt).getUri().equals(XMLSchema.ANYURI))
      return getOccurrences(new SesameLocator(value));
    Collection<Occurrence> result = new HashSet<Occurrence>();
    Iterator<Resource> h = getNodes(value).iterator();
    while (h.hasNext()) {
      COccurrence o = new COccurrence(sesameTopicMap, h.next());
      if (o.getDatatype().equals(dt))
        result.add(o);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getVariants(java.lang.String)
   */
  @Override
  public Collection<Variant> getVariants(String value) {
    if (value == null)
      throw new IllegalArgumentException("getVariants(null) is illegal");
    SesameLocator dt = new SesameLocator(XMLSchema.STRING.stringValue());
    Collection<Variant> result = new HashSet<Variant>();
    Set<Resource> h1 = getNodes(value);
    Set<Resource> h2 = getNodes(CREGAN.VARIANT);
    h1.retainAll(h2);
    Iterator<Resource> h = h1.iterator();
    while (h.hasNext()) {
      CVariant o = new CVariant(sesameTopicMap, h.next());
      try {
        if (o.getDatatype().equals(dt))
          result.add(o);
      } catch (Exception e) {
      }

    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getVariants(org.tmapi.core.Locator)
   */
  @Override
  public Collection<Variant> getVariants(Locator value) {
    if (value == null)
      throw new IllegalArgumentException("getVariants(null) is illegal");
    Collection<Variant> result = new HashSet<Variant>();
    Iterator<Resource> h = getNodes(value).iterator();
    while (h.hasNext()) {
      result.add(new CVariant(sesameTopicMap, h.next()));
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getVariants(java.lang.String,
   * org.tmapi.core.Locator)
   */
  @Override
  public Collection<Variant> getVariants(String value, Locator dt) {
    if (value == null)
      throw new IllegalArgumentException("getVariants(null, dt) is illegal");
    if (dt == null)
      throw new IllegalArgumentException(
          "getVariants(\"value\", null) is illegal");
    if (((SesameLocator) dt).getUri().equals(XMLSchema.ANYURI))
      return getVariants(new SesameLocator(value));
    Collection<Variant> result = new HashSet<Variant>();
    Iterator<Resource> h = getNodes(value).iterator();
    while (h.hasNext()) {
      CVariant o = new CVariant(sesameTopicMap, h.next());
      if (o.getDatatype().equals(dt))
        result.add(o);
    }
    return result;
  }

}

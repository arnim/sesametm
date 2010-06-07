/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryEvaluationException;
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Variant;
import org.tmapi.index.LiteralIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.core.LName;
import de.topicmapslab.sesametm.live.core.LOccurrence;
import de.topicmapslab.sesametm.live.core.LTopic;
import de.topicmapslab.sesametm.live.core.SparqlTopicMap;
import de.topicmapslab.sesametm.live.utils.LValue;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LLiteralIndex extends LIndex implements LiteralIndex {

  /**
   * @param tm
   */
  public LLiteralIndex(SparqlTopicMap tm) {
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
    HashSet<Name> names = new HashSet<Name>();
    Set<Resource> map = mapping.mapsTo(RTM.NAME);
    String q = "CONSTRUCT {?s ?p \"" + value + "\" } " + "WHERE {?s ?p  \""
        + value + "\"^^<http://www.w3.org/2001/XMLSchema#string> }";
    GraphQueryResult re = runSparqlConstructQueryOnRepository(q);
    try {
      while (re.hasNext()) {
        Statement statemet = re.next();
        if (map.contains(statemet.getPredicate()))
          names.add(new LName((URI) statemet.getSubject(), statemet
              .getPredicate(), new LValue(statemet.getObject()),
              new LTopic(new SesameLocator(statemet.getSubject()
                  .stringValue()), topicMap)));
      }
    } catch (QueryEvaluationException e) {
      e.printStackTrace();
    }
    return names;
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
    HashSet<Occurrence> occurences = new HashSet<Occurrence>();
    Set<Resource> map = mapping.mapsTo(RTM.OCCURENCE);
    String q = "CONSTRUCT {?s ?p \"" + value + "\" } " + "WHERE {?s ?p  \""
        + value + "\"^^<http://www.w3.org/2001/XMLSchema#string> }";
    GraphQueryResult re = runSparqlConstructQueryOnRepository(q);
    try {
      while (re.hasNext()) {
        Statement statemet = re.next();
        if (map.contains(statemet.getPredicate()))
          occurences.add(new LOccurrence((URI) statemet.getSubject(),
              statemet.getPredicate(), new LValue(new LiteralImpl(statemet
                  .getObject().stringValue(), con.getValueFactory().createURI(
                  "http://www.w3.org/2001/XMLSchema#string"))),
              new LTopic(new SesameLocator(statemet.getSubject()
                  .stringValue()), topicMap)));
      }
    } catch (QueryEvaluationException e) {
      e.printStackTrace();
    }
    return occurences;
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
    HashSet<Occurrence> occurences = new HashSet<Occurrence>();
    Set<Resource> map = mapping.mapsTo(RTM.OCCURENCE);
    String q = "CONSTRUCT {?s ?p <" + value + "> } " + "WHERE {?s ?p  <"
        + value + "> }";
    GraphQueryResult re = runSparqlConstructQueryOnRepository(q);
    try {
      while (re.hasNext()) {
        Statement statemet = re.next();
        if (map.contains(statemet.getPredicate())) {
          occurences.add(new LOccurrence((URI) statemet.getSubject(),
              statemet.getPredicate(), new LValue(statemet.getObject()),
              new LTopic(new SesameLocator(statemet.getSubject()
                  .stringValue()), topicMap)));
        }
      }
    } catch (QueryEvaluationException e) {
      e.printStackTrace();
    }
    return occurences;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getOccurrences(java.lang.String,
   * org.tmapi.core.Locator)
   */
  @Override
  public Collection<Occurrence> getOccurrences(String value, Locator datatype) {
    if (value == null)
      throw new IllegalArgumentException(
          "getOccurrences(null, datatype) is illegal");
    if (datatype == null)
      throw new IllegalArgumentException(
          "getOccurrences(value, null) is illegal");
    HashSet<Occurrence> occurences = new HashSet<Occurrence>();
    Set<Resource> map = mapping.mapsTo(RTM.OCCURENCE);
    String q = "CONSTRUCT {?s ?p \"" + value + "\" } " + "WHERE {?s ?p  \""
        + value + "\"^^<" + datatype + "> }";
    GraphQueryResult re = runSparqlConstructQueryOnRepository(q);
    try {
      while (re.hasNext()) {
        Statement statemet = re.next();
        if (map.contains(statemet.getPredicate()))
          occurences.add(new LOccurrence((URI) statemet.getSubject(),
              statemet.getPredicate(), new LValue(new LiteralImpl(statemet
                  .getObject().stringValue(), ((SesameLocator) datatype)
                  .getUri())), new LTopic(new SesameLocator(statemet
                  .getSubject().stringValue()), topicMap)));
      }
    } catch (QueryEvaluationException e) {
      e.printStackTrace();
    }
    return occurences;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getVariants(java.lang.String)
   */
  @Override
  public Collection<Variant> getVariants(String string) {
    return new HashSet<Variant>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getVariants(org.tmapi.core.Locator)
   */
  @Override
  public Collection<Variant> getVariants(Locator locator) {
    return new HashSet<Variant>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.LiteralIndex#getVariants(java.lang.String,
   * org.tmapi.core.Locator)
   */
  @Override
  public Collection<Variant> getVariants(String string, Locator locator) {
    return new HashSet<Variant>();
  }

}

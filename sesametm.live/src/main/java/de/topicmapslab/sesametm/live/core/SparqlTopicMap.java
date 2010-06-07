/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.index.Index;
import org.tmapi.index.LiteralIndex;
import org.tmapi.index.ScopedIndex;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.index.LLiteralIndex;
import de.topicmapslab.sesametm.live.index.LScopedIndex;
import de.topicmapslab.sesametm.live.index.LTypeInstanceIndex;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class SparqlTopicMap extends LConstruct implements TopicMap {

  private LTopicMapSystem tms;
  public Locator baseIRI;

  SparqlTopicMap(Locator l, LTopicMapSystem theTms) {
    super(l, theTms);
    tms = theTms;
    baseIRI = l;
  }

  public LTopicMapSystem topicMapgetTMS() {
    return tms;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#close()
   */
  @Override
  public void close() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createAssociation(org.tmapi.core.Topic,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Association createAssociation(Topic arg0, Topic... arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createAssociation(org.tmapi.core.Topic,
   * java.util.Collection)
   */
  @Override
  public Association createAssociation(Topic arg0, Collection<Topic> arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createLocator(java.lang.String)
   */
  @Override
  public Locator createLocator(String arg0) {
    return tms.createLocator(arg0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createTopic()
   */
  @Override
  public Topic createTopic() {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#createTopicByItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public Topic createTopicByItemIdentifier(Locator arg0) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#createTopicBySubjectIdentifier(org.tmapi.core.Locator
   * )
   */
  @Override
  public Topic createTopicBySubjectIdentifier(Locator arg0) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#createTopicBySubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public Topic createTopicBySubjectLocator(Locator arg0) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getAssociations()
   */
  @Override
  public Set<Association> getAssociations() {
    Set<Association> result = new HashSet<Association>();
    Iterator<Resource> assotioationPredicatesIterator, assotioationsSubjectsIterator;
    Iterator<Value> objectsIterator;
    URI associationPredicate, subjectPlayer, objectPlayer;
    Association a;
    assotioationPredicatesIterator = mapping.mapsTo(RTM.ASSOCIATION).iterator();
    while (assotioationPredicatesIterator.hasNext()) {
      associationPredicate = (URI) assotioationPredicatesIterator.next();
      assotioationsSubjectsIterator = getSubjects(associationPredicate, null)
          .iterator();
      while (assotioationsSubjectsIterator.hasNext()) {
        subjectPlayer = (URI) assotioationsSubjectsIterator.next();
        objectsIterator = getObjects(subjectPlayer, associationPredicate)
            .iterator();
        while (objectsIterator.hasNext()) {
          objectPlayer = (URI) objectsIterator.next();
          a = new LAssociation(associationPredicate, tm, subjectPlayer,
              objectPlayer);
          result.add(a);
        }
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getConstructById(java.lang.String)
   */
  @Override
  public Construct getConstructById(String id) {
    int indexBeginType = id
        .indexOf("?constructType=Association?associationType=") + 43;
    LConstruct resultat = null;
    Set<Value> list;
    try {
      int indexEndType = id.indexOf("?subjectPayer=");
      int indexEndsubjectPayer = id.indexOf("?objectPayer=");
      String assoType = id.substring(indexBeginType, indexEndType);
      String subjectPlayer = id.substring(indexEndType + 14,
          indexEndsubjectPayer);
      String objectPlayer = id.substring(indexEndsubjectPayer + 13);
      list = getObjects(createURI(subjectPlayer), createURI(assoType));
      if (list.contains(createURI(objectPlayer))) {
        resultat = new LAssociation(createURI(assoType), tm,
            createURI(subjectPlayer), createURI(objectPlayer));
      }
    } catch (StringIndexOutOfBoundsException e) {
      try {
        SesameLocator l = new SesameLocator(id);
        resultat = (LConstruct) getTopicBySubjectIdentifier(l);
      } catch (Exception e2) {
      }
    }

    return resultat;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#getConstructByItemIdentifier(org.tmapi.core.Locator
   * )
   */
  @Override
  public Construct getConstructByItemIdentifier(Locator arg0) {
    // TODO impl?
    return getTopicBySubjectIdentifier(arg0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getIndex(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <I extends Index> I getIndex(Class<I> arg0) {
    if (arg0 == TypeInstanceIndex.class)
      return (I) new LTypeInstanceIndex(this);
    if (arg0 == LiteralIndex.class)
      return (I) new LLiteralIndex(this);
    if (arg0 == ScopedIndex.class)
      return (I) new LScopedIndex(this);
    throw new UnsupportedOperationException("Exception unknown index "
        + arg0.getName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getParent()
   */
  @Override
  public Construct getParent() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#getTopicBySubjectIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public Topic getTopicBySubjectIdentifier(Locator l) {
    SesameLocator locator = (SesameLocator) l;
    URI locatorUri = locator.getUri();
    URI uri;
    Set<Value> potential = new HashSet<Value>();
    Set<Resource> assotioationPredicates = mapping.mapsTo(RTM.ASSOCIATION);
    Iterator<Resource> iterator = mapping.mapsTo(RTM.INSTANCEOF).iterator();
    while (iterator.hasNext()) {
      uri = (URI) iterator.next();
      potential.addAll(getObjects(locatorUri, uri));
      potential.addAll(getSubjects(uri, locatorUri));
    }
    if (!potential.isEmpty()) {
      return new LTopic(locator, tm);
    }
    if (assotioationPredicates != null
        && assotioationPredicates.contains(locatorUri)) {
      return new LTopic(locator, tm);
    }
    Topic t = getTopicByExplicite(l, RTM.SUBJECTIDENTIFIER);
    if (t != null)
      return t;
    // TODO performant?
    Set<LTopic> sparqlTopics = generateTopicsFromResources(mapping
        .getObjects(null, RTM.INSCOPE));
    sparqlTopics.addAll(generateTopicsFromResources(mapping.getSubjects(
        RTM.MAPSTO, null)));
    sparqlTopics.addAll(generateTopicsFromResources(mapping.getObjects(null,
        RTM.TYPE)));
    sparqlTopics.addAll(generateTopicsFromResources(mapping.getObjects(null,
        RTM.SUBJECTROLE)));
    sparqlTopics.addAll(generateTopicsFromResources(mapping.getObjects(null,
        RTM.OBJECTROLE)));
    Iterator<LTopic> ti = sparqlTopics.iterator();
    while (ti.hasNext()) {
      LTopic sparqlTopic = ti.next();
      if (sparqlTopic.getReference().equals(l)) {
        return sparqlTopic;
      }
    }
    return null;
  }

  /**
   * 
   * @param l
   * @param criteria
   * @return
   */
  private Topic getTopicByExplicite(Locator l, URI criteria) {
    SesameLocator locator = (SesameLocator) l;
    URI locatorUri = locator.getUri();
    URI subjectLocatorPredicate;
    SesameLocator subjetIdentifier;
    Set<Resource> subjects;
    Set<Resource> subjectLocatorPredicates = mapping.mapsTo(criteria);
    Iterator<Resource> subjectLocatorPredicatesIterator = subjectLocatorPredicates
        .iterator();
    while (subjectLocatorPredicatesIterator.hasNext()) {
      subjectLocatorPredicate = (URI) subjectLocatorPredicatesIterator.next();
      subjects = getSubjects(subjectLocatorPredicate, locatorUri);
      if (!subjects.isEmpty()) {
        subjetIdentifier = new SesameLocator(subjects.iterator().next()
            .stringValue());
        return new LTopic(subjetIdentifier, this);
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#getTopicBySubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public Topic getTopicBySubjectLocator(Locator l) {
    return getTopicByExplicite(l, RTM.SUBJECLOCATOR);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getTopics()
   */
  @Override
  public Set<Topic> getTopics() {
    URI instOf;
    Iterator<Resource> subjects;
    Iterator<Value> objects;
    Resource resource;
    LTopic t;
    Set<Topic> result = new HashSet<Topic>();
    Iterator<Resource> instacesOfs = mapping.mapsTo(RTM.INSTANCEOF).iterator();
    while (instacesOfs.hasNext()) {
      instOf = (URI) instacesOfs.next();
      subjects = getSubjects(instOf, null).iterator();
      while (subjects.hasNext()) {
        resource = subjects.next();
        t = new LTopic(new SesameLocator(resource.stringValue()), tm);
        result.add(t);
      }
      objects = getObjects(null, instOf).iterator();
      while (objects.hasNext()) {
        resource = (Resource) objects.next();
        t = new LTopic(new SesameLocator(resource.stringValue()), tm);
        result.add(t);
      }
    }

    result.addAll(generateTopicsFromResources(mapping.getObjects(null,
        RTM.INSCOPE)));
    result.addAll(generateTopicsFromResources(mapping.getObjects(null,
        RTM.SUBJECTROLE)));
    result.addAll(generateTopicsFromResources(mapping.getObjects(null,
        RTM.OBJECTROLE)));
    result.addAll(generateTopicsFromResources(mapping
        .getObjects(null, RTM.TYPE)));
    result.addAll(generateTopicsFromResources(mapping.mapsTo(RTM.ASSOCIATION)));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#mergeIn(org.tmapi.core.TopicMap)
   */
  @Override
  public void mergeIn(TopicMap arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Reifiable#getReifier()
   */
  @Override
  public Topic getReifier() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Reifiable#setReifier(org.tmapi.core.Topic)
   */
  @Override
  public void setReifier(Topic arg0) throws ModelConstraintException {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#addItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void addItemIdentifier(Locator arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getId()
   */
  @Override
  public String getId() {
    return baseIRI.getReference();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getItemIdentifiers()
   */
  @Override
  public Set<Locator> getItemIdentifiers() {
    Set<Locator> result = new HashSet<Locator>();
    result.add(baseIRI);
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getTopicMap()
   */
  @Override
  public TopicMap getTopicMap() {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#remove()
   */
  @Override
  public void remove() {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#removeItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void removeItemIdentifier(Locator arg0) {
    notSupported();
  }

  @Override
  public Locator getLocator() {
    return baseIRI;
  }

}

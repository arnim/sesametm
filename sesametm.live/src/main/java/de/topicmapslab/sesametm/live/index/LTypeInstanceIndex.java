/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryEvaluationException;
import org.tmapi.core.Association;
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.core.LAssociation;
import de.topicmapslab.sesametm.live.core.LName;
import de.topicmapslab.sesametm.live.core.LOccurrence;
import de.topicmapslab.sesametm.live.core.LTopic;
import de.topicmapslab.sesametm.live.core.SparqlTopicMap;
import de.topicmapslab.sesametm.live.utils.LValue;
import de.topicmapslab.sesametm.utils.LiveException;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LTypeInstanceIndex extends LIndex implements
    TypeInstanceIndex {

  /**
   * @param sparqlTopicMap
   * 
   */
  public LTypeInstanceIndex(SparqlTopicMap sesameTopicMap) {
    super(sesameTopicMap);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getAssociationTypes()
   */
  public Collection<Topic> getAssociationTypes() {
    HashSet<Topic> topics = new HashSet<Topic>();
    URI uri;
    Set<Resource> map = mapping.mapsTo(RTM.ASSOCIATION);
    map.addAll(mapping.mapsTo(RTM.INSTANCEOF));
    Iterator<Resource> mapi = map.iterator();
    while (mapi.hasNext()) {
      uri = (URI) mapi.next();
      if (repositoryHasStatement(null, uri, null))
        topics.add(new LTopic(new SesameLocator(uri.stringValue()),
            topicMap));
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.index.TypeInstanceIndex#getAssociations(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Association> getAssociations(Topic type) {
    Set<Association> associations = new HashSet<Association>();
    Association a;
    URI uri;
    String q;
    Statement statement;
    try {
      uri = ((SesameLocator) type.getSubjectIdentifiers().iterator().next())
          .getUri();
      if (mapping.mapsTo(RTM.ASSOCIATION).contains((uri))) {
        q = "CONSTRUCT {?s <" + uri.stringValue() + "> ?o } " + "WHERE {?s <"
            + uri.stringValue() + "> ?o }";
        GraphQueryResult re = runSparqlConstructQueryOnRepository(q);
        while (re.hasNext()) {
          statement = re.next();
          a = new LAssociation(uri, topicMap,
              (URI) statement.getSubject(), (URI) statement.getObject());
          associations.add(a);
        }
      }
    } catch (NullPointerException e) {
    } catch (QueryEvaluationException e) {
    }
    return associations;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getNameTypes()
   */
  @Override
  public Collection<Topic> getNameTypes() {
    HashSet<Topic> topics = new HashSet<Topic>();
    URI uri;
    Iterator<Resource> mapi = mapping.mapsTo(RTM.NAME).iterator();
    while (mapi.hasNext()) {
      uri = (URI) mapi.next();
      if (repositoryHasStatement(null, uri, null))
        topics.add(new LTopic(new SesameLocator(uri.stringValue()),
            topicMap));
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getNames(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Name> getNames(Topic type) {
    Set<Name> names = new HashSet<Name>();
    Topic parentTopic;
    Statement statement;
    LValue value;
    try {
      URI uri = ((SesameLocator) type.getSubjectIdentifiers().iterator().next())
          .getUri();
      if (mapping.mapsTo(RTM.NAME).contains((uri))) {
        String q = "CONSTRUCT {?s <" + uri.stringValue() + "> ?o } "
            + "WHERE {?s <" + uri.stringValue() + "> ?o }";
        GraphQueryResult re = runSparqlConstructQueryOnRepository(q);
        while (re.hasNext()) {
          statement = re.next();
          value = new LValue(statement.getObject());
          parentTopic = topicMap.getTopicBySubjectIdentifier(new SesameLocator(
              statement.getSubject().stringValue()));
          if (value.getType().toString().equals(
              "http://www.w3.org/2001/XMLSchema#string"))
            names
                .add(new LName((URI) statement.getSubject(),
                    ((SesameLocator) type.getSubjectIdentifiers().iterator()
                        .next()).getUri(), value, (LTopic) parentTopic));
        }
      }
    } catch (NullPointerException e) {
    } catch (QueryEvaluationException e) {
    }
    return names;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getOccurrenceTypes()
   */
  @Override
  public Collection<Topic> getOccurrenceTypes() {
    HashSet<Topic> topics = new HashSet<Topic>();
    URI uri;
    Iterator<Resource> mapi = mapping.mapsTo(RTM.OCCURENCE).iterator();
    while (mapi.hasNext()) {
      uri = (URI) mapi.next();
      if (repositoryHasStatement(null, uri, null))
        topics.add(new LTopic(new SesameLocator(uri.stringValue()),
            topicMap));
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getOccurrences(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Occurrence> getOccurrences(Topic type) {
    Set<Occurrence> occurrences = new HashSet<Occurrence>();
    Topic parentTopic;
    Statement statement;
    LValue value;
    try {
      URI uri = ((SesameLocator) type.getSubjectIdentifiers().iterator().next())
          .getUri();
      if (mapping.mapsTo(RTM.OCCURENCE).contains((uri))) {
        String q = "CONSTRUCT {?s <" + uri.stringValue() + "> ?o } "
            + "WHERE {?s <" + uri.stringValue() + "> ?o }";
        GraphQueryResult re = runSparqlConstructQueryOnRepository(q);
        while (re.hasNext()) {
          statement = re.next();
          value = new LValue(statement.getObject());
          parentTopic = topicMap.getTopicBySubjectIdentifier(new SesameLocator(
              statement.getSubject().stringValue()));
          occurrences.add(new LOccurrence((URI) statement.getSubject(),
              ((SesameLocator) type.getSubjectIdentifiers().iterator().next())
                  .getUri(), value, (LTopic) parentTopic));
        }
      }
    } catch (NullPointerException e) {
    } catch (QueryEvaluationException e) {
    }
    return occurrences;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getRoleTypes()
   */
  @Override
  public Collection<Topic> getRoleTypes() {
    HashSet<Topic> topics = new HashSet<Topic>();
    URI uri;
    Set<Resource> map = mapping.mapsTo(RTM.ASSOCIATION);
    // map.addAll(mapping.mapsTo(RTM.INSTANCEOF)); maybe
    Iterator<Resource> mapi = map.iterator();
    while (mapi.hasNext()) {
      uri = (URI) mapi.next();
      if (repositoryHasStatement(null, uri, null)) {
        topics.add(new LTopic(new SesameLocator(mapping.objectRole(uri)
            .stringValue()), topicMap));
        topics.add(new LTopic(new SesameLocator(mapping.subjectRole(uri)
            .stringValue()), topicMap));
      }
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getRoles(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Role> getRoles(Topic type) {
    Collection<Association> associations = new HashSet<Association>();
    Role[] roleTuple;
    HashSet<Role> roles = new HashSet<Role>();
    Iterator<URI> assoTypes = getAssotypesUriByRoleTypes(type, RTM.SUBJECTROLE)
        .iterator();
    while (assoTypes.hasNext()) {
      associations = getAssociations(new LTopic(new SesameLocator(
          assoTypes.next().stringValue()), topicMap));
    }
    assoTypes = getAssotypesUriByRoleTypes(type, RTM.OBJECTROLE).iterator();
    while (assoTypes.hasNext()) {
      associations.addAll(getAssociations(new LTopic(new SesameLocator(
          assoTypes.next().stringValue()), topicMap)));
    }
    Iterator<Association> associationsIterator = associations.iterator();
    while (associationsIterator.hasNext()) {
      roleTuple = associationsIterator.next().getRoles().toArray(new Role[0]);
      if (roleTuple[0].getType().equals(type))
        roles.add(roleTuple[0]);
      if (roleTuple[1].getType().equals(type))
        roles.add(roleTuple[1]);
    }
    return roles;
  }

  /*
   * 
   */
  private Set<URI> getAssotypesUriByRoleTypes(Topic type, URI subjectOrObject) {
    HashSet<URI> result = new HashSet<URI>();
    SesameLocator typesi = (SesameLocator) type.getSubjectIdentifiers()
        .iterator().next();
    String qString = "CONSTRUCT {?s <" + subjectOrObject.stringValue() + "> <"
        + typesi.getUri() + "> } " + "WHERE {?s <"
        + subjectOrObject.stringValue() + "> <" + typesi.getUri() + "> }";
    GraphQueryResult re = runSparqlConstructQueryOnVocabulary(qString);
    try {
      while (re.hasNext()) {
        result.add((URI) re.next().getSubject());
      }
    } catch (QueryEvaluationException e) {
    } catch (ClassCastException cce) {
      throw new LiveException(cce);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getTopicTypes()
   */
  @Override
  public Collection<Topic> getTopicTypes() {
    HashSet<Topic> topics = new HashSet<Topic>();
    URI uri, tTypeUri;
    String qString;
    Set<Resource> map = mapping.mapsTo(RTM.INSTANCEOF);
    Iterator<Resource> mapi = map.iterator();
    while (mapi.hasNext()) {
      uri = (URI) mapi.next();
      qString = "CONSTRUCT {?s <" + uri.stringValue() + "> ?t } "
          + "WHERE {?s <" + uri.stringValue() + "> ?t }";
      GraphQueryResult re = runSparqlConstructQueryOnRepository(qString);
      try {
        while (re.hasNext()) {
          tTypeUri = (URI) re.next().getObject();
          topics.add(new LTopic(new SesameLocator(tTypeUri.stringValue()),
              topicMap));
        }
      } catch (QueryEvaluationException e) {
      } catch (ClassCastException cce) {
        throw new LiveException(cce);
      }
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getTopics(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Topic> getTopics(Topic type) {
    HashSet<Topic> topics = new HashSet<Topic>();
    URI uri, instanceUri, typeUri;
    String qString;
    Statement statement;
    Set<Resource> map = mapping.mapsTo(RTM.INSTANCEOF);
    Iterator<Resource> mapi = map.iterator();
    while (mapi.hasNext()) {
      uri = (URI) mapi.next();
      qString = "CONSTRUCT {?s <" + uri.stringValue() + "> ?t } "
          + "WHERE {?s <" + uri.stringValue() + "> ?t }";
      GraphQueryResult re = runSparqlConstructQueryOnRepository(qString);
      try {
        while (re.hasNext()) {
          statement = re.next();
          instanceUri = (URI) statement.getSubject();
          typeUri = (URI) statement.getObject();
          Set<Locator> typeSIs = type.getSubjectIdentifiers();
          if (typeSIs.contains(new SesameLocator(typeUri.stringValue())))
            topics.add(new LTopic(new SesameLocator(instanceUri
                .stringValue()), topicMap));
        }
      } catch (QueryEvaluationException e) {
      } catch (ClassCastException cce) {
        throw new LiveException(cce);
      }
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getTopics(org.tmapi.core.Topic[],
   * boolean)
   */
  @Override
  public Collection<Topic> getTopics(Topic[] types, boolean matchAll) {
    if (types.length == 1)
      return getTopics(types[0]);
    HashSet<Topic> topics = new HashSet<Topic>();
    if (!matchAll)
      for (Topic type : types)
        topics.addAll(getTopics(type));
    else {
      if (types.length > 1) {
        topics.addAll(getTopics(types[0]));
        for (Topic type : types)
          topics.retainAll(getTopics(type));
      }
    }
    return topics;
  }

}

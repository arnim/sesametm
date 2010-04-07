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
import org.tmapi.core.Association;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.sesametm.cregan.core.CAssociation;
import de.topicmapslab.sesametm.cregan.core.CName;
import de.topicmapslab.sesametm.cregan.core.COccurrence;
import de.topicmapslab.sesametm.cregan.core.CRole;
import de.topicmapslab.sesametm.cregan.core.CTopicMap;
import de.topicmapslab.sesametm.vocabularies.CREGAN;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CTypeInstanceIndex extends CIndex implements
    TypeInstanceIndex {

  public CTypeInstanceIndex(CTopicMap sesameTopicMap) {
    super(sesameTopicMap);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getAssociationTypes()
   */
  @Override
  public Collection<Topic> getAssociationTypes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.ASSOCIATION).iterator();
    while (h.hasNext()) {
      result.add(new CAssociation(sesameTopicMap, h.next()).getType());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.index.TypeInstanceIndex#getAssociations(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Association> getAssociations(Topic type) {
    Collection<Association> result = new HashSet<Association>();
    Set<Resource> h1 = getNodes(type);
    h1.retainAll(getNodes(CREGAN.ASSOCIATION));
    Iterator<Resource> h = h1.iterator();
    while (h.hasNext()) {
      result.add(new CAssociation(sesameTopicMap, h.next()));
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getNameTypes()
   */
  @Override
  public Collection<Topic> getNameTypes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.NAME).iterator();
    while (h.hasNext()) {
      result.add(new CName(sesameTopicMap, h.next()).getType());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getNames(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Name> getNames(Topic type) {
    Collection<Name> result = new HashSet<Name>();
    Set<Resource> h1 = getNodes(type);
    h1.retainAll(getNodes(CREGAN.NAME));
    Iterator<Resource> h = h1.iterator();
    while (h.hasNext()) {
      result.add(new CName(sesameTopicMap, h.next()));
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getOccurrenceTypes()
   */
  @Override
  public Collection<Topic> getOccurrenceTypes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.OCCURRENCE).iterator();
    while (h.hasNext()) {
      result.add(new COccurrence(sesameTopicMap, h.next()).getType());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getOccurrences(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Occurrence> getOccurrences(Topic type) {
    Collection<Occurrence> result = new HashSet<Occurrence>();
    Set<Resource> h1 = getNodes(type);
    h1.retainAll(getNodes(CREGAN.OCCURRENCE));
    Iterator<Resource> h = h1.iterator();
    while (h.hasNext()) {
      result.add(new COccurrence(sesameTopicMap, h.next()));
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getRoleTypes()
   */
  @Override
  public Collection<Topic> getRoleTypes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.ROLE).iterator();
    while (h.hasNext()) {
      result.add(new CRole(sesameTopicMap, h.next()).getType());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getRoles(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Role> getRoles(Topic type) {
    Collection<Role> result = new HashSet<Role>();
    Set<Resource> h1 = getNodes(type);
    h1.retainAll(getNodes(CREGAN.ROLE));
    Iterator<Resource> h = h1.iterator();
    while (h.hasNext()) {
      result.add(new CRole(sesameTopicMap, h.next()));
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
    Iterator<Topic> allTopics = sesameTopicMap.getTopics().iterator();
    Collection<Topic> topicTypes = new HashSet<Topic>();
    while (allTopics.hasNext()) {
      Topic topic = allTopics.next();
      topicTypes.addAll(topic.getTypes());
    }
    return topicTypes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.TypeInstanceIndex#getTopics(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Topic> getTopics(Topic type) {
    Iterator<Topic> allTopics = sesameTopicMap.getTopics().iterator();
    Collection<Topic> result = new HashSet<Topic>();
    while (allTopics.hasNext()) {
      Topic topic = allTopics.next();
      if (type == null) {
        if (topic.getTypes().isEmpty())
          result.add(topic);
      } else {
        if (topic.getTypes().contains(type))
          result.add(topic);
      }
    }
    return result;
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
    Iterator<Topic> allTopicsIterator = sesameTopicMap.getTopics().iterator();
    Collection<Topic> result = new HashSet<Topic>();
    while (allTopicsIterator.hasNext()) {
      Topic topic = allTopicsIterator.next();
      if (matchAll) {
        result.addAll(getTopics(types[0]));
        for (int i = 1; i < types.length; i++)
          result.retainAll(getTopics(types[i]));
      } else {
        for (Topic type : types)
          if (topic.getTypes().contains(type))
            result.add(topic);
      }
    }
    return result;
  }

}

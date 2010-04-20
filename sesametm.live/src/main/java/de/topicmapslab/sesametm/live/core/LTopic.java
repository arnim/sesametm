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
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Reifiable;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.utils.LValue;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * 
 */
public class LTopic extends LConstruct implements Topic {

  public class MaSet {

  }


  public LTopic(Locator l, SparqlTopicMap theTm) {
    super(l, theTm);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#addSubjectIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void addSubjectIdentifier(Locator arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#addSubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public void addSubjectLocator(Locator arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#addType(org.tmapi.core.Topic)
   */
  @Override
  public void addType(Topic arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(java.lang.String,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Name createName(String arg0, Topic... arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(java.lang.String,
   * java.util.Collection)
   */
  @Override
  public Name createName(String arg0, Collection<Topic> arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Topic[])
   */
  @Override
  public Name createName(Topic arg0, String arg1, Topic... arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(org.tmapi.core.Topic,
   * java.lang.String, java.util.Collection)
   */
  @Override
  public Name createName(Topic arg0, String arg1, Collection<Topic> arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Topic[])
   */
  @Override
  public Occurrence createOccurrence(Topic arg0, String arg1, Topic... arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, java.util.Collection)
   */
  @Override
  public Occurrence createOccurrence(Topic arg0, String arg1,
      Collection<Topic> arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * org.tmapi.core.Locator, org.tmapi.core.Topic[])
   */
  @Override
  public Occurrence createOccurrence(Topic arg0, Locator arg1, Topic... arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * org.tmapi.core.Locator, java.util.Collection)
   */
  @Override
  public Occurrence createOccurrence(Topic arg0, Locator arg1,
      Collection<Topic> arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Locator, org.tmapi.core.Topic[])
   */
  @Override
  public Occurrence createOccurrence(Topic arg0, String arg1, Locator arg2,
      Topic... arg3) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Locator, java.util.Collection)
   */
  @Override
  public Occurrence createOccurrence(Topic arg0, String arg1, Locator arg2,
      Collection<Topic> arg3) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getNames()
   */
  @Override
  public Set<Name> getNames() {
    Set<Name> names = new HashSet<Name>();
    URI namePredicate;
    LValue value;
    Set<Resource> namePredicates = mapping.mapsTo(RTM.BASENAME);
      Iterator<Resource> namePredicatesIterator = namePredicates.iterator();
      while (namePredicatesIterator.hasNext()) {
        namePredicate = (URI) namePredicatesIterator.next();
        Iterator<LValue> nameValuesIterator = getValues(reference, namePredicate) 
            .iterator();
        while (nameValuesIterator.hasNext()) {
          value = nameValuesIterator.next();
          if (value.getType().toString().equals(
            "http://www.w3.org/2001/XMLSchema#string"))
            names.add(new LName(reference, namePredicate, value, this));
        }
      }
    return names;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getNames(org.tmapi.core.Topic)
   */
  @Override
  public Set<Name> getNames(Topic type) {
    URI typeTopicURI = ((LTopic) type).reference;
    Iterator<Resource> typeMappingIterator = mapping.getSubjects(RTM.TYPE,
        typeTopicURI).iterator();
    if (typeMappingIterator.hasNext())
      typeTopicURI = (URI) typeMappingIterator.next();
    Set<Name> names = new HashSet<Name>();
    Name n;
    Set<Resource> namePredicates = mapping.mapsTo(RTM.BASENAME);
      Iterator<Resource> namePredicatesIterator = namePredicates.iterator();
      while (namePredicatesIterator.hasNext()) {
        URI occrencePredicate = (URI) namePredicatesIterator.next();
        boolean isOfType = false;
        try {
          isOfType = typeTopicURI.toString().equals(
              occrencePredicate.stringValue());
        } catch (Exception e) {
        }
        if (isOfType) {
          Iterator<Resource> subjects = getSubjects(occrencePredicate, null)
              .iterator();
          while (subjects.hasNext()) {
            URI subject = (URI) subjects.next();
            Iterator<LValue> objects = getValues(subject, occrencePredicate)
                .iterator();
            while (objects.hasNext()) {
              LValue myValue = objects.next();
              if (myValue.getType().toString().equals(
                  "http://www.w3.org/2001/XMLSchema#string")) {
                n = new LName(subject, occrencePredicate, myValue, this);
                names.add(n);
              }

            }
          }

        }
      }
    return names;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getOccurrences()
   */
  @Override
  public Set<Occurrence> getOccurrences() {
    Set<Occurrence> ocurenes = new HashSet<Occurrence>();
    URI namePredicate;
    Set<Resource> namePredicates = mapping.mapsTo(RTM.OCCURENCE);
      Iterator<Resource> occurrencePredicatesIterator = namePredicates.iterator();
      while (occurrencePredicatesIterator.hasNext()) {
        namePredicate = (URI) occurrencePredicatesIterator.next();
        Iterator<LValue> occurrencesValuesIterator = getValues(reference, namePredicate) 
            .iterator();
        while (occurrencesValuesIterator.hasNext()) {
            ocurenes.add(new LOccurrence(reference, namePredicate, occurrencesValuesIterator.next(), this));
        }
      }
    return ocurenes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getOccurrences(org.tmapi.core.Topic)
   */
  @Override
  public Set<Occurrence> getOccurrences(Topic type) {
    URI typeTopicURI = ((LTopic) type).reference;
    Iterator<Resource> typeMappingIterator = mapping.getSubjects(RTM.TYPE,
        typeTopicURI).iterator();
    if (typeMappingIterator.hasNext())
      typeTopicURI = (URI) typeMappingIterator.next();
    Set<Occurrence> occurrences = new HashSet<Occurrence>();
    Occurrence o;
    Set<Resource> occurencePredicates = mapping.mapsTo(RTM.OCCURENCE);

    if (occurencePredicates != null) {
      Iterator<Resource> occurencePredicatesIterator = occurencePredicates
          .iterator();
      while (occurencePredicatesIterator.hasNext()) {
        URI occrencePredicate = (URI) occurencePredicatesIterator.next();
        boolean isOfType = false;
        try {
          isOfType = typeTopicURI.toString().equals(
              occrencePredicate.stringValue());
        } catch (Exception e) {
        }
        if (isOfType) {
          Iterator<Resource> subjects = getSubjects(occrencePredicate, null)
              .iterator();
          while (subjects.hasNext()) {
            URI subject = (URI) subjects.next();
            Iterator<LValue> objects = getValues(subject, occrencePredicate)
                .iterator();
            while (objects.hasNext()) {
              LValue myValue = objects.next();
              o = new LOccurrence(subject, occrencePredicate, myValue,
                  this);
              occurrences.add(o);
            }
          }
        }
      }
    }
    return occurrences;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getReified()
   */
  @Override
  public Reifiable getReified() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getRolesPlayed()
   */
  @Override
  public Set<Role> getRolesPlayed() {
    Set<Role> roles = new HashSet<Role>();
    Iterator<Resource> assotioationPredicatesIterator;
    URI associationPredicate, subjectRoleType, obectRoleType, object, subject;
    LAssociation association;
    Set<Value> objects;
    Iterator<Value> objectsIterator;
    Iterator<Resource> subjectsIterator;
    Role role;
    Set<Resource> subjects;
    assotioationPredicatesIterator = mapping.mapsTo(RTM.ASSOCIATION).iterator();
    while (assotioationPredicatesIterator.hasNext()) {
      associationPredicate = (URI) assotioationPredicatesIterator.next();
      subjectRoleType = (URI) mapping.subjectRole(associationPredicate);
      obectRoleType = (URI) mapping.objectRole(associationPredicate);
      objects = getObjects(reference, associationPredicate);
      if (!objects.isEmpty()) {
        objectsIterator = objects.iterator();
        while (objectsIterator.hasNext()) {
          object = (URI) objectsIterator.next();
          association = new LAssociation(associationPredicate, tm,
              reference, object);
          role = new LRole(obectRoleType, association, reference);
          roles.add(role);
        }
      }
      subjects = getSubjects(associationPredicate, reference);
      if (!subjects.isEmpty()) {
        subjectsIterator = subjects.iterator();
        while (subjectsIterator.hasNext()) {
          subject = (URI) subjectsIterator.next();
          association = new LAssociation(associationPredicate, tm,
              reference , subject);
          role = new LRole(subjectRoleType, association, reference);
          roles.add(role);
        }
      }
    }
    return roles;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getRolesPlayed(org.tmapi.core.Topic)
   */
  @Override
  public Set<Role> getRolesPlayed(Topic theType) {
    LTopic type = (LTopic) theType;
    URI typeURI = type.getReference().getUri();
    Set<Role> roles = new HashSet<Role>();
    Iterator<Resource> assotioationPredicatesIterator;
    URI associationPredicate, subjectRoleType, obectRoleType, object, subject;
    LAssociation association;
    Set<Value> objects;
    Iterator<Value> objectsIterator;
    Iterator<Resource> subjectsIterator;
    Role role;
    Set<Resource> subjects;
    assotioationPredicatesIterator = mapping.mapsTo(RTM.ASSOCIATION).iterator();
    while (assotioationPredicatesIterator.hasNext()) {
      associationPredicate = (URI) assotioationPredicatesIterator.next();
      subjectRoleType = (URI) mapping.subjectRole(associationPredicate);
      obectRoleType = (URI) mapping.objectRole(associationPredicate);
      objects = getObjects(reference, associationPredicate);
      if (!objects.isEmpty() && obectRoleType.equals(typeURI)) {
        objectsIterator = objects.iterator();
        while (objectsIterator.hasNext()) {
          object = (URI) objectsIterator.next();
          association = new LAssociation(associationPredicate, tm,
              reference, object);
          role = new LRole(obectRoleType, association, reference);
          roles.add(role);
        }
      }
      subjects = getSubjects(associationPredicate, reference);
      if (!subjects.isEmpty() && subjectRoleType.equals(typeURI)) {
        subjectsIterator = subjects.iterator();
        while (subjectsIterator.hasNext()) {
          subject = (URI) subjectsIterator.next();
          association = new LAssociation(associationPredicate, tm,
              reference , subject);
          role = new LRole(subjectRoleType, association, reference);
          roles.add(role);
        }
      }
    }
    return roles;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getRolesPlayed(org.tmapi.core.Topic,
   * org.tmapi.core.Topic)
   */
  @Override
  public Set<Role> getRolesPlayed(Topic arg0, Topic arg1) {
    return getRolesPlayed(arg0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getSubjectIdentifiers()
   */
  @Override
  public Set<Locator> getSubjectIdentifiers() {
    Set<Locator> locators = getLocatorsExpliceite(RTM.SUBJECTIDENTIFIER);
    locators.add(getReference());
    return locators;
  }

  /**
   * 
   * @return the {@link Resource} as {@link SesameLocator}
   */
  public SesameLocator getReference() {
    return new SesameLocator(reference.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getSubjectLocators()
   */
  @Override
  public Set<Locator> getSubjectLocators() {
    return getLocatorsExpliceite(RTM.SUBJECLOCATOR);
  }

  private Set<Locator> getLocatorsExpliceite(URI criteria) {
    Set<Locator> locators = new HashSet<Locator>();
    URI resource;
    Iterator<Value> objects;
    Set<Resource> subjectLocatorPredicates = mapping.mapsTo(criteria);
    Iterator<Resource> subjectLocatorPredicatesIterator = subjectLocatorPredicates
        .iterator();
    while (subjectLocatorPredicatesIterator.hasNext()) {
      resource = (URI) subjectLocatorPredicatesIterator.next();
      objects = getObjects(reference, resource).iterator();
      while (objects.hasNext()) {
        locators.add(new SesameLocator(objects.next().stringValue()));
      }
    }
    return locators;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getTypes()
   */
  @Override
  public Set<Topic> getTypes() {
    Set<Topic> topics = new HashSet<Topic>();
    Iterator<Resource> isInstanceOfUri = mapping.mapsTo(RTM.INSTANCEOF)
        .iterator();
    Iterator<Value> i;
    while (isInstanceOfUri.hasNext()) {
      Resource resource = isInstanceOfUri.next();
      i = getObjects(reference, (URI) resource).iterator();
      while (i.hasNext()) {
        Value value = i.next();
        topics.add(new LTopic(new SesameLocator(value.stringValue()), tm));
      }
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#mergeIn(org.tmapi.core.Topic)
   */
  @Override
  public void mergeIn(Topic arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#removeSubjectIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void removeSubjectIdentifier(Locator arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#removeSubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public void removeSubjectLocator(Locator arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#removeType(org.tmapi.core.Topic)
   */
  @Override
  public void removeType(Topic arg0) {
    notSupported();
  }

  @Override
  public void addItemIdentifier(Locator arg0) {
    notSupported();
  }

  @Override
  public TopicMap getParent() {
    return tm;
  }

  @Override
  public void remove() {
    notSupported();
  }

  @Override
  public String getId() {
    return reference.stringValue();
  }

//  @Override
//  public Set<Locator> getItemIdentifiers() {
//    Set<Locator> locators = new HashSet<Locator>();
//    locators.add(new SesameLocator(reference.stringValue()));
//    return locators;
//  }

  @Override
  public TopicMap getTopicMap() {
    return tm;
  }

  @Override
  public void removeItemIdentifier(Locator arg0) {
    notSupported();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String NEW_LINE = System.getProperty("line.separator");
    builder.append("si: " + getSubjectIdentifiers().toString() + NEW_LINE);
    return builder.toString() + NEW_LINE;
  }

  @Override
  public int hashCode() {
    return reference.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    LTopic topic = (LTopic) o;
    return hashCode() == topic.hashCode();
  }

}

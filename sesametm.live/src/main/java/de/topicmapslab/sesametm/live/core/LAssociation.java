/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.tmapi.core.Association;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LAssociation extends LConstruct implements Association {

  URI theSubjectPayer, theObjectPlayer;

  public LAssociation(URI type, SparqlTopicMap theTM, URI subjectPlayer,
      URI objectPlayer) {
    super(type, theTM);
    theSubjectPayer = subjectPlayer;
    theObjectPlayer = objectPlayer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((theObjectPlayer == null) ? 0 : theObjectPlayer.hashCode());
    result = prime * result
        + ((theSubjectPayer == null) ? 0 : theSubjectPayer.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    LAssociation other = (LAssociation) obj;
    if (theObjectPlayer == null) {
      if (other.theObjectPlayer != null)
        return false;
    } else if (!theObjectPlayer.equals(other.theObjectPlayer))
      return false;
    if (theSubjectPayer == null) {
      if (other.theSubjectPayer != null)
        return false;
    } else if (!theSubjectPayer.equals(other.theSubjectPayer))
      return false;
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getParent()
   */
  @Override
  public TopicMap getParent() {
    return tm;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#createRole(org.tmapi.core.Topic,
   * org.tmapi.core.Topic)
   */
  @Override
  public Role createRole(Topic arg0, Topic arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#getRoleTypes()
   */
  @Override
  public Set<Topic> getRoleTypes() {
    HashSet<Topic> result = new HashSet<Topic>();
    result.add(new LTopic(new SesameLocator(mapping.objectRole(reference).stringValue()), tm));
    result.add(new LTopic(new SesameLocator(mapping.subjectRole(reference).stringValue()), tm));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#getRoles()
   */
  @Override
  public Set<Role> getRoles() {
    Set<Role> roles = new HashSet<Role>();
    roles.add(new LRole((URI) mapping.subjectRole(reference), this,
        theSubjectPayer));
    roles.add(new LRole((URI) mapping.objectRole(reference), this,
        theObjectPlayer));
    return roles;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#getRoles(org.tmapi.core.Topic)
   */
  @Override
  public Set<Role> getRoles(Topic type) {    
    HashSet<Role> result = new HashSet<Role>();
    Iterator<Role> ri = getRoles().iterator();
    Role role;
    while (ri.hasNext()) {
      role = ri.next();
      if (role.getType().equals(type))
        result.add(role);      
    }
    return result;
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
   * @see org.tmapi.core.Typed#getType()
   */
  @Override
  public Topic getType() {
    URI usedType = reference;
    Iterator<Resource> typeMappingIterator = mapping.getObjects(usedType,
        RTM.TYPE).iterator();
    if (typeMappingIterator.hasNext())
      usedType = (URI) typeMappingIterator.next();
    return new LTopic(new SesameLocator(usedType.stringValue()), tm);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Typed#setType(org.tmapi.core.Topic)
   */
  @Override
  public void setType(Topic arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Scoped#addTheme(org.tmapi.core.Topic)
   */
  @Override
  public void addTheme(Topic arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Scoped#getScope()
   */
  @Override
  public Set<Topic> getScope() {
    Set<Topic> topics = new HashSet<Topic>();
    topics.addAll(generateTopicsFromResources(mapping.getObjects(reference,
        RTM.INSCOPE)));
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Scoped#removeTheme(org.tmapi.core.Topic)
   */
  @Override
  public void removeTheme(Topic arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getId()
   */
  @Override
  public String getId() {
    return reference.getNamespace()
        + "?constructType=Association?associationType="
        + reference.stringValue() + "?subjectPayer=" + theSubjectPayer
        + "?objectPayer=" + theObjectPlayer;
  }

}

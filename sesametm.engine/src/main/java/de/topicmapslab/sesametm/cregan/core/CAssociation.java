/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.tmapi.core.Association;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;

import de.topicmapslab.sesametm.cregan.utils.CEntity;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CAssociation extends CConstruct implements Association {

  /**
   * Constructor for creating a new {@link Association}
   * 
   * @param sesameTopicMap
   *          The {@link CTopicMap} this Association belongs to.
   */
  public CAssociation(CTopicMap sesameTopicMap) {
    super(sesameTopicMap);
    addRdfStatement(proxy, RDF.TYPE, CREGAN.ASSOCIATION);
  }

  /**
   * Constructor for getting a existing {@link Association}
   * 
   * @param sesameTopicMap
   *          The {@link CTopicMap} this Association belongs to.
   * @param node
   *          The {@link Resource} of the Proxy the Association is build upon.
   */
  public CAssociation(CTopicMap sesameTopicMap, Resource node) {
    super(sesameTopicMap, node);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#createRole(org.tmapi.core.Topic,
   * org.tmapi.core.Topic)
   */
  @Override
  public Role createRole(Topic type, Topic player) {
    if (player == null) {
      throw new ModelConstraintException(getParent(),
          "Role creation where player is null shouldn't be allowed");
    }
    if (type == null) {
      throw new ModelConstraintException(getParent(),
          "Role creation where type is null shouldn't be allowed");
    }
    if (!type.getTopicMap().equals(getTopicMap())) {
      throw new ModelConstraintException(getTopicMap(),
          "Expected a model contraint violation");
    }
    if (!player.getTopicMap().equals(getTopicMap())) {
      throw new ModelConstraintException(getTopicMap(),
          "Expected a model contraint violation");
    }
    Role r = new CRole(this, type, player);
    addRdfStatement(proxy, CREGAN.HASROLE, ((CEntity) r).proxy);
    addRdfStatement(((CEntity) r).proxy, CREGAN.ROLEINASSOCIATION, proxy);
    return r;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#getParent()
   */
  @Override
  public TopicMap getParent() {
    return getTopicMap();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#getRoleTypes()
   */
  @Override
  public Set<Topic> getRoleTypes() {
    Set<Topic> result = new HashSet<Topic>();
    Iterator<Role> i = getRoles().iterator();
    while (i.hasNext()) {
      Role role = i.next();
      Topic t = role.getType();
      result.add(t);
    }
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
    Iterator<Value> proxyUris = getProperties(CREGAN.HASROLE).iterator();
    while (proxyUris.hasNext()) {
      roles.add(new CRole(sesameTopicMap, ((Resource) proxyUris.next())));
    }
    return roles;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Association#getRoles(org.tmapi.core.Topic)
   */
  @Override
  public Set<Role> getRoles(Topic topic) {
    if (topic == null) {
      throw new IllegalArgumentException("getRoles(null) is illegal");
    }
    Set<Role> roles = getRoles();
    Set<Role> result = new HashSet<Role>();
    Iterator<Role> i = roles.iterator();
    while (i.hasNext()) {
      Role role = i.next();
      if (role.getType().equals(topic)) {
        result.add(role);
      }
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
    Topic t = null;
    try {
      t = new CTopic(sesameTopicMap,
          (Resource) getFirstValue(CREGAN.HASREIFIER));
    } catch (Exception e) {
    }
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Reifiable#setReifier(org.tmapi.core.Topic)
   */
  @Override
  public void setReifier(Topic reifier) throws ModelConstraintException {
    removeProperty(CREGAN.HASREIFIER);
    removeForeignValue(CREGAN.ISREIFIEROF, proxy);
    if (reifier != null) {
      if (!reifier.getTopicMap().equals(getTopicMap())) {
        throw new ModelConstraintException(getTopicMap(),
            "Setting the reifier to a topic of another topic map shouldn't be allowed.");
      }
      if (reifier.getReified() != null) {
        throw new ModelConstraintException(this,
            "The reifier reifies already another construct.");
      }
      addPredicate(CREGAN.HASREIFIER, ((CEntity) reifier).proxy);
      addRdfStatement(((CEntity) reifier).proxy, CREGAN.ISREIFIEROF, proxy);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#remove()
   */
  @Override
  public void remove() {
    Iterator<Role> roles = getRoles().iterator();
    while (roles.hasNext()) {
      Role role = roles.next();
      role.remove();
    }
    super.remove();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Typed#getType()
   */
  @Override
  public Topic getType() {
    Topic t = new CTopic(getParent(), (Resource) getFirstValue(TMDM.TYPE));
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Typed#setType(org.tmapi.core.Topic)
   */
  @Override
  public void setType(Topic type) {
    if (type == null) {
      throw new ModelConstraintException(getTopicMap(),
          "Setting the type to null should be disallowed.");
    }
    if (!type.getTopicMap().equals(getTopicMap())) {
      throw new ModelConstraintException(getTopicMap(),
          "Setting the type to a topic from another topic map shouldn't be allowed");
    }
    removeProperty(TMDM.TYPE);
    addPredicate(TMDM.TYPE, ((CEntity) type).proxy);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Scoped#addTheme(org.tmapi.core.Topic)
   */
  @Override
  public void addTheme(Topic theme) {
    if (theme == null) {
      throw new ModelConstraintException(getTopicMap(),
          "addTheme(null) is illegal");
    }
    if (!theme.getTopicMap().equals(getTopicMap())) {
      throw new ModelConstraintException(getTopicMap(),
          "Expected a model contraint violation");
    }
    addPredicate(CREGAN.SCOPE, ((CEntity) theme).proxy);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Scoped#getScope()
   */
  @Override
  public Set<Topic> getScope() {
    Iterator<Value> i = getProperties(CREGAN.SCOPE).iterator();
    Set<Topic> res = new HashSet<Topic>();
    Value value;
    while (i.hasNext()) {
      value = i.next();
      res.add(new CTopic(getParent(), (Resource) value));
    }
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Scoped#removeTheme(org.tmapi.core.Topic)
   */
  @Override
  public void removeTheme(Topic theme) {
    removeValue(CREGAN.SCOPE, ((CTopic) theme).proxy);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return proxy.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    try {
      Association t = (Association) o;
      return t.getId().equals(getId());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Equals according to the TMDM
   * 
   * @param o
   *          The other object to which this should be compared to
   * @return True ore false according to the TMDM
   */
  public boolean mEquals(Object o) {
    try {
      Association a = (Association) o;
      boolean result = true;
      if (!getType().equals(a.getType()))
        result = false;
      return result;
    } catch (Exception e) {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    String NEW_LINE = System.getProperty("line.separator");
    result.append(super.toString() + NEW_LINE);
    Iterator<Role> i = getRoles().iterator();
    while (i.hasNext()) {
      result.append(i.next() + NEW_LINE);
    }
    return result.toString();
  }

}

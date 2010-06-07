/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.core;

import org.openrdf.model.Resource;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.tmapi.core.Association;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;

import de.topicmapslab.sesametm.cregan.utils.CEntity;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CRole extends CConstruct implements Role {

  /**
   * @param tm
   * @param node
   */
  public CRole(CTopicMap tm, Resource node) {
    super(tm, node);
  }

  public CRole(CAssociation sesameAssociation, Topic type,
      Topic player) {
    super(sesameAssociation.getParent());
    setType(type);
    addRdfStatement(proxy, RDF.TYPE, CREGAN.ROLE);
    addRdfStatement(proxy, CREGAN.PLAYEDBY, ((CEntity) player).proxy);
    addRdfStatement(((CEntity) player).proxy, CREGAN.HASROLE, proxy);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Role#getParent()
   */
  @Override
  public Association getParent() {
    Value v = getFirstValue(CREGAN.ROLEINASSOCIATION);
    return new CAssociation(sesameTopicMap, (Resource) v);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Role#getPlayer()
   */
  @Override
  public Topic getPlayer() {
    Value v = getFirstValue(CREGAN.PLAYEDBY);
    return new CTopic(sesameTopicMap, (Resource) v);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Role#setPlayer(org.tmapi.core.Topic)
   */
  @Override
  public void setPlayer(Topic player) {
    if (player == null) {
      throw new ModelConstraintException(getTopicMap(),
          "Setting the role player to null is not allowed");
    }
    if (!player.getTopicMap().equals(getTopicMap())) {
      throw new ModelConstraintException(getTopicMap(),
          "Setting the player to a topic of another topic map shouldn't be allowed.");
    }
    removeProperty(CREGAN.PLAYEDBY);

    removeForeignValue(CREGAN.HASROLE, proxy);
    addRdfStatement(proxy, CREGAN.PLAYEDBY, ((CEntity) player).proxy);
    addRdfStatement(((CEntity) player).proxy, CREGAN.HASROLE, proxy);
    addRdfStatement(proxy, CREGAN.ROLEINASSOCIATION,
        ((CEntity) player).proxy);

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
   * @see org.tmapi.core.Typed#getType()
   */
  @Override
  public Topic getType() {
    Topic t = new CTopic(getTopicMap(),
        (Resource) getFirstValue(TMDM.TYPE));
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
    Role r = (Role) o;
    return r.getId().equals(getId());
  }

}

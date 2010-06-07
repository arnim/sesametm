/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import org.openrdf.model.URI;
import org.tmapi.core.Association;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;

import de.topicmapslab.sesametm.core.SesameLocator;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LRole extends LConstruct implements Role {

  URI thePlayer;
  LAssociation parent;

  /**
   * @param l
   * @param theTms
   */
  public LRole(URI type, LAssociation theAssociation, URI player) {
    super(type, (SparqlTopicMap) theAssociation.getParent());
    thePlayer = player;
    parent = theAssociation;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getParent()
   */
  @Override
  public Association getParent() {
    return parent;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Role#getPlayer()
   */
  @Override
  public Topic getPlayer() {
    return new LTopic(new SesameLocator(thePlayer.stringValue()), tm);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Role#setPlayer(org.tmapi.core.Topic)
   */
  @Override
  public void setPlayer(Topic arg0) {
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
   * @see org.tmapi.core.Typed#getType()
   */
  @Override
  public Topic getType() {
    return new LTopic(new SesameLocator(reference.stringValue()), tm);
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
   * @see org.tmapi.core.Construct#getId()
   */
  @Override
  public String getId() {
    // TODO if is not unique!!
    return ":roles/" + reference.getLocalName();
  }

}

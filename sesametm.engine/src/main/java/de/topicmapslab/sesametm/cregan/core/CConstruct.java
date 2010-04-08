/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.tmapi.core.Construct;
import org.tmapi.core.IdentityConstraintException;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.cregan.utils.CEntity;
import de.topicmapslab.sesametm.cregan.utils.SesameVerify;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CConstruct extends CEntity implements Construct {

  protected SesameVerify verify;

  /**
   * Constructor for creating a new SesameConstruct
   * 
   * @param Connection
   * @param tm
   */
  public CConstruct(TopicMap tm) {
    super((CTopicMap) tm);
    verify = new SesameVerify(this);
  }

  /**
   * Constructor for retrieving an existing SesameConstruct
   * 
   * @param tm
   * @param proxy
   */
  public CConstruct(TopicMap tm, Resource proxy) {
    super((CTopicMap) tm, proxy);
    verify = new SesameVerify(this);
  }

  /**
   * Constructor for retrieving an existing SesameConstruct
   * 
   * @param tm
   * @param key
   * @param l
   */
  public CConstruct(CTopicMap tm, URI key, Locator l) {
    super(tm, key, l);
    verify = new SesameVerify(this);
  }

  /**
   * Constructor for retrieving an existing SesameConstruct
   * 
   * @param uri
   * @param tms
   */
  public CConstruct(URI uri, CTopicMapSystem tms) {
    super(uri, tms);
    verify = new SesameVerify(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#addItemIdentifier()
   */
  @Override
  public void addItemIdentifier(Locator itemIdentifier) {
    if (itemIdentifier == null) {
      throw new ModelConstraintException(getTopicMap(),
          "addItemIdentifier(null) is illegal");
    }
    Construct existing = getTopicMap().getConstructByItemIdentifier(
        itemIdentifier);
    if (existing == null)
      existing = getTopicMap().getTopicBySubjectIdentifier(itemIdentifier);
    boolean eq = false;
    try {
      eq = equals(existing);
    } catch (Exception e) {
    }
    if (existing != null && !eq) {
      throw new IdentityConstraintException(this, getTopicMap()
          .getConstructByItemIdentifier(itemIdentifier), itemIdentifier,
          "Topic Maps constructs with the same item identifier are not allowed");
    }
    SesameLocator sl = (SesameLocator) itemIdentifier;
    addPredicate(CREGAN.ITEMIDENTIFIER, sl.getUri());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getId()
   */
  @Override
  public String getId() {
    return proxy.stringValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getItemIdentifiers()
   */
  @Override
  public Set<Locator> getItemIdentifiers() {
    Set<Locator> result = new HashSet<Locator>();
    Iterator<Value> i = super.getProperties(CREGAN.ITEMIDENTIFIER).iterator();
    while (i.hasNext()) {
      Value value = i.next();
      result.add(new SesameLocator(value.stringValue()));
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getParent()
   */
  @Override
  public Construct getParent() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getTopicMap()
   */
  @Override
  public TopicMap getTopicMap() {
    return sesameTopicMap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#remove()
   */
  @Override
  public void remove() {
    super.remove();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#removeItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void removeItemIdentifier(Locator itemIdentifier) {
    removeValue(CREGAN.ITEMIDENTIFIER, ((SesameLocator) itemIdentifier).getUri());
  }

  /**
   * @return The Topic with the SubjectIdentifier
   *         http://psi.topicmaps.org/iso13250/model/supertype-subtype
   */
  public Topic SuperTypeSubtypeAssoType() {
    return sesameTopicMap.getTopicBySubjectIdentifier(sesameTopicMap
        .createLocator(TMDM.SUPERTYPESUBTYPEASSOTYPE.stringValue()));
  }

  /**
   * @return The Topic with the SubjectIdentifier
   *         http://psi.topicmaps.org/iso13250/model/type-instance
   */
  protected Topic IypeInstanceAssoType() {
    return sesameTopicMap.getTopicBySubjectIdentifier(sesameTopicMap
        .createLocator(TMDM.TYPEINSTANCEASSOTYPE.stringValue()));
  }

  /**
   * @return The Topic with the SubjectIdentifier
   *         http://psi.topicmaps.org/iso13250/glossary/unconstrained-scope
   */
  protected Topic UcsTopic() {
    return sesameTopicMap.getTopicBySubjectIdentifier(sesameTopicMap
        .createLocator(TMDM.UCS.stringValue()));
  }

  /**
   * @return The Topic with the SubjectIdentifier
   *         http://psi.topicmaps.org/iso13250/model/topic-name
   */
  protected Topic defaultNameTypeTopic() {
    return sesameTopicMap.getTopicBySubjectIdentifier(sesameTopicMap
        .createLocator(TMDM.TOPICNAME.stringValue()));
  }

  /**
   * @return The Topic with the SubjectIdentifier
   *         http://psi.topicmaps.org/iso13250/model/instance
   */
  protected Topic theInstance() {
    return sesameTopicMap.getTopicBySubjectIdentifier(sesameTopicMap
        .createLocator(TMDM.INSTANCE.stringValue()));
  }

  /**
   * @return The Topic with the SubjectIdentifier
   *         http://psi.topicmaps.org/iso13250/model/type
   */
  protected Topic theType() {
    return sesameTopicMap.getTopicBySubjectIdentifier(sesameTopicMap
        .createLocator(TMDM.TYPE.stringValue()));
  }

}

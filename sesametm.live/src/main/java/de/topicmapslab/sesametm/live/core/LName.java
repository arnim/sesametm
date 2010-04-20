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
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Name;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.utils.LValue;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LName extends LConstruct implements Name {

  private LValue theValue;
  private URI theType;
  private LTopic theParent;

  public LName(URI subject, URI occrencePredicate, LValue Value,
      LTopic sparqlTopic) {
    super(subject, (SparqlTopicMap) sparqlTopic.getParent());
    theType = occrencePredicate;
    theValue = Value;
    theParent = sparqlTopic;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getParent()
   */
  @Override
  public Topic getParent() {
    return theParent;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Variant createVariant(String arg0, Topic... arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * java.util.Collection)
   */
  @Override
  public Variant createVariant(String arg0, Collection<Topic> arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(org.tmapi.core.Locator,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Variant createVariant(Locator arg0, Topic... arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(org.tmapi.core.Locator,
   * java.util.Collection)
   */
  @Override
  public Variant createVariant(Locator arg0, Collection<Topic> arg1) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * org.tmapi.core.Locator, org.tmapi.core.Topic[])
   */
  @Override
  public Variant createVariant(String arg0, Locator arg1, Topic... arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * org.tmapi.core.Locator, java.util.Collection)
   */
  @Override
  public Variant createVariant(String arg0, Locator arg1, Collection<Topic> arg2) {
    notSupported();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#getValue()
   */
  @Override
  public String getValue() {
    return theValue.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#getVariants()
   */
  @Override
  public Set<Variant> getVariants() {
    return new HashSet<Variant>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#setValue(java.lang.String)
   */
  @Override
  public void setValue(String arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Typed#getType()
   */
  @Override
  public Topic getType() {
    URI usedType = theType;
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
    topics.addAll(generateTopicsFromResources(mapping.getObjects(theType,
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
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return (theParent.getId() + theType.stringValue() + theValue.toString() + reference
        .toString()).hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    try {
      LName oName = (LName) obj;
      if (oName.getValue().equals(getValue())
          && oName.getType().equals(getType())
          && oName.getParent().equals(getParent()))
        return true;
    } catch (Exception e) {
    }
    return false;
  }

}

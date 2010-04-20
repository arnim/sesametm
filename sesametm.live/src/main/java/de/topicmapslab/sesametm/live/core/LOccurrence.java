/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.utils.LValue;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LOccurrence extends LConstruct implements Occurrence {

  private LValue theValue;
  private URI theType;
  private LTopic theParent;

  public LOccurrence(URI subject, URI occrencePredicate, LValue Value,
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
   * @see org.tmapi.core.DatatypeAware#decimalValue()
   */
  @Override
  public BigDecimal decimalValue() {
    return new BigDecimal(theValue.getValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#floatValue()
   */
  @Override
  public float floatValue() {
    return new Float(theValue.getValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#getDatatype()
   */
  @Override
  public Locator getDatatype() {
    return new SesameLocator(theValue.getType().toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#getValue()
   */
  @Override
  public String getValue() {
    return theValue.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#intValue()
   */
  @Override
  public int intValue() {
    return new Integer(theValue.getValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#integerValue()
   */
  @Override
  public BigInteger integerValue() {
    return new BigInteger(theValue.getValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#locatorValue()
   */
  @Override
  public Locator locatorValue() {
    if (!theValue.getType().toString().equals(
        "http://www.w3.org/2001/XMLSchema#anyURI"))
      throw new IllegalArgumentException(
          "The value cannot be represented as a Locator instance");
    return new SesameLocator(theValue.getValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#longValue()
   */
  @Override
  public long longValue() {
    return new Long(theValue.getValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.lang.String)
   */
  @Override
  public void setValue(String arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(org.tmapi.core.Locator)
   */
  @Override
  public void setValue(Locator arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.math.BigDecimal)
   */
  @Override
  public void setValue(BigDecimal arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.math.BigInteger)
   */
  @Override
  public void setValue(BigInteger arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(long)
   */
  @Override
  public void setValue(long arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(float)
   */
  @Override
  public void setValue(float arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(int)
   */
  @Override
  public void setValue(int arg0) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.lang.String,
   * org.tmapi.core.Locator)
   */
  @Override
  public void setValue(String arg0, Locator arg1) {
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
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return (theParent.getId() + theType.stringValue() + theValue.toString()
        + reference.toString() + getDatatype().toExternalForm()).hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    try {
      LOccurrence oOccurrence = (LOccurrence) obj;
      if (oOccurrence.getValue().equals(getValue())
          && oOccurrence.getType().equals(getType())
          && oOccurrence.getParent().equals(getParent())
          && oOccurrence.getDatatype().equals(getDatatype()))
        return true;
    } catch (Exception e) {
    }
    return false;
  }

}

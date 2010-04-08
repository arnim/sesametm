/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.cregan.utils.CEntity;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class COccurrence extends CConstruct implements Occurrence {

  public COccurrence(CTopicMap tm) {
    super(tm);
    addRdfStatement(proxy, RDF.TYPE, CREGAN.OCCURRENCE);
  }

  public COccurrence(CTopicMap tm, Resource node) {
    super(tm, node);
    addRdfStatement(proxy, RDF.TYPE, CREGAN.OCCURRENCE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Occurrence#getParent()
   */
  @Override
  public Topic getParent() {
    return new CTopic(sesameTopicMap, (Resource) super
        .getFirstValue(CREGAN.OCCURENCEOF));
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
   * @see org.tmapi.core.DatatypeAware#decimalValue()
   */
  @Override
  public BigDecimal decimalValue() {
    testForNumberFormatException();
    String v = getFirstValue(CREGAN.VALUE).stringValue();
    BigDecimal result = new BigDecimal(v);
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#floatValue()
   */
  @Override
  public float floatValue() {
    testForNumberFormatException();
    String v = getFirstValue(CREGAN.VALUE).stringValue();
    Float result = new Float(v);
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#getDatatype()
   */
  @Override
  public Locator getDatatype() {
    return new SesameLocator(super.getFirstValue(CREGAN.DATATYPE).stringValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#getValue()
   */
  @Override
  public String getValue() {
    return getFirstValue(CREGAN.VALUE).stringValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#intValue()
   */
  @Override
  public int intValue() {
    testForNumberFormatException();
    String v = getFirstValue(CREGAN.VALUE).stringValue();
    Integer result = new Integer(myTrim(v));
    return result;
  }

  private String myTrim(String v) {
    int i = v.indexOf(".");
    if (i > 0) {
      v = v.substring(0, i);
    }
    return v;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#integerValue()
   */
  @Override
  public BigInteger integerValue() {
    testForNumberFormatException();
    String v = getFirstValue(CREGAN.VALUE).stringValue();
    BigInteger result = new BigInteger(myTrim(v));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#locatorValue()
   */
  @Override
  public Locator locatorValue() {
    return new SesameLocator(getFirstValue(CREGAN.VALUE).stringValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#longValue()
   */
  @Override
  public long longValue() {
    testForNumberFormatException();
    String v = getFirstValue(CREGAN.VALUE).stringValue();
    Long result = new Long(myTrim(v));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.lang.String)
   */
  @Override
  public void setValue(String arg0) {
    if (arg0 == null) {
      throw new ModelConstraintException(sesameTopicMap,
          "datatypeAware.setValue((String)null) is illegal");
    }
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, XMLSchema.STRING);
    addProperty(CREGAN.VALUE, arg0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(org.tmapi.core.Locator)
   */
  @Override
  public void setValue(Locator arg0) {
    if (arg0 == null) {
      throw new ModelConstraintException(sesameTopicMap,
          "datatypeAware.setValue((Locator)null) is illegal");
    }
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, XMLSchema.ANYURI);
    addPredicate(CREGAN.VALUE, ((SesameLocator) arg0).getUri());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.math.BigDecimal)
   */
  @Override
  public void setValue(BigDecimal arg0) {
    if (arg0 == null) {
      throw new ModelConstraintException(sesameTopicMap,
          "datatypeAware.setValue((BigDecimal)null) is illegal");
    }
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, XMLSchema.DECIMAL);
    addProperty(CREGAN.VALUE, arg0.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.math.BigInteger)
   */
  @Override
  public void setValue(BigInteger arg0) {
    if (arg0 == null) {
      throw new ModelConstraintException(sesameTopicMap,
          "datatypeAware.setValue((BigInteger)null) is illegal");
    }
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, XMLSchema.INTEGER);
    addProperty(CREGAN.VALUE, arg0.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(long)
   */
  @Override
  public void setValue(long arg0) {
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, XMLSchema.LONG);
    addProperty(CREGAN.VALUE, Long.toString(arg0));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(float)
   */
  @Override
  public void setValue(float arg0) {
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, XMLSchema.FLOAT);
    addProperty(CREGAN.VALUE, Float.toString(arg0));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(int)
   */
  @Override
  public void setValue(int arg0) {
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, XMLSchema.INT);
    addProperty(CREGAN.VALUE, "" + arg0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#setValue(java.lang.String,
   * org.tmapi.core.Locator)
   */
  @Override
  public void setValue(String arg0, Locator arg1) {
    if (arg1 == null) {
      throw new ModelConstraintException(sesameTopicMap,
          "datatypeAware.setValue(\"value\", null) is illegal");
    }
    if (arg0 == null) {
      throw new ModelConstraintException(sesameTopicMap,
          "datatypeAware.setValue(null, datatype) is illegal");
    }
    removeProperty(CREGAN.VALUE);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, ((SesameLocator) arg1).getUri());
    addProperty(CREGAN.VALUE, arg0);
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
      res.add(new CTopic(getTopicMap(), (Resource) value));
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

  private void testForNumberFormatException() {
    String str = getFirstValue(CREGAN.DATATYPE).toString();
    if (!(str.equals(XMLSchema.INTEGER.toString()))
        && !(str.equals(XMLSchema.DECIMAL.toString()))
        && !(str.toString().equals(XMLSchema.INT.toString()))
        && !(str.toString().equals(XMLSchema.LONG.toString()))
        && !(str.equals(XMLSchema.FLOAT.toString()))) {
      throw new NumberFormatException("Failure for converting the value");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    try {
      Occurrence oc = (Occurrence) o;
      boolean result = true;
      if (!getValue().equals(oc.getValue()))
        result = false;
      if (!getType().equals(oc.getType()))
        result = false;
      if (!getScope().equals(oc.getScope()))
        result = false;
      if (!getParent().equals(oc.getParent()))
        result = false;
      if (!getDatatype().equals(oc.getDatatype()))
        result = false;
      return result;
    } catch (Exception e) {
      return false;
    }
  }

}

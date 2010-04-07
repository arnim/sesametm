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
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Name;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.Variant;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.cregan.utils.CEntity;
import de.topicmapslab.sesametm.vocabularies.CREGAN;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CVariant extends CConstruct implements Variant {

  Name p;

  public CVariant(Name p) {
    super(p.getTopicMap());
    addRdfStatement(proxy, RDF.TYPE, CREGAN.VARIANT);
    addRdfStatement(proxy, CREGAN.ISVARIANTOF, ((CEntity) p).proxy);
    addRdfStatement(((CEntity) p).proxy, CREGAN.HASVARIANT, proxy);
  }

  public CVariant(TopicMap tm, Resource node) {
    super(tm, node);
  }

  /**
   * @param tm
   * @param key
   * @param l
   */
  public CVariant(CTopicMap tm, URI key, Locator l) {
    super(tm, key, l);
  }

  @Override
  public Name getParent() {
    return new CName(sesameTopicMap,
        (Resource) getFirstValue(CREGAN.ISVARIANTOF));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Variant#getScope()
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
    res.addAll(getParent().getScope());
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#decimalValue()
   */
  @Override
  public BigDecimal decimalValue() {
    if (!(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INTEGER
        .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.DECIMAL
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INT
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.LONG
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.FLOAT
            .toString()))) {
      throw new NumberFormatException(
          "Failure for converting the value to 'BigDecimal'");
    }
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
    if (!(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INTEGER
        .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.DECIMAL
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INT
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.LONG
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.FLOAT
            .toString()))) {
      throw new NumberFormatException(
          "Failure for converting the value to 'float'");
    }
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
    if (!(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INTEGER
        .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.DECIMAL
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INT
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.LONG
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.FLOAT
            .toString()))) {
      throw new NumberFormatException(
          "Failure for converting the value to 'Int'");
    }
    String v = getFirstValue(CREGAN.VALUE).stringValue();
    Integer result = new Integer(myTrim(v));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.DatatypeAware#integerValue()
   */
  @Override
  public BigInteger integerValue() {
    if (!(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INTEGER
        .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.DECIMAL
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INT
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.LONG
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.FLOAT
            .toString()))) {
      throw new NumberFormatException(
          "Failure for converting the value to 'BigInteger'");
    }
    String v = getFirstValue(CREGAN.VALUE).stringValue();
    BigInteger result = new BigInteger(myTrim(v));
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
    if (!(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INTEGER
        .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.DECIMAL
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.INT
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.LONG
            .toString()))
        && !(getFirstValue(CREGAN.DATATYPE).toString().equals(XMLSchema.FLOAT
            .toString()))) {
      throw new NumberFormatException(
          "Failure for converting the value to 'BigInteger'");
    }
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

  public void setThisDatatype(Locator dt) {
    verify.parameterIsNotNull(dt);
    removeProperty(CREGAN.DATATYPE);
    addPredicate(CREGAN.DATATYPE, ((SesameLocator) dt).getUri());
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

  @Override
  public int hashCode() {
    return proxy.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    Variant v = (Variant) o;
    return v.getId().equals(getId());
  }

}

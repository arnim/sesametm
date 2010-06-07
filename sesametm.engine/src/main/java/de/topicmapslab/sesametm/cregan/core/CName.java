/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.core;

import java.util.Collection;
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
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CName extends CConstruct implements Name {

  public CName(TopicMap tm) {
    super(tm);
    addRdfStatement(proxy, RDF.TYPE, CREGAN.NAME);
  }

  public CName(TopicMap tm, Resource node) {
    super(tm, node);
  }

  public CName(CTopicMap tm, URI key, Locator l) {
    super(tm, key, l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Topic getParent() {
    return new CTopic(sesameTopicMap,
        (Resource) getFirstValue(CREGAN.NAMEOF));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Variant createVariant(String value, Topic... scopes) {
    if (scopes == null) {
      throw new ModelConstraintException(getTopicMap(),
          "Creation of a variant with an null scope is not allowed");
    }
    Set<Topic> sc = getScope();
    int z = scopes.length;
    for (Topic ele : scopes)
      if (sc.contains(ele))
        z--;
    if (z == 0)
      throw new ModelConstraintException(getTopicMap(),
          "The variant would be in the same scope as the parent");

    Variant v = new CVariant(this);
    v.setValue(value);
    for (Topic element : scopes)
      v.addTheme(element);
    return v;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * java.util.Collection)
   */
  @Override
  public Variant createVariant(String value, Collection<Topic> scopes) {
    if (scopes.isEmpty()) {
      throw new ModelConstraintException(getTopicMap(),
          "Creation of a variant with an empty scope is not allowed");
    }
    Topic[] ts = scopes.toArray(new Topic[0]);
    return createVariant(value, new SesameLocator(XMLSchema.STRING
        .stringValue()), ts);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(org.tmapi.core.Locator,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Variant createVariant(Locator value, Topic... scopes) {
    Variant v = new CVariant(this);
    v.setValue(value);
    if (scopes == null || scopes.length == 0)
      throw new ModelConstraintException(this, "Creation of a variant with an empty scope is not allowed");
    for (Topic element : scopes)
      v.addTheme(element);
    return v;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(org.tmapi.core.Locator,
   * java.util.Collection)
   */
  @Override
  public Variant createVariant(Locator arg0, Collection<Topic> arg1) {
    if (arg1.isEmpty())
      throw new ModelConstraintException(this, "Creation of a variant with an empty scope is not allowed");
    return createVariant(arg0, (Topic[]) arg1.toArray());
  }
  
  private boolean isTrueSuperset(Topic... scopes){
    Set<Topic> sc = getScope();
    int z = scopes.length;
    for (Topic ele : scopes)
      if (sc.contains(ele))
        z--;
    return (z>0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * org.tmapi.core.Locator, org.tmapi.core.Topic[])
   */
  @Override
  public Variant createVariant(String value, Locator dt, Topic... scopes) {
    if (scopes == null || scopes.length == 0)
      throw new ModelConstraintException(this, "Creation of a variant with an empty scope is not allowed");
    if (!isTrueSuperset(scopes))
      throw new ModelConstraintException(getTopicMap(),
          "Scopes of the new Variant have to be a true SuperSet of its Parent");
    if (dt == null)
      throw new ModelConstraintException(getTopicMap(),
          "Creation of a variant with datatype == null is not allowed");
    CVariant v = new CVariant(this);
    v.setValue(value);
    for (Topic element : scopes)
      v.addTheme(element);
    v.setThisDatatype(dt);
    return v;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#createVariant(java.lang.String,
   * org.tmapi.core.Locator, java.util.Collection)
   */
  @Override
  public Variant createVariant(String value, Locator dt, Collection<Topic> arg2) {
    if (arg2.isEmpty())
      throw new ModelConstraintException(this, "Creation of a variant with an empty scope is not allowed");
    return createVariant(value, dt, (Topic[]) arg2.toArray());
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
   * @see org.tmapi.core.Name#getVariants()
   */
  @Override
  public Set<Variant> getVariants() {
    Set<Variant> variants = new HashSet<Variant>();
    Iterator<Value> proxyUris = getProperties(CREGAN.HASVARIANT).iterator();
    while (proxyUris.hasNext()) {
      variants.add(new CVariant(sesameTopicMap, ((Resource) proxyUris
          .next())));
    }
    return variants;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Name#setValue(java.lang.String)
   */
  @Override
  public void setValue(String arg0) {
    if (arg0 == null) {
      throw new ModelConstraintException(getTopicMap(),
          "setValue(null) is not allowed");
    }

    removeProperty(CREGAN.VALUE);
    addProperty(CREGAN.VALUE, arg0);

    Topic t = getParent();
    Set<Name> otherNames = t.getNames(getType());
    otherNames.remove(this);

    Iterator<Name> i = otherNames.iterator();
    while (i.hasNext()) {
      Name name = i.next();
      if (name.equals(this))
        mergeIn(name);
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
    if (type == null)
      throw new ModelConstraintException(getTopicMap(),
          "Setting the type to null should be disallowed.");
    if (!type.getTopicMap().equals(getTopicMap()))
      throw new ModelConstraintException(getTopicMap(),
          "Setting the type to a topic from another topic map shouldn't be allowed");
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
    if (theme == null)
      throw new ModelConstraintException(getTopicMap(),
          "addTheme(null) is illegal");
    if (!theme.getTopicMap().equals(getTopicMap()))
      throw new ModelConstraintException(getTopicMap(),
          "Expected a model contraint violation");
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

  public void mergeIn(Name o) {
    CTopic tr = (CTopic) getReifier();
    CTopic or = (CTopic) o.getReifier();

    super.mergeIn(o);

    try {
      if (tr.mEquals(or))
        tr.mergeIn(or);
    } catch (NullPointerException e) {
    }
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
      Name n = (Name) o;
      boolean result = true;
      if (!getValue().equals(n.getValue()))
        result = false;
      if (!getType().equals(n.getType()))
        result = false;
      if (!getScope().equals(n.getScope()))
        result = false;
      if (!getParent().equals(n.getParent()))
        result = false;
      return result;
    } catch (Exception e) {
      return false;
    }

  }

}

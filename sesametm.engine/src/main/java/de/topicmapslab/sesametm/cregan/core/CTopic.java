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
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.RepositoryResult;
import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.IdentityConstraintException;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Reifiable;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicInUseException;
import org.tmapi.core.TopicMap;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.cregan.utils.CEntity;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CTopic extends CConstruct implements Topic {

  public CTopic(TopicMap tm) {
    super(tm);
    addRdfStatement(proxy, RDF.TYPE, CREGAN.TOPIC);
  }

  public CTopic(TopicMap tm, Resource proxy) {
    super(tm, proxy);
  }

  public CTopic(CTopicMap tm, URI key, Locator l) {
    super(tm, key, l);
  }

  @Override
  public TopicMap getParent() {
    return getTopicMap();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#addItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void addItemIdentifier(Locator itemIdentifier) {
    if (sesameTopicMap.isAutomerged()) {
      Construct existing = getTopicMap().getConstructByItemIdentifier(
          itemIdentifier);
      if (existing == null)
        existing = getTopicMap().getTopicBySubjectIdentifier(itemIdentifier);

      if (existing != null && !equals(existing)) {
        mergeIn(existing);
      }
      super.addItemIdentifier(itemIdentifier);
    } else {
      super.addItemIdentifier(itemIdentifier);
    }
  }

  @Override
  public void addSubjectIdentifier(Locator arg0) {
    verify.parameterIsNotNull(arg0);
    CTopic existing = (CTopic) getParent()
        .getTopicBySubjectIdentifier(arg0);
    SesameLocator sl = (SesameLocator) arg0;
    if (existing == null)
      existing = (CTopic) getParent().getConstructByItemIdentifier(arg0);
    if (sesameTopicMap.isAutomerged()) {
      if (existing != null) {
        mergeIn(existing);
      }
    } else {
      if (existing != null && !equals(existing)) {
        throw new IdentityConstraintException(this, existing, arg0,
            "Duplicate Subject Identifier");
      }
    }
    addPredicate(CREGAN.SUBJECTIDETIFIER, sl.getUri());
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.topicmapslab.sesametm.cregan.core.SesameConstruct#remove()
   */
  @Override
  public void remove() {
    if (getReified() != null)
      throw new TopicInUseException(this, "The topic is used as reifier");
    if (labelExists(TMDM.TYPE, proxy))
      throw new TopicInUseException(this, "The topic is used as type");
    if (labelExists(CREGAN.SCOPE, proxy))
      throw new TopicInUseException(this, "The topic is used as type");
    Set<Role> rolesPlayed = getRolesPlayed();
    if (!rolesPlayed.isEmpty()
        && (rolesPlayed.size() != getRolesPlayed(theInstance()).size()))
      throw new TopicInUseException(this, "The topic is used as type / player");
    Iterator<Occurrence> ocs = getOccurrences().iterator();
    while (ocs.hasNext()) {
      Occurrence occurrence = ocs.next();
      occurrence.remove();
    }
    Iterator<Name> nas = getNames().iterator();
    while (nas.hasNext()) {
      Name name = nas.next();
      name.remove();
    }
    super.remove();
  }

  public void mergeRemove() {
    super.remove();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#addSubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public void addSubjectLocator(Locator arg0) {
    verify.parameterIsNotNull(arg0);
    Topic existing = getParent().getTopicBySubjectLocator(arg0);
    SesameLocator sl = (SesameLocator) arg0;
    if (sesameTopicMap.isAutomerged()) {
      if (existing != null) {
        mergeIn(existing);
      }
    } else {
      if (existing != null && !equals(existing)) {
        throw new IdentityConstraintException(this, existing, arg0,
            "Duplicate Subject Locator");
      }
    }

    addPredicate(CREGAN.SUBJECTLOCATOR, sl.getUri());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#addType(org.tmapi.core.Topic)
   */
  @Override
  public void addType(Topic type) {
    verify.parameterIsNotNull(type);
    Association tia = sesameTopicMap.createAssociation(IypeInstanceAssoType(),
        UcsTopic());
    tia.createRole(theInstance(), this);
    tia.createRole(theType(), type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(java.lang.String,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Name createName(String value, Topic... scopes) {
    return createName(super.defaultNameTypeTopic(), value, scopes);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(java.lang.String,
   * java.util.Collection)
   */
  @Override
  public Name createName(String value, Collection<Topic> scopes) {
    return createName(super.defaultNameTypeTopic(), value, scopes);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Topic[])
   */
  @Override
  public Name createName(Topic type, String value, Topic... scopes) {
    verify.parameterIsNotNull(type);
    verify.parameterIsNotNull(value);
    verify.parameterIsNotNull(scopes);
    Name n = new CName(getParent());
    addRdfStatement(proxy, CREGAN.HASNAME, ((CEntity) n).proxy);
    addRdfStatement(((CEntity) n).proxy, CREGAN.NAMEOF, proxy);
    n.setType(type);
    n.setValue(value);
    for (Topic element : scopes)
      n.addTheme(element);
    return n;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createName(org.tmapi.core.Topic,
   * java.lang.String, java.util.Collection)
   */
  @Override
  public Name createName(Topic type, String value, Collection<Topic> scopes) {
    verify.parameterIsNotNull(scopes);
    Name n = createName(type, value);
    Iterator<Topic> i = scopes.iterator();
    while (i.hasNext()) {
      Topic topic = i.next();
      n.addTheme(topic);
    }
    return n;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Topic[])
   */
  @Override
  public Occurrence createOccurrence(Topic type, String value, Topic... scopes) {
    verify.parameterIsNotNull(type);
    verify.parameterIsNotNull(value);
    verify.parameterIsNotNull(scopes);

    return createOccurrence(type, value, new SesameLocator(XMLSchema.STRING
        .stringValue()), scopes);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, java.util.Collection)
   */
  @Override
  public Occurrence createOccurrence(Topic type, String value,
      Collection<Topic> scopes) {
    if (scopes == null) {
      throw new ModelConstraintException(this,
          "createOccurrence(topic, \"Occurrence\", (Collection<Topic>)null) is illegal");
    }
    return createOccurrence(type, value, new SesameLocator(XMLSchema.STRING
        .stringValue()), scopes);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * org.tmapi.core.Locator, org.tmapi.core.Topic[])
   */
  @Override
  public Occurrence createOccurrence(Topic type, Locator value, Topic... scopes) {
    verify.parameterIsNotNull(type);
    verify.parameterIsNotNull(value);
    verify.parameterIsNotNull(scopes);
    Occurrence o = new COccurrence((CTopicMap) getParent());
    addRdfStatement(proxy, CREGAN.HASOCCURENCE, ((CEntity) o).proxy);
    addRdfStatement(((CEntity) o).proxy, CREGAN.OCCURENCEOF, proxy);
    addRdfStatement(((CEntity) o).proxy, CREGAN.DATATYPE, XMLSchema.ANYURI);
    o.setType(type);
    o.setValue(value);
    for (Topic element : scopes)
      o.addTheme(element);
    return o;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * org.tmapi.core.Locator, java.util.Collection)
   */
  @Override
  public Occurrence createOccurrence(Topic type, Locator value,
      Collection<Topic> scopes) {
    return createOccurrence(type, value.toExternalForm(), new SesameLocator(
        XMLSchema.ANYURI.stringValue()), scopes);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Locator, org.tmapi.core.Topic[])
   */
  @Override
  public Occurrence createOccurrence(Topic type, String value,
      Locator dataType, Topic... scopes) {
    verify.parameterIsNotNull(dataType);
    Occurrence o = new COccurrence((CTopicMap) getParent());
    addRdfStatement(proxy, CREGAN.HASOCCURENCE, ((CEntity) o).proxy);
    addRdfStatement(((CEntity) o).proxy, CREGAN.OCCURENCEOF, proxy);
    o.setType(type);
    o.setValue(value, dataType);
    for (Topic element : scopes)
      o.addTheme(element);
    return o;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#createOccurrence(org.tmapi.core.Topic,
   * java.lang.String, org.tmapi.core.Locator, java.util.Collection)
   */
  @Override
  public Occurrence createOccurrence(Topic type, String value,
      Locator dataType, Collection<Topic> scopes) {
    Occurrence o = createOccurrence(type, value, dataType);
    Topic topic;
    Iterator<Topic> i = scopes.iterator();
    while (i.hasNext()) {
      topic = i.next();
      o.addTheme(topic);
    }
    return o;
  }

  @Override
  public Set<Name> getNames() {
    Set<Name> names = new HashSet<Name>();
    Iterator<Value> proxyUris = getProperties(CREGAN.HASNAME).iterator();
    while (proxyUris.hasNext()) {
      names.add(new CName(sesameTopicMap, ((Resource) proxyUris.next())));
    }
    return names;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getNames(org.tmapi.core.Topic)
   */
  @Override
  public Set<Name> getNames(Topic arg0) {
    if (arg0 == null) {
      throw new IllegalArgumentException("topic.getNames(null) is illegal");
    }
    Iterator<Name> allNames = getNames().iterator();
    Set<Name> result = new HashSet<Name>();
    while (allNames.hasNext()) {
      Name name = allNames.next();
      Topic type = name.getType();
      if (type.equals(arg0)) {
        result.add(name);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getOccurrences()
   */
  @Override
  public Set<Occurrence> getOccurrences() {
    Set<Occurrence> occurrences = new HashSet<Occurrence>();
    Iterator<Value> proxyUris = getProperties(CREGAN.HASOCCURENCE).iterator();
    while (proxyUris.hasNext()) {
      occurrences.add(new COccurrence(sesameTopicMap,
          ((Resource) proxyUris.next())));
    }
    return occurrences;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getOccurrences(org.tmapi.core.Topic)
   */
  @Override
  public Set<Occurrence> getOccurrences(Topic arg0) {
    verify.parameterIsNotIllegalArgument(arg0);
    Iterator<Occurrence> allOccurrences = getOccurrences().iterator();
    Set<Occurrence> result = new HashSet<Occurrence>();
    while (allOccurrences.hasNext()) {
      Occurrence occurrence = allOccurrences.next();
      Topic type = occurrence.getType();
      if (type.equals(arg0)) {
        result.add(occurrence);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getReified()
   */
  @Override
  public Reifiable getReified() {
    Reifiable reifiable = null;
    try {
      Resource uri = (Resource) getFirstValue(CREGAN.ISREIFIEROF);
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(uri, RDF.TYPE, null, true,
              ((CTopicMap) getParent()).base);
      Statement s = statements.next();
      Value result = s.getObject();
      if (result.stringValue().equals(CREGAN.NAME.toString())) {
        reifiable = new CName(sesameTopicMap, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.OCCURRENCE.toString())) {
        reifiable = new COccurrence(sesameTopicMap, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.ASSOCIATION.toString())) {
        reifiable = new CAssociation(sesameTopicMap, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.ROLE.toString())) {
        reifiable = new CRole(sesameTopicMap, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.VARIANT.toString())) {
        reifiable = new CVariant(sesameTopicMap, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.TOPICMAP.toString())) {
        reifiable = getParent();
      }
    } catch (Exception e) {
    }
    return reifiable;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getRolesPlayed()
   */
  @Override
  public Set<Role> getRolesPlayed() {

    Set<Role> roles = new HashSet<Role>();
    Iterator<Value> values = getProperties(CREGAN.HASROLE).iterator();
    while (values.hasNext()) {
      CRole r = new CRole(sesameTopicMap, ((Resource) values.next()));
      roles.add(r);
    }

    return roles;
  }

  @Override
  public Set<Role> getRolesPlayed(Topic topic) {
    verify.parameterIsNotIllegalArgument(topic);
    Set<Role> result = new HashSet<Role>();
    Iterator<Role> roles = getRolesPlayed().iterator();
    while (roles.hasNext()) {
      Role role = roles.next();
      if (role.getType().equals(topic)) {
        result.add(role);
      }
    }
    return result;
  }

  @Override
  public Set<Role> getRolesPlayed(Topic arg0, Topic arg1) {
    verify.parameterIsNotIllegalArgument(arg1);
    Set<Role> result = new HashSet<Role>();
    Iterator<Role> roles = getRolesPlayed(arg0).iterator();
    while (roles.hasNext()) {
      Role role = roles.next();
      Association asso = role.getParent();
      if (asso.getType().equals(arg1)) {
        result.add(role);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getSubjectIdentifiers()
   */
  @Override
  public Set<Locator> getSubjectIdentifiers() {
    Iterator<Value> vals = getProperties(CREGAN.SUBJECTIDETIFIER).iterator();
    Set<Locator> locators = new HashSet<Locator>();
    while (vals.hasNext()) {
      locators.add(sesameTopicMap.createLocator(vals.next().toString()));
    }
    return locators;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getSubjectLocators()
   */
  @Override
  public Set<Locator> getSubjectLocators() {
    Iterator<Value> vals = getProperties(CREGAN.SUBJECTLOCATOR).iterator();
    Set<Locator> locators = new HashSet<Locator>();
    while (vals.hasNext()) {
      locators.add(sesameTopicMap.createLocator(vals.next().toString()));
    }
    return locators;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#getTypes()
   */
  @Override
  public Set<Topic> getTypes() {
    Set<Topic> result = new HashSet<Topic>();
    Iterator<Role> roles = getRolesPlayed(theInstance()).iterator();
    Association as;
    while (roles.hasNext()) {
      as = (roles.next()).getParent();
      if (as.getType().equals(IypeInstanceAssoType()))
        try {
          result.add(as.getRoles(theType()).iterator().next().getPlayer());
        } catch (Exception e) {
        }
    }
    return result;
  }

  public Set<CAssociation> getAssociations() {
    Set<CAssociation> result = new HashSet<CAssociation>();
    Iterator<Role> rs = getRolesPlayed().iterator();
    while (rs.hasNext()) {
      result.add((CAssociation) (rs.next()).getParent());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#mergeIn(org.tmapi.core.Topic)
   */
  @Override
  public void mergeIn(Topic o) {
    if (!equals(o)) {
      if (getReified() != null && !getReified().equals(o.getReified())) {
        throw new ModelConstraintException(this,
            "The topics reify different Topic Maps constructs and cannot be merged");
      }
      Set<CAssociation> oas = ((CTopic) o).getAssociations();
      Iterator<CAssociation> as = getAssociations().iterator();
      while (as.hasNext()) {
        CAssociation thisSesameAssociation = as.next();
        Iterator<CAssociation> oasi = oas.iterator();
        while (oasi.hasNext()) {
          CAssociation otherSesameAssociation = oasi.next();
          if (thisSesameAssociation.mEquals(otherSesameAssociation))
            thisSesameAssociation.mergeIn(otherSesameAssociation);
        }
      }
      Iterator<Occurrence> oi = getOccurrences().iterator();
      Iterator<Occurrence> oio;
      oio = o.getOccurrences().iterator();
      Iterator<Name> ni = getNames().iterator();
      Iterator<Name> nio;
      nio = o.getNames().iterator();
      super.mergeIn(o);
      while (ni.hasNext()) {
        Name n = ni.next();
        while (nio.hasNext()) {
          Name no = nio.next();
          if (n.equals(no))
            ((CName) n).mergeIn(no);
        }
      }

      while (oi.hasNext()) {
        Occurrence oo = oi.next();
        while (oio.hasNext()) {
          Occurrence no = oio.next();
          if (oo.equals(no))
            ((COccurrence) oo).mergeIn(no);
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#removeSubjectIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void removeSubjectIdentifier(Locator arg0) {
    removeValue(CREGAN.SUBJECTIDETIFIER, ((SesameLocator) arg0).locatorURI);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#removeSubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public void removeSubjectLocator(Locator arg0) {
    removeValue(CREGAN.SUBJECTLOCATOR, ((SesameLocator) arg0).locatorURI);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Topic#removeType(org.tmapi.core.Topic)
   */
  @Override
  public void removeType(Topic t) {
    Iterator<Role> rs1 = t.getRolesPlayed(theType()).iterator();
    Iterator<Role> rs2 = getRolesPlayed(theInstance()).iterator();
    while (rs1.hasNext()) {
      Role role1 = rs1.next();
      Association a1 = role1.getParent();
      while (rs2.hasNext()) {
        Role role2 = rs2.next();
        Association a2 = role2.getParent();
        if (a1.equals(a2)) {
          a1.remove();
        }
      }
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
      Topic t = (Topic) o;
      return t.getId().equals(getId());
    } catch (Exception e) {
    }
    return false;
  }

  public boolean mEquals(Object o) {
    Topic t = (Topic) o;
    boolean result = false;
    try {
      if (getReified().equals(t.getReified()))
        result = true;
    } catch (Exception e) {
    }
    if (getItemIdentifiers().removeAll(t.getItemIdentifiers()))
      result = true;
    if (getSubjectIdentifiers().removeAll(t.getSubjectIdentifiers()))
      result = true;
    if (getSubjectLocators().removeAll(t.getSubjectLocators()))
      result = true;
    if (getSubjectIdentifiers().removeAll(t.getItemIdentifiers()))
      result = true;
    if (getItemIdentifiers().removeAll(t.getSubjectIdentifiers()))
      result = true;
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.topicmapslab.sesametm.cregan.utils.SesameEntity#toString()
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    String NEW_LINE = System.getProperty("line.separator");
    result.append(super.toString() + NEW_LINE);
    Iterator<Occurrence> i = getOccurrences().iterator();
    while (i.hasNext()) {
      result.append(i.next() + NEW_LINE);
    }
    return result.toString();
  }

}

/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.core;

import info.aduna.iteration.Iterations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.FeatureNotRecognizedException;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.index.Index;
import org.tmapi.index.LiteralIndex;
import org.tmapi.index.ScopedIndex;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.core.SesameTopicMapSystem;
import de.topicmapslab.sesametm.cregan.index.CLiteralIndex;
import de.topicmapslab.sesametm.cregan.index.CScopedIndex;
import de.topicmapslab.sesametm.cregan.index.CTypeInstanceIndex;
import de.topicmapslab.sesametm.cregan.utils.CEntity;
import de.topicmapslab.sesametm.cregan.utils.OntologyFactory;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.FEATURE;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CTopicMap extends CConstruct implements TopicMap {

  public Locator baseIRI;
  private static int counter = 0;
  private boolean AUTOMERGE;
  private SesameTopicMapSystem tms;

  public CTopicMap(Locator arg0, CTopicMapSystem theTms) {
    super(((SesameLocator) arg0).getUri(), theTms);
    baseIRI = arg0;
    tms = theTms;
    try {
      AUTOMERGE = tms.getFeature(FEATURE.AUTOMERGE);
    } catch (FeatureNotRecognizedException e) {
      e.printStackTrace();
    }
    addRdfStatement(proxy, RDF.TYPE, CREGAN.TOPICMAP);
    OntologyFactory.load(this);
  }

  public boolean isAutomerged() {
    return AUTOMERGE;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createTopic()
   */
  public CTopic createTopic() {
    CTopic topic = new CTopic(this);
    topic.addItemIdentifier(createLocator(base.stringValue()
        + System.currentTimeMillis() + counter++));
    return topic;
  }

  public RepositoryConnection getConnection() {
    return repositoryConnection;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#close()
   */
  @Override
  public void close() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createAssociation(org.tmapi.core.Topic,
   * org.tmapi.core.Topic[])
   */
  @Override
  public Association createAssociation(Topic arg0, Topic... arg1) {
    verify.parameterIsNotNull(arg0);
    Association a = new CAssociation(this);
    try {
      for (Topic element : arg1)
        a.addTheme(element);
    } catch (Exception e) {
      throw new ModelConstraintException(getTopicMap(),
          "Creating an association with scope == null is not allowed");
    }
    a.setType(arg0);
    return a;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createAssociation(org.tmapi.core.Topic,
   * java.util.Collection)
   */
  @Override
  public Association createAssociation(Topic arg0, Collection<Topic> arg1) {
    verify.parameterIsNotNull(arg0);

    Topic topic;
    Association a = new CAssociation(this);
    a.setType(arg0);
    try {
      Iterator<Topic> i = arg1.iterator();
      while (i.hasNext()) {
        topic = i.next();
        a.addTheme(topic);
      }
    } catch (Exception e) {
      throw new ModelConstraintException(getTopicMap(),
          "Creating an association with scope == (Collection) null is not allowed");
    }
    return a;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#createLocator(java.lang.String)
   */
  @Override
  public Locator createLocator(String arg0) {
    return tms.createLocator(arg0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#createTopicByItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public Topic createTopicByItemIdentifier(Locator arg0) {
    verify.parameterIsNotNull(arg0);
    Topic t;
    Construct existing = getTopicMap().getConstructByItemIdentifier(arg0);
    if (existing == null)
      existing = getTopicMap().getTopicBySubjectIdentifier(arg0);
    if (existing == null) {
      t = new CTopic(this);
    } else {
      t = (Topic) existing;
    }
    t.addItemIdentifier(arg0);
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#createTopicBySubjectIdentifier(org.tmapi.core.Locator
   * )
   */
  @Override
  public Topic createTopicBySubjectIdentifier(Locator arg0) {
    verify.parameterIsNotNull(arg0);
    Topic t;
    Construct existing = getTopicMap().getConstructByItemIdentifier(arg0);
    if (existing == null)
      existing = getTopicMap().getTopicBySubjectIdentifier(arg0);
    if (existing == null) {
      t = new CTopic(this);
    } else {
      t = (Topic) existing;
    }
    t.addSubjectIdentifier(arg0);
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#createTopicBySubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public Topic createTopicBySubjectLocator(Locator arg0) {
    verify.parameterIsNotNull(arg0);
    Topic t = getTopicBySubjectLocator(arg0);
    if (t == null) {
      t = new CTopic(this);
      t.addSubjectLocator(arg0);
    }
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getAssociations()
   */
  @Override
  public Set<Association> getAssociations() {
    Set<Association> result = new HashSet<Association>();
    RepositoryResult<Statement> statements;
    try {
      statements = repositoryConnection.getStatements(null, RDF.TYPE,
          CREGAN.ASSOCIATION, true, base);
      while (statements.hasNext()) {
        Statement st = statements.next();
        result.add(new CAssociation(this, st.getSubject()));
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getConstructById(java.lang.String)
   */
  @Override
  public Construct getConstructById(String arg0) {
    Value result = null;
    Construct construct = null;
    RepositoryResult<Statement> statements = null;
    try {
      if (arg0.contains(":")) {
        statements = repositoryConnection
            .getStatements(repositoryConnection.getValueFactory().createURI(
                arg0), RDF.TYPE, null, true, this.base);
      } else {
        statements = repositoryConnection.getStatements(repositoryConnection
            .getValueFactory().createBNode(arg0), RDF.TYPE, null, true,
            this.base);
      }
      Statement s = statements.next();
      result = s.getObject();
      if (result.stringValue().equals(CREGAN.NAME.toString())) {
        construct = new CName(this, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.TOPICMAP.toString())) {
        construct = this;
      }
      if (result.stringValue().equals(CREGAN.OCCURRENCE.toString())) {
        construct = new COccurrence(this, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.TOPIC.toString())) {
        construct = new CTopic(this, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.ASSOCIATION.toString())) {
        construct = new CAssociation(this, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.ROLE.toString())) {
        construct = new CRole(this, s.getSubject());
      }
      if (result.stringValue().equals(CREGAN.VARIANT.toString())) {
        construct = new CVariant(this, s.getSubject());
      }
    } catch (RepositoryException e) {
    }
    return construct;
  }

  private Construct getConstrubtbySomething(URI loc, URI key) throws Exception {
    Value result = null;
    Construct construct = null;
    RepositoryResult<Statement> statements = repositoryConnection
        .getStatements(null, key, loc, true, this.base);
    statements = repositoryConnection.getStatements(statements.next()
        .getSubject(), RDF.TYPE, null, true, this.base);
    Statement s = statements.next();
    result = s.getObject();

    if (result.stringValue().equals(CREGAN.NAME.toString())) {
      construct = new CName(this, s.getSubject());
    }
    if (result.stringValue().equals(CREGAN.TOPICMAP.toString())) {
      construct = this;
    }
    if (result.stringValue().equals(CREGAN.OCCURRENCE.toString())) {
      construct = new COccurrence(this, s.getSubject());
    }
    if (result.stringValue().equals(CREGAN.TOPIC.toString())) {
      construct = new CTopic(this, s.getSubject());
    }
    if (result.stringValue().equals(CREGAN.ASSOCIATION.toString())) {
      construct = new CAssociation(this, s.getSubject());
    }
    if (result.stringValue().equals(CREGAN.ROLE.toString())) {
      construct = new CRole(this, s.getSubject());
    }
    if (result.stringValue().equals(CREGAN.VARIANT.toString())) {
      construct = new CVariant(this, s.getSubject());
    }
    return construct;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#getConstructByItemIdentifier(org.tmapi.core.Locator
   * )
   */
  @Override
  public Construct getConstructByItemIdentifier(Locator l) {
    Construct result = null;
    try {
      result = getConstrubtbySomething(((SesameLocator) l).getUri(),
          CREGAN.ITEMIDENTIFIER);
    } catch (Exception e) {
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getIndex(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <I extends Index> I getIndex(Class<I> arg0) {
    if (arg0 == TypeInstanceIndex.class) {
      return (I) new CTypeInstanceIndex(this);
    }
    if (arg0 == ScopedIndex.class) {
      return (I) new CScopedIndex(this);
    }
    if (arg0 == LiteralIndex.class) {
      return (I) new CLiteralIndex(this);
    }
    throw new UnsupportedOperationException(
        "Exception expected for an unknown index");
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.topicmapslab.sesametm.cregan.core.SesameConstruct#getParent()
   */
  @Override
  public Construct getParent() {
    return null;
  }


  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#getTopicBySubjectIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public Topic getTopicBySubjectIdentifier(Locator arg0) {
    Topic t = new CTopic(this, CREGAN.SUBJECTIDETIFIER, arg0);
    if (((CEntity) t).proxy == null) {
      t = null;
    }
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.tmapi.core.TopicMap#getTopicBySubjectLocator(org.tmapi.core.Locator)
   */
  @Override
  public Topic getTopicBySubjectLocator(Locator arg0) {
    Topic t = new CTopic(this, CREGAN.SUBJECTLOCATOR, arg0);
    if (((CEntity) t).proxy == null) {
      t = null;
    }
    return t;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#getTopics()
   */
  @Override
  public Set<Topic> getTopics() {
    Set<Topic> result = new HashSet<Topic>();
    RepositoryResult<Statement> statements;
    try {
      statements = repositoryConnection.getStatements(null, RDF.TYPE,
          CREGAN.TOPIC, true, base);
      while (statements.hasNext()) {
        Statement st = statements.next();
        result.add(new CTopic(this, st.getSubject()));
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMap#mergeIn(org.tmapi.core.TopicMap)
   */
  @Override
  public void mergeIn(TopicMap other) {
    URI obase = ((CTopicMap) other).base;
    try {
      RepositoryResult<Statement> oStatements = repositoryConnection
          .getStatements(null, null, null, true, obase);
      repositoryConnection.add(oStatements, base);

    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    Set<Topic> allTopics = getTopics();
    Iterator<Topic> OtherTopicsI = other.getTopics().iterator();
    while (OtherTopicsI.hasNext()) {
      Iterator<Topic> allTopicsI = allTopics.iterator();

      CTopic otherTopic = (CTopic) OtherTopicsI.next();
      while (allTopicsI.hasNext()) {
        CTopic topic = (CTopic) allTopicsI.next();
        if (topic.mEquals(otherTopic)) {
          CTopic tt = new CTopic(this, otherTopic.proxy);
          topic.mergeIn(tt);
        }
      }

    }
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

  @Override
  public String getId() {
    return base.stringValue();
  }

  @Override
  public Set<Locator> getItemIdentifiers() {
    Set<Locator> result = new HashSet<Locator>();
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(base, CREGAN.ITEMIDENTIFIER, null, true);
      while (statements.hasNext()) {
        result.add(createLocator(statements.next().getObject().stringValue()));
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.topicmapslab.sesametm.cregan.core.SesameConstruct#getTopicMap()
   */
  @Override
  public TopicMap getTopicMap() {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.topicmapslab.sesametm.cregan.core.SesameConstruct#remove()
   */
  @Override
  public void remove() {
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(base, null, null, true);
      List<Statement> aboutAlice = Iterations.addAll(statements,
          new ArrayList<Statement>());
      repositoryConnection.remove(aboutAlice);
      statements = repositoryConnection.getStatements(null, null, base, true);
      aboutAlice = Iterations.addAll(statements, new ArrayList<Statement>());
      repositoryConnection.remove(aboutAlice);
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    // Not a real hashCode, however iterating over all components much too
    // expensive.
    return base.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    TopicMap t = (TopicMap) o;
    return t.getId().equals(getId());
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
    Iterator<Topic> topicIterator = getTopics().iterator();
    while (topicIterator.hasNext()) {
      Topic t = topicIterator.next();
      result.append(t.toString() + NEW_LINE);
    }
    Iterator<Association> associationIterator = getAssociations().iterator();
    while (associationIterator.hasNext()) {
      Association a = associationIterator.next();
      result.append(a.toString() + NEW_LINE);
    }
    return result.toString();
  }

  @Override
  public Locator getLocator() {
    return baseIRI;
  }

}

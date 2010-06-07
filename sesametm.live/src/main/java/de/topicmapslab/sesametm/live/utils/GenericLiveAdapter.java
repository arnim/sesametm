/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.tmapi.core.Locator;

import de.topicmapslab.sesametm.live.core.LTopicMapSystem;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class GenericLiveAdapter {

  protected LTopicMapSystem tms;
  protected RepositoryConnection con;
  protected MappingHandler mapping;

  protected URI reference;
  private ValueFactory valueFactory;

  public GenericLiveAdapter(Locator l, LTopicMapSystem theTms) {
    this(theTms.getConnection().getValueFactory().createURI(l.getReference()),
        theTms);
  }

  public GenericLiveAdapter(URI u, LTopicMapSystem theTms) {
    tms = theTms;
    con = tms.getConnection();
    mapping = tms.getMapping();
    reference = u;
    valueFactory = con.getValueFactory();
  }

  public MappingHandler getMappingHandler() {
    return mapping;
  }

  public RepositoryConnection getConnection() {
    return con;
  }

  public URI createURI(String s) {
    return valueFactory.createURI(s);
  }

  /**
   * Tests if a RDF Statement exists.
   * 
   * @param subject
   * @param predicate
   * @param object
   * @return <code>true</code> if Statement exists else <code>false</code>.
   */
  protected boolean statementExists(Resource subject, URI predicate,
      Value object) {
    try {
      if (con.hasStatement(subject, predicate, object, true)) {
        return true;
      } else {
        return false;
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Returns the Set of Subjects, as Set<Resource>, that has the given Predicate
   * and the given Object as URI
   * 
   * @param predicate
   *          a URI as Key
   * @param object
   *          a URI as Value
   * @return a Set of Subjects (as Set<Resource>)
   */
  protected Set<Resource> getSubjects(URI predicate, URI object) {
    Set<Resource> result = new HashSet<Resource>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(null,
          predicate, object, true);
      while (statements.hasNext()) {
        result.add(statements.next().getSubject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  protected Set<LValue> getValues(Resource subject, URI predicate) {
    Set<LValue> myValues = new HashSet<LValue>();
    Value v;
    Iterator<Value> valIterator = getObjects(subject, predicate).iterator();
    while (valIterator.hasNext()) {
      v = valIterator.next();
      myValues.add(new LValue(v));
    }
    return myValues;
  }

  /**
   * Returns the Set of Objects, as Set<Resource>, that has the given Predicate
   * and the given Subject as URI
   * 
   * @param subject
   *          a URI as Key
   * @param object
   *          a URI as Value
   * @return a Set of Objects (as Set<Resource>)
   */
  protected Set<Value> getObjects(Resource subject, URI predicate) {
    Set<Value> result = new HashSet<Value>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(subject,
          predicate, null, true);
      while (statements.hasNext()) {
        result.add(statements.next().getObject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

}

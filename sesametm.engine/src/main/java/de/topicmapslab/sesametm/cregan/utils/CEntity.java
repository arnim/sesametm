/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.utils;

import info.aduna.iteration.Iterations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.tmapi.core.Locator;
import org.tmapi.core.TopicInUseException;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.cregan.core.CTopic;
import de.topicmapslab.sesametm.cregan.core.CTopicMap;
import de.topicmapslab.sesametm.cregan.core.CTopicMapSystem;

/**
 * The abstract class SesameEntity handles the connection between
 * org.tmapi.core.Construct's and the Sesame API This class widely plays the
 * role of a Proxy as understood by the Topic MPas Reference Model. A Proxy is a
 * finite Set of Properties. A Property consists of a Key (given as URI) and a
 * Set of values.
 * 
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */
public abstract class CEntity {

  protected RepositoryConnection repositoryConnection;
  public Resource proxy;
  protected ValueFactory valueFactory;
  protected CTopicMap sesameTopicMap;
  public URI base;

  /**
   * Constructor for the creation of new Entities (Proxy's)
   * 
   * @param tm
   *          the SesameTopicMap this Entity will belong too
   */
  public CEntity(CTopicMap tm) {
    sesameTopicMap = tm;
    repositoryConnection = sesameTopicMap.getConnection();
    valueFactory = repositoryConnection.getValueFactory();
    proxy = valueFactory.createBNode();
    base = sesameTopicMap.base;
  }

  /**
   * Constructor for the retrieves of already existing Entities (Proxy's)
   * 
   * @param tm
   *          the SesameTopicMap this Entity will belong too
   * @param node
   *          Resource that is the Label of the Proxy
   */
  public CEntity(CTopicMap tm, Resource node) {
    sesameTopicMap = tm;
    repositoryConnection = sesameTopicMap.getConnection();
    valueFactory = repositoryConnection.getValueFactory();
    proxy = node;
    base = sesameTopicMap.base;
  }

  /**
   * Constructor for retrieving of already existing Entities (Proxy's)
   * 
   * @param tm
   *          the SesameTopicMap this Entity will belong too
   * @param key
   *          the URI that acts as a key in the Proxy
   * @param l
   *          the Locater that acts as a identifier for the Topic
   */
  public CEntity(CTopicMap tm, URI key, Locator l) {
    sesameTopicMap = tm;
    repositoryConnection = sesameTopicMap.getConnection();
    RepositoryResult<Statement> statements;
    valueFactory = repositoryConnection.getValueFactory();
    base = sesameTopicMap.base;
    try {
      statements = repositoryConnection.getStatements(null, key,
          ((SesameLocator) l).getUri(), true, sesameTopicMap.base);
      proxy = statements.next().getSubject();
    } catch (Exception e) {
    }
  }

  /**
   * Constructor for TopicMpas
   * 
   * @param uri
   * @param tms
   */
  public CEntity(URI uri, CTopicMapSystem tms) {
    sesameTopicMap = (CTopicMap) this;
    base = uri;
    proxy = base;
    repositoryConnection = tms.getConnection();
  }

  /**
   * Adds a Property to the Entity
   * 
   * @param key
   *          a arbitrary Key given as URI
   * @param value
   *          a arbitrary Value given as String
   */
  protected void addProperty(URI key, String value) {
    addRdfStatement(proxy, key, valueFactory.createLiteral(value));
  }

  /**
   * Adds a Property to the Entity
   * 
   * @param key
   *          a arbitrary Key given as URI
   * @param value
   *          a arbitrary Value given as Resource
   */
  protected void addPredicate(URI key, Resource value) {
    addRdfStatement(proxy, key, value);
  }

  /**
   * Returns the Set of values for a given Key of this Entity
   * 
   * @param key
   *          a URI as Key
   * @return a Set<Value> for the given Key
   */
  protected Set<Value> getProperties(URI key) {
    Set<Value> result = new HashSet<Value>();
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(proxy, key, null, true, base);
      while (statements.hasNext()) {
        result.add(statements.next().getObject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }

    return result;
  }

  /**
   * Returns the Set of Labels, as Set<Resource>, that has the given Key and the
   * given Value as URI
   * 
   * @param key
   *          a URI as Key
   * @param value
   *          a URI as Value
   * @return a Set of Labels (as Set<Resource>)
   */
  protected Set<Resource> getLabels(URI key, URI value) {
    Set<Resource> result = new HashSet<Resource>();
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(null, key, value, true, base);
      while (statements.hasNext()) {
        Resource uri = statements.next().getSubject();
        result.add(uri);
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Adds a Statement to the Sesame Storage in the COntext the current Topic Map
   * uses
   * 
   * @param label
   *          a Resource the Statement is made about
   * @param key
   *          a URI acting as Predicate
   * @param value
   *          the Object
   */
  protected void addRdfStatement(Resource label, URI key, Value value) {
    try {
      repositoryConnection.add(label, key, value, base);
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the first Value for the provided key if the Key does not exist null
   * is returned
   * 
   * @param key
   *          a URI
   * @return one Value or null
   */
  protected Value getFirstValue(URI key) {
    Value result = null;
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(proxy, key, null, true, base);
      result = statements.next().getObject();
    } catch (RepositoryException e) {
    }
    return result;
  }

  /**
   * Checks if a Label exists with the provided Property
   * 
   * @param key
   *          a URI as Key of the Property
   * @param value
   *          a Value as Value of the Property
   * @return
   */
  protected boolean labelExists(URI key, Resource value) {
    boolean result = false;
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(null, key, value, true, base);
      result = statements.hasNext();
    } catch (RepositoryException e) {
    }
    return result;
  }

  /**
   * Removes the Property with the given Key from the Proxy
   * 
   * @param key
   *          a Key for of the Proxy as URI
   */
  protected void removeProperty(URI key) {
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(proxy, key, null, true, base);
      List<Statement> toRemove = Iterations.addAll(statements,
          new ArrayList<Statement>());
      repositoryConnection.remove(toRemove);
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes a given Value for a given Key
   * 
   * @param key
   *          a URI as Key
   * @param value
   *          a String as Value
   */
  protected void removeValue(URI key, String value) {
    removeValue(key, valueFactory.createURI(value));
  }

  /**
   * Removes a given Value for a given Key
   * 
   * @param key
   *          a URI as Key
   * @param value
   *          a Value
   */
  protected void removeValue(URI key, Value value) {
    try {
      repositoryConnection.remove(repositoryConnection.getStatements(proxy,
          key, value, true, base));
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes a foreign Reference
   * 
   * @param key
   *          a
   * @param label
   */
  protected void removeForeignValue(URI key, Resource label) {
    try {
      repositoryConnection.remove(repositoryConnection.getStatements(null, key,
          label, true, base));
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generic mergein function for {@link CEntity}
   * 
   * @param o
   *          the other {@link CEntity} instance
   */
  public void mergeIn(Object o) {
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(((CEntity) o).proxy, null, null, true, base);
      while (statements.hasNext()) {
        Statement s = statements.next();
        repositoryConnection.add(proxy, s.getPredicate(), s.getObject(), base);
      }
      statements = repositoryConnection.getStatements(null, null,
          ((CEntity) o).proxy, true, base);
      while (statements.hasNext()) {
        Statement s = statements.next();
        repositoryConnection.add(s.getSubject(), s.getPredicate(), proxy, base);
      }
      try {

        try {
          ((CTopic) o).mergeRemove();
        } catch (Exception e) {
          ((CEntity) o).remove();
        }

      } catch (TopicInUseException e) {
        ((CTopic) o).mergeRemove();
      }

    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    String NEW_LINE = System.getProperty("line.separator");
    StringBuilder theType = new StringBuilder();
    Resource P;
    Value O;
    try {
      RepositoryResult<Statement> statements = repositoryConnection
          .getStatements(proxy, null, null, true, base);
      while (statements.hasNext()) {
        Statement st = statements.next();
        P = st.getPredicate();
        O = st.getObject();
        if (RDF.TYPE.toString().equals(P.toString())) {
          theType.append((proxy).stringValue() + " isa "
              + ((URI) O).getLocalName().toUpperCase().toString() + NEW_LINE);
        } else {
          result.append(((URI) P).getLocalName().toString() + ": "
              + O.toString() + NEW_LINE);
        }
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return theType.toString() + result.toString();
  }

  /**
   * Removes this {@link CEntity} and all the references to it
   */
  public void remove() {
    try {
      repositoryConnection.remove(repositoryConnection.getStatements(proxy,
          null, null, true, base));
      repositoryConnection.remove(repositoryConnection.getStatements(null,
          null, proxy, true, base));
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

}

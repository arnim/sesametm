/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.openrdf.model.Statement;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;
import org.tmapi.core.Locator;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapExistsException;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.core.SesameTopicMapSystem;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.PROPERTY;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CTopicMapSystem extends SesameTopicMapSystem {

  private RepositoryConnection con;

  public CTopicMapSystem(HashMap<String, Object> properties,
      HashMap<String, Boolean> features) {
    super(properties, features);
    try {
    	con = (RepositoryConnection) getProperty(PROPERTY.CONNECTION);
    	if (con == null)
    		throw new Exception();
	} catch (Exception e) {
	    Repository myRepository = new SailRepository(new MemoryStore());
	    try {
	      myRepository.initialize();
	      con = myRepository.getConnection();
	      setConnection(con);
	    } catch (Exception e1) {
	      e.printStackTrace();
	    }
	}
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMapSystem#close()
   */
  @Override
  public void close() {
    try {
      con.close();
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMapSystem#createLocator(java.lang.String)
   */
  @Override
  public Locator createLocator(String arg0) {
    SesameLocator sl = new SesameLocator(arg0);
//    try {
//      con.add(sl.locatorURI, RDF.TYPE, CREGAN.LOCATOR);
//    } catch (RepositoryException e) {
//      e.printStackTrace();
//    }
    return sl;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMapSystem#createTopicMap(org.tmapi.core.Locator)
   */
  @Override
  public TopicMap createTopicMap(Locator arg0) throws TopicMapExistsException {
    try {
      RepositoryResult<Statement> statements = con.getStatements(
          ((SesameLocator) arg0).locatorURI, RDF.TYPE, CREGAN.TOPICMAP, true);
      if (statements.hasNext()) {
        throw new TopicMapExistsException(arg0.toString());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    topicMpap = new CTopicMap(arg0, this);
    return topicMpap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMapSystem#createTopicMap(java.lang.String)
   */
  @Override
  public TopicMap createTopicMap(String arg0) throws TopicMapExistsException {
    return createTopicMap(new SesameLocator(arg0));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.TopicMapSystem#getLocators()
   */
  @Override
  public Set<Locator> getLocators() {
    Set<Locator> result = new HashSet<Locator>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(null,
          RDF.TYPE, CREGAN.TOPICMAP, true);
      while (statements.hasNext()) {
        Locator uri = new SesameLocator(statements.next().getSubject()
            .stringValue());
        result.add(uri);
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TopicMap getTopicMap(Locator locator) {
	  RepositoryResult<Statement> statements;
	  try {
		statements = con.getStatements(
		          ((SesameLocator) locator).locatorURI, RDF.TYPE, CREGAN.TOPICMAP, true);
	  if (statements.hasNext()) 
		  return new CTopicMap(locator, this);;
	  } catch (RepositoryException e) {}
	return null;
  }
  

  public RepositoryConnection getConnection() {
    return con;
  }

}

/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.index;

import java.util.HashSet;
import java.util.Set;

import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.tmapi.core.Locator;
import org.tmapi.core.Topic;
import org.tmapi.index.Index;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.cregan.core.CTopic;
import de.topicmapslab.sesametm.cregan.core.CTopicMap;
import de.topicmapslab.sesametm.utils.SesameTMIndex;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CIndex extends SesameTMIndex implements Index {

  CTopicMap sesameTopicMap;

  public CIndex(CTopicMap tm) {
    sesameTopicMap = tm;
    base = sesameTopicMap.base;
    con = sesameTopicMap.getConnection();
  }

  protected Set<Resource> getNodes(URI type) {
    Set<Resource> result = new HashSet<Resource>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(null,
          RDF.TYPE, type, true, base);
      while (statements.hasNext()) {
        result.add(statements.next().getSubject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  protected Set<Resource> getNodes(Topic type) {
    CTopic t = (CTopic) type;
    Set<Resource> result = new HashSet<Resource>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(null,
          TMDM.TYPE, t.proxy, true, base);
      while (statements.hasNext()) {
        result.add(statements.next().getSubject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  protected Set<Resource> getNodes(String value) {
    Literal theValue = con.getValueFactory().createLiteral(value);
    Set<Resource> result = new HashSet<Resource>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(null,
          CREGAN.VALUE, theValue, true, base);
      while (statements.hasNext()) {
        result.add(statements.next().getSubject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  protected Set<Resource> getNodes(Locator value) {
    URI theValue = ((SesameLocator) value).getUri();
    Set<Resource> result = new HashSet<Resource>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(null,
          CREGAN.VALUE, theValue, true, base);
      while (statements.hasNext()) {
        result.add(statements.next().getSubject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

  protected Set<Resource> getNodesbyTheme(Topic type) {
    CTopic t = (CTopic) type;
    Set<Resource> result = new HashSet<Resource>();
    try {
      RepositoryResult<Statement> statements = con.getStatements(null,
          CREGAN.SCOPE, t.proxy, true, base);
      while (statements.hasNext()) {
        result.add(statements.next().getSubject());
      }
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
    return result;
  }

}

/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.tmapi.index.Index;

import de.topicmapslab.sesametm.live.core.SparqlTopicMap;
import de.topicmapslab.sesametm.live.utils.MappingHandler;
import de.topicmapslab.sesametm.utils.SesameTMIndex;;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LIndex extends SesameTMIndex implements Index {

  SparqlTopicMap topicMap;
  private RepositoryConnection mapingConn;
  public MappingHandler mapping;

  public LIndex(SparqlTopicMap tm) {
    topicMap = tm;
    con = topicMap.getConnection();
    mapping = topicMap.getMappingHandler();
    mapingConn = mapping.getRepositoryConnection();
  }

  public GraphQueryResult runSparqlConstructQueryOnRepository(String string) {
    try {
      GraphQuery query = con.prepareGraphQuery(QueryLanguage.SPARQL, string);
      return query.evaluate();
    } catch (OpenRDFException e) {
    }
    return null;
  }

  public boolean repositoryHasStatement(Resource s, URI p, Value o) {
    try {
      return con.hasStatement(s, p, o, true, (Resource) null);
    } catch (RepositoryException e) {
      return false;
    }
  }

  public GraphQueryResult runSparqlConstructQueryOnVocabulary(String string) {
    try {
      GraphQuery query = mapingConn.prepareGraphQuery(QueryLanguage.SPARQL,
          string);
      return query.evaluate();
    } catch (OpenRDFException e) {
    }
    return null;
  }

}

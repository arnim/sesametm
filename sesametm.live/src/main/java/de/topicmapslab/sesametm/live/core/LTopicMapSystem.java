/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.tmapi.core.Locator;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapExistsException;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.core.SesameTopicMapSystem;
import de.topicmapslab.sesametm.live.utils.MappingHandler;
import de.topicmapslab.sesametm.utils.SesameTMException;
import de.topicmapslab.sesametm.vocabularies.PROPERTY;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LTopicMapSystem extends SesameTopicMapSystem {

  MappingHandler mapping;
  RepositoryConnection con;

  public LTopicMapSystem(HashMap<String, Object> properties,
      HashMap<String, Boolean> features) {
    super(properties, features);
    try {
      if (getProperty(PROPERTY.SPARQLENDPOINT) != null) {
        Repository repro = new SPARQLRepository(
            (String) getProperty(PROPERTY.SPARQLENDPOINT));
        con = repro.getConnection();
      } else {
        con = (RepositoryConnection) getProperty(PROPERTY.CONNECTION);
      }
      mapping = new MappingHandler(
          (InputStream) getProperty(PROPERTY.MAPPINGVOCAB));
    } catch (Exception e) {
      throw new SesameTMException(e);
    }
  }

  @Override
  public void close() {
    try {
      con.close();
    } catch (RepositoryException e) {
    }
  }

  @Override
  public Locator createLocator(String reference) {
    return new SesameLocator(reference);
  }

  @Override
  public TopicMap createTopicMap(Locator iri) throws TopicMapExistsException {
    return new SparqlTopicMap(iri, this);
  }

  @Override
  public TopicMap createTopicMap(String iri) throws TopicMapExistsException {
    return createTopicMap(createLocator(iri));
  }

  @Override
  public Set<Locator> getLocators() {
    // to think about
    return new HashSet<Locator>();
  }

  public MappingHandler getMapping() {
    return mapping;
  }

  public RepositoryConnection getConnection() {
    return con;
  }

}

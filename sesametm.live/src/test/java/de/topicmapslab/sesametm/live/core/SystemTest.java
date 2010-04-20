/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.openrdf.rio.RDFFormat;
import org.tmapi.core.Locator;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystemFactory;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.core.LTopicMapSystem;
import de.topicmapslab.sesametm.vocabularies.PROPERTY;
import de.topicmapslab.sesametm.live.testn3content.TestCotentAccessor;

public class SystemTest extends TestCase {
  
  private TestCotentAccessor contentAccesor;
  private String sesameServer = "http://localhost:8080/openrdf-sesame";
  private RepositoryConnection con;
  private String repoID = "test";
  private String base = sesameServer;
  private String testRTM = "indexTest.mapping.n3";
  private String testData = "indexTest.n3";
  private String SparqlEndpoint = sesameServer + "/repositories/" + repoID;
  private LTopicMapSystem _sys;
  private Locator _defaultLocator;
  private TopicMap _tm;

  
  public SystemTest() throws Exception {
    contentAccesor = new TestCotentAccessor();
    _defaultLocator = new SesameLocator(base);
  }

  @Test
  public final void testSPARQLENDPOINT() throws Exception {
    TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
    factory.setProperty(PROPERTY.READONLY, true);
    assertEquals(true, factory.getProperty(PROPERTY.READONLY));
    factory.setProperty(PROPERTY.MAPPINGVOCAB, contentAccesor
        .convertStringToInputStream(testRTM));
    factory.setProperty(PROPERTY.SPARQLENDPOINT, SparqlEndpoint);
    _sys = (LTopicMapSystem) factory.newTopicMapSystem();
    _tm = _sys.createTopicMap(_defaultLocator);
    loadToEndpoint(sesameServer, testData);
    assertEquals(15, _tm.getTopics().size());
  }
  
  @Test
  public final void testCONNECTION() throws Exception {
    TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
    factory.setProperty(PROPERTY.READONLY, true);
    factory.setProperty(PROPERTY.MAPPINGVOCAB, contentAccesor
        .convertStringToInputStream(testRTM));
    factory.setProperty(PROPERTY.CONNECTION, new SPARQLRepository(SparqlEndpoint).getConnection());
    _sys = (LTopicMapSystem) factory.newTopicMapSystem();
    _tm = _sys.createTopicMap(_defaultLocator);
    loadToEndpoint(sesameServer, testData);
    assertEquals(15, _tm.getTopics().size());
  }




  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  private void loadToEndpoint(String repositoryID, String filename)
      throws Exception {
    InputStream is = null;
    is = contentAccesor.convertStringToInputStream(filename);
    Repository myRepository = new HTTPRepository(sesameServer, repoID);
    myRepository.initialize();
    con = myRepository.getConnection();
    con.clear();
    con.add(is, base, RDFFormat.N3);
  }

}

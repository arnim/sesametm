/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live;

import java.io.InputStream;

import junit.framework.TestCase;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.memory.MemoryStore;
import org.tmapi.core.Locator;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystemFactory;

import de.topicmapslab.sesametm.live.core.LTopicMapSystem;
import de.topicmapslab.sesametm.vocabularies.PROPERTY;
import de.topicmapslab.sesametm.live.testn3content.TestCotentAccessor;

public class LTestCase extends TestCase {

	String testData;
	String testRTM;
	private TestCotentAccessor contentAccesor;
	private final String CONNECTION = "de.topicmapslab.sesametm.vocabularies.connection";

	public LTestCase(String data, String rtm) {
		testData = data + ".n3";
		testRTM = rtm + ".n3";
		contentAccesor = new TestCotentAccessor();
	}

	protected LTopicMapSystem _sys;
	protected Locator _defaultLocator;
	protected TopicMap _tm;
	String sesameServer = "http://localhost:8080/openrdf-sesame";
	String repoID = "test";
	String SparqlEndpoint = sesameServer + "/repositories/" + repoID;
	RepositoryConnection con;
	String base = sesameServer;

	@Override
	protected void setUp() throws Exception {
		TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
		factory.setProperty(PROPERTY.MAPPINGVOCAB, contentAccesor
				.convertStringToInputStream(testRTM));

		Repository myRepository = new SailRepository(new MemoryStore());
		myRepository.initialize();
		con = myRepository.getConnection();

		// factory.setProperty(PROPERTY.SPARQLENDPOINT, SparqlEndpoint);
		factory.setProperty(PROPERTY.CONNECTION, con);
		_sys = (LTopicMapSystem) factory.newTopicMapSystem();
		_defaultLocator = _sys.createLocator(base);
		_tm = _sys.createTopicMap(_defaultLocator);
//		loadToEndpoint(sesameServer, testData);
		loadToEndpoint(con, testData);

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		_sys.close();
//		con.clear();
//		con.close();
	}

	private void loadToEndpoint(RepositoryConnection con, String filename) throws Exception {
		InputStream is = null;
		is = contentAccesor.convertStringToInputStream(filename);
		con.clear();
		con.add(is, base, RDFFormat.N3);
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

/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.utils;

import org.openrdf.model.Resource;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.tmapi.core.TMAPIRuntimeException;

import de.topicmapslab.sesametm.cregan.core.CTopicMap;
import de.topicmapslab.sesametm.vocabularies.CREGAN;
import de.topicmapslab.sesametm.vocabularies.TMDM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class OntologyFactory {

  public static void load(CTopicMap tm) {

    RepositoryConnection con = tm.getConnection();
    ValueFactory vf = con.getValueFactory();
    Resource about;

    try {

      about = vf.createBNode();
      con.add(about, RDF.TYPE, TMDM.TOPICTYPE, tm.base);
      con.add(about, CREGAN.SUBJECTIDETIFIER, TMDM.TYPE, tm.base);

      about = vf.createBNode();
      con.add(about, RDF.TYPE, TMDM.TOPICTYPE, tm.base);
      con.add(about, CREGAN.SUBJECTIDETIFIER, TMDM.INSTANCE, tm.base);

      about = vf.createBNode();
      con.add(about, RDF.TYPE, TMDM.TOPICTYPE, tm.base);
      con.add(about, CREGAN.SUBJECTIDETIFIER, TMDM.TYPEINSTANCEASSOTYPE, tm.base);

      about = vf.createBNode();
      con.add(about, RDF.TYPE, TMDM.TOPICTYPE, tm.base);
      con.add(about, CREGAN.SUBJECTIDETIFIER, TMDM.UCS, tm.base);

      about = vf.createBNode();
      con.add(about, RDF.TYPE, TMDM.TOPICTYPE, tm.base);
      con.add(about, CREGAN.SUBJECTIDETIFIER, TMDM.TOPICNAME, tm.base);

    } catch (RepositoryException e) {
      throw new TMAPIRuntimeException(e.getMessage(), e.getCause());
    }
  }
}

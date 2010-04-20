/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.vocabularies;

/**
 * The Properties supported by the engine.
 * 
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class PROPERTY {

  public static final String NAMESPACE = "de.topicmapslab.sesametm.vocabularies.";

  public final static String READONLY;

  public final static String MAPPINGVOCAB;

  public final static String SPARQLENDPOINT;

  public final static String CONNECTION;

  static {

    READONLY = NAMESPACE + "readonly";

    MAPPINGVOCAB = NAMESPACE + "mapping";

    SPARQLENDPOINT = NAMESPACE + "spqrqlenpoint";

    CONNECTION = NAMESPACE + "connection";

  }

}

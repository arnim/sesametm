/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.vocabularies;

import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;

/**
 * Implements an RDF vocabulary for describing mappings from RDF schemas to
 * topic maps as a {@link URI} set.
 * 
 * Further documentation can be found at http://psi.ontopia.net/rdf2tm/
 * 
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class RTM {

  public static final String NAMESPACE = "http://psi.ontopia.net/rtm/#";

  public final static URI CONSTRUCT;

  public final static URI BASENAME;

  public final static URI NAME;

  public final static URI OCCURENCE;

  public final static URI ASSOCIATION;

  public final static URI SOURCELOCATOR;

  public final static URI SUBJECTIDENTIFIER;

  public final static URI SUBJECLOCATOR;

  public final static URI INSTANCEOF;

  public final static URI MAPSTO;

  public final static URI TYPE;

  public final static URI INSCOPE;

  public final static URI SUBJECTROLE;

  public final static URI OBJECTROLE;

  static {

    ValueFactory factory = ValueFactoryImpl.getInstance();

    CONSTRUCT = factory.createURI(NAMESPACE, "Construct");

    BASENAME = factory.createURI(NAMESPACE, "basename");

    // TODO BASENAME vs. NAME !!

    NAME = factory.createURI(NAMESPACE, "basename");

    OCCURENCE = factory.createURI(NAMESPACE, "occurrence");

    ASSOCIATION = factory.createURI(NAMESPACE, "association");

    SOURCELOCATOR = factory.createURI(NAMESPACE, "source-locator");

    SUBJECTIDENTIFIER = factory.createURI(NAMESPACE, "subject-identifier");

    SUBJECLOCATOR = factory.createURI(NAMESPACE, "subject-locator");

    INSTANCEOF = factory.createURI(NAMESPACE, "instance-of");

    MAPSTO = factory.createURI(NAMESPACE, "maps-to");

    TYPE = factory.createURI(NAMESPACE, "type");

    INSCOPE = factory.createURI(NAMESPACE, "in-scope");

    SUBJECTROLE = factory.createURI(NAMESPACE, "subject-role");

    OBJECTROLE = factory.createURI(NAMESPACE, "object-role");

  }

}

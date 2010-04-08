/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.vocabularies;

import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;

/**
 * The vocabulary described in www.isotopicmaps.org/sam/sam-model/ implemented
 * as {@link URI}'s.
 * 
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class TMDM {

  public static final String MODEL = "http://psi.topicmaps.org/iso13250/model/";

  public static final String GLOSSARY = "http://psi.topicmaps.org/iso13250/glossary/";
  

  public final static URI TOPICTYPE;
  
  
  public final static URI TYPEINSTANCEASSOTYPE;
  public final static URI TYPE;  
  public final static URI INSTANCE;

  public final static URI SUPERTYPESUBTYPEASSOTYPE;
  public final static URI SUPERTYPE;
  public final static URI SUBTYPE;
  
  public final static URI SORT;

  public final static URI TOPICNAME;



  public static URI UCS;


  static {

    ValueFactory factory = ValueFactoryImpl.getInstance();
    
    TOPICTYPE = factory.createURI(MODEL, "TopicType");

    
    TYPEINSTANCEASSOTYPE = factory.createURI(MODEL, "type-instance");
    TYPE = factory.createURI(MODEL, "type");
    INSTANCE = factory.createURI(MODEL, "instance");
    
    SUPERTYPESUBTYPEASSOTYPE = factory.createURI(MODEL,
    "supertype-subtype");
    SUPERTYPE = factory.createURI(MODEL, "supertype");
    SUBTYPE = factory.createURI(MODEL, "subtype");

    SORT = factory.createURI(MODEL, "topic-sort");    

    TOPICNAME = factory.createURI(MODEL, "topic-name");    

    
    
    UCS = factory.createURI(GLOSSARY, "unconstrained-scope");

  }
}
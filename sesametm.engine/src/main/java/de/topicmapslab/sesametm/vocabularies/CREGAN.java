/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.vocabularies;

import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;

/**
 * The {@link URI}'s used as vocabulary. Based on Anne Cregan's
 * "Building Topic Maps in OWL-DL".
 * 
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CREGAN {

  public static final String NAMESPACE = "http://www.sesametm.topicmapslab.de/vocabularies/cregan#";

  public final static URI TOPIC;
  

  public final static URI ASSOCIATIONROLE;

  public final static URI ASSOCIATIONROLETYPE;

  public final static URI TOPICMAP;

  public final static URI ASSOCIATION;

  public final static URI TOPICNAME;

  public final static URI VARIANT;

  public final static URI SCOPE;

  public final static URI OCCURRENCE;

  public final static URI OCCURENCETYPE;

  public final static URI ASSOCIATIONTYPE;

  public final static URI SUPERTYPESUBTYPE;

  public final static URI SUBTYPEINSUPERTYPESUBTYPE;

  public final static URI TYPEINTYPEINSTANCE;

  public final static URI TYPEINSTANCE;

  public final static URI INSTANCEINTYPEINSTANCE;

  public final static URI BELONGSTOTOPICMAP;

  public final static URI PLAYEDBY;

  public final static URI HASROLE;

  public final static URI ROLEINASSOCIATION;

  public final static URI OCCURENCEOF;

  public final static URI NAMEOF;

  public final static URI HASNAME;

  public final static URI ISVARIANTOF;

  public final static URI HASVARIANT;

  public final static URI HASREIFIER;

  public final static URI ISREIFIEROF;

  public final static URI HASOCCURENCE;
  
  public final static URI DATATYPE;
  
  public final static URI NAME;

  public static URI VALUE;
  
  
  public final static URI ITEMIDENTIFIER;

  public final static URI SUBJECTIDETIFIER;

  public final static URI SUBJECTLOCATOR;
  
  public final static URI ROLE;

  public final static URI LOCATOR;




  static {

    ValueFactory factory = ValueFactoryImpl.getInstance();
    
    ROLE = factory.createURI(NAMESPACE, "Role");
    LOCATOR = factory.createURI(NAMESPACE, "locator");

    
    ITEMIDENTIFIER = factory.createURI(NAMESPACE, "item-identifier");
    SUBJECTIDETIFIER = factory.createURI(NAMESPACE, "subject-identifier");
    SUBJECTLOCATOR = factory.createURI(NAMESPACE, "subject-locator");
    
    VALUE = factory.createURI(NAMESPACE, "value");
    
    NAME = factory.createURI(NAMESPACE, "Name");
 
    DATATYPE = factory.createURI(NAMESPACE, "hasDataType");

    ISREIFIEROF = factory.createURI(NAMESPACE, "isReifierOf");

    HASREIFIER = factory.createURI(NAMESPACE, "hasReifier");

    HASVARIANT = factory.createURI(NAMESPACE, "hasVariant");

    ISVARIANTOF = factory.createURI(NAMESPACE, "ifVariantOf");

    OCCURENCEOF = factory.createURI(NAMESPACE, "occurenceOf");

    NAMEOF = factory.createURI(NAMESPACE, "nameOf");

    TOPIC = factory.createURI(NAMESPACE, "Topic");

    ASSOCIATIONROLE = factory.createURI(NAMESPACE, "AssociationRole");

    ASSOCIATIONROLETYPE = factory.createURI(NAMESPACE, "AssociationRoleType");

    TOPICMAP = factory.createURI(NAMESPACE, "TopicMap");

    ASSOCIATION = factory.createURI(NAMESPACE, "Association");

    TOPICNAME = factory.createURI(NAMESPACE, "TopicName");

    VARIANT = factory.createURI(NAMESPACE, "Variant");

    SCOPE = factory.createURI(NAMESPACE, "Scope");

    OCCURRENCE = factory.createURI(NAMESPACE, "Occurrence");

    OCCURENCETYPE = factory.createURI(NAMESPACE, "OccurrenceType");

    ASSOCIATIONTYPE = factory.createURI(NAMESPACE, "AssociationType");

    SUPERTYPESUBTYPE = factory.createURI(NAMESPACE, "Supertype_Subtype");

    SUBTYPEINSUPERTYPESUBTYPE = factory.createURI(NAMESPACE,
        "Subtype_In_Supertype_Subtype");

    TYPEINTYPEINSTANCE = factory.createURI(NAMESPACE, "Type_In_Type_Instance");

    TYPEINSTANCE = factory.createURI(NAMESPACE, "Type_Instance");

    INSTANCEINTYPEINSTANCE = factory.createURI(NAMESPACE,
        "Instance_In_Type_Instance");

    BELONGSTOTOPICMAP = factory.createURI(NAMESPACE, "belongsToTopicMap");

    PLAYEDBY = factory.createURI(NAMESPACE, "playedBy");

    HASROLE = factory.createURI(NAMESPACE, "hasRole");

    ROLEINASSOCIATION = factory.createURI(NAMESPACE, "roleInAssociation");

    HASNAME = factory.createURI(NAMESPACE, "hasName");

    HASOCCURENCE = factory.createURI(NAMESPACE, "hasOccurence");

  }
}
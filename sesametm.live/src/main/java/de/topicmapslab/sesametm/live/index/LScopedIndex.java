/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryEvaluationException;
import org.tmapi.core.Association;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;
import org.tmapi.index.ScopedIndex;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.core.LAssociation;
import de.topicmapslab.sesametm.live.core.LName;
import de.topicmapslab.sesametm.live.core.LOccurrence;
import de.topicmapslab.sesametm.live.core.LTopic;
import de.topicmapslab.sesametm.live.core.SparqlTopicMap;
import de.topicmapslab.sesametm.live.utils.LValue;
import de.topicmapslab.sesametm.utils.LiveException;
import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LScopedIndex extends LIndex implements ScopedIndex {

  /**
   * @param tm
   */
  public LScopedIndex(SparqlTopicMap tm) {
    super(tm);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getAssociationThemes()
   */
  @Override
  public Collection<Topic> getAssociationThemes() {
    return getThemes(RTM.ASSOCIATION);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getAssociations(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Association> getAssociations(Topic theme) {
    HashSet<Association> associations = new HashSet<Association>();
    Statement vocabularyStatement, repStatement;
    GraphQueryResult reMapping, reRepository;
    try {
      String locatorSting = theme.getSubjectIdentifiers().iterator().next()
          .toExternalForm();
      String q = "CONSTRUCT {?s <" + RTM.MAPSTO + "> <" + RTM.ASSOCIATION
          + ">. } " + "WHERE {?s <" + RTM.INSCOPE + "> <" + locatorSting
          + ">. " + " ?s <" + RTM.MAPSTO + "> <" + RTM.ASSOCIATION + ">. }";
      reMapping = runSparqlConstructQueryOnVocabulary(q);
      while (reMapping.hasNext()) {
        vocabularyStatement = reMapping.next();
        q = "CONSTRUCT {?s <" + vocabularyStatement.getSubject() + "> ?o. } "
            + "WHERE {?s <" + vocabularyStatement.getSubject() + "> ?o. }";
        reRepository = runSparqlConstructQueryOnRepository(q);
        while (reRepository.hasNext()) {
          repStatement = reRepository.next();
          associations.add(new LAssociation(repStatement.getPredicate(),
              topicMap, (URI) repStatement.getSubject(), (URI) repStatement
                  .getObject()));
        }
      }
    } catch (ClassCastException e) {
      throw new LiveException(e);
    } catch (QueryEvaluationException e) {
      e.printStackTrace();
    }
    return associations;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getAssociations(org.tmapi.core.Topic[],
   * boolean)
   */
  @Override
  public Collection<Association> getAssociations(Topic[] themes,
      boolean matchAll) {
    if (themes.length == 1)
      return getAssociations(themes[0]);
    HashSet<Association> associations = new HashSet<Association>();
    if (!matchAll)
      for (Topic scope : themes)
        associations.addAll(getAssociations(scope));
    else {
      if (themes.length > 1) {
        associations.addAll(getAssociations(themes[0]));
        for (Topic scope : themes)
          associations.retainAll(getAssociations(scope));
      }
    }
    return associations;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getNameThemes()
   */
  @Override
  public Collection<Topic> getNameThemes() {
    return getThemes(RTM.NAME);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getNames(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Name> getNames(Topic theme) {
    HashSet<Name> names = new HashSet<Name>();
    Statement vocabularyStatement, repStatement;
    GraphQueryResult reMapping, reRepository;
    LValue v;
    try {
      String locatorSting = theme.getSubjectIdentifiers().iterator().next()
          .toExternalForm();
      String q = "CONSTRUCT {?s <" + RTM.MAPSTO + "> <" + RTM.NAME + ">. } "
          + "WHERE {?s <" + RTM.INSCOPE + "> <" + locatorSting + ">. "
          + " ?s <" + RTM.MAPSTO + "> <" + RTM.NAME + ">. }";
      reMapping = runSparqlConstructQueryOnVocabulary(q);
      while (reMapping.hasNext()) {
        vocabularyStatement = reMapping.next();
        q = "CONSTRUCT {?s <" + vocabularyStatement.getSubject() + "> ?o. } "
            + "WHERE {?s <" + vocabularyStatement.getSubject() + "> ?o. }";
        reRepository = runSparqlConstructQueryOnRepository(q);
        while (reRepository.hasNext()) {
          repStatement = reRepository.next();
          v = new LValue(repStatement.getObject());
          if (v.getType().toString().equals(
              "http://www.w3.org/2001/XMLSchema#string"))
            names.add(new LName((URI) repStatement.getSubject(),
                (URI) vocabularyStatement.getSubject(), v, new LTopic(
                    new SesameLocator(repStatement.getSubject().stringValue()),
                    topicMap)));
        }
      }
    } catch (ClassCastException e) {
      throw new LiveException(e);
    } catch (QueryEvaluationException e) {
      e.printStackTrace();
    }
    return names;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getNames(org.tmapi.core.Topic[], boolean)
   */
  @Override
  public Collection<Name> getNames(Topic[] themes, boolean matchAll) {
    if (themes.length == 1)
      return getNames(themes[0]);
    HashSet<Name> names = new HashSet<Name>();
    if (!matchAll)
      for (Topic scope : themes)
        names.addAll(getNames(scope));
    else {
      if (themes.length > 1) {
        names.addAll(getNames(themes[0]));
        for (Topic scope : themes)
          names.retainAll(getNames(scope));
      }
    }
    return names;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getOccurrenceThemes()
   */
  @Override
  public Collection<Topic> getOccurrenceThemes() {
    return getThemes(RTM.OCCURENCE);
  }

  /*
   * DRY helper for getOccurrenceThemes(), getNameThemes(), and
   * getAssociationThemes()
   * 
   * @param URI kind to specify the type of the construct for whom the themes
   * are searched.
   */
  private Collection<Topic> getThemes(URI kind) {
    HashSet<Topic> themes = new HashSet<Topic>();
    Resource scope, type;
    Iterator<Resource> scopes;
    Iterator<Resource> map = mapping.mapsTo(kind).iterator();
    while (map.hasNext()) {
      type = map.next();
      scopes = mapping.getObjects(type, RTM.INSCOPE).iterator();
      while (scopes.hasNext()) {
        scope = scopes.next();
        if (repositoryHasStatement(null, (URI) type, null))
          themes.add(topicMap.getTopicBySubjectIdentifier(new SesameLocator(
              scope.stringValue())));
      }
    }
    return themes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getOccurrences(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Occurrence> getOccurrences(Topic theme) {
    HashSet<Occurrence> occurrences = new HashSet<Occurrence>();
    Statement vocabularyStatement, repStatement;
    GraphQueryResult reMapping, reRepository;
    try {
      String locatorSting = theme.getSubjectIdentifiers().iterator().next()
          .toExternalForm();
      String q = "CONSTRUCT {?s <" + RTM.MAPSTO + "> <" + RTM.OCCURENCE
          + ">. } " + "WHERE {?s <" + RTM.INSCOPE + "> <" + locatorSting
          + ">. " + " ?s <" + RTM.MAPSTO + "> <" + RTM.OCCURENCE + ">. }";
      reMapping = runSparqlConstructQueryOnVocabulary(q);
      while (reMapping.hasNext()) {
        vocabularyStatement = reMapping.next();
        q = "CONSTRUCT {?s <" + vocabularyStatement.getSubject() + "> ?o. } "
            + "WHERE {?s <" + vocabularyStatement.getSubject() + "> ?o. }";
        reRepository = runSparqlConstructQueryOnRepository(q);
        while (reRepository.hasNext()) {
          repStatement = reRepository.next();
          occurrences.add(new LOccurrence((URI) repStatement.getSubject(),
              (URI) vocabularyStatement.getSubject(), new LValue(repStatement
                  .getObject()), new LTopic(new SesameLocator(repStatement
                  .getSubject().stringValue()), topicMap)));
        }
      }
    } catch (ClassCastException e) {
      throw new LiveException(e);
    } catch (QueryEvaluationException e) {
      e.printStackTrace();
    }
    return occurrences;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getOccurrences(org.tmapi.core.Topic[],
   * boolean)
   */
  @Override
  public Collection<Occurrence> getOccurrences(Topic[] themes, boolean matchAll) {
    if (themes.length == 1)
      return getOccurrences(themes[0]);
    HashSet<Occurrence> occurrences = new HashSet<Occurrence>();
    if (!matchAll)
      for (Topic scope : themes)
        occurrences.addAll(getOccurrences(scope));
    else {
      if (themes.length > 1) {
        occurrences.addAll(getOccurrences(themes[0]));
        for (Topic scope : themes)
          occurrences.retainAll(getOccurrences(scope));
      }
    }
    return occurrences;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getVariantThemes()
   */
  @Override
  public Collection<Topic> getVariantThemes() {
    return new HashSet<Topic>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getVariants(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Variant> getVariants(Topic theme) {
    return new HashSet<Variant>();

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getVariants(org.tmapi.core.Topic[],
   * boolean)
   */
  @Override
  public Collection<Variant> getVariants(Topic[] themes, boolean matchAll) {
    return new HashSet<Variant>();
  }

}

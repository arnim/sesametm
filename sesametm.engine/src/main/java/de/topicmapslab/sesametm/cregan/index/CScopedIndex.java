/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.openrdf.model.Resource;
import org.tmapi.core.Association;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;
import org.tmapi.index.ScopedIndex;

import de.topicmapslab.sesametm.cregan.core.CAssociation;
import de.topicmapslab.sesametm.cregan.core.CName;
import de.topicmapslab.sesametm.cregan.core.COccurrence;
import de.topicmapslab.sesametm.cregan.core.CTopicMap;
import de.topicmapslab.sesametm.cregan.core.CVariant;
import de.topicmapslab.sesametm.vocabularies.CREGAN;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class CScopedIndex extends CIndex implements ScopedIndex {

  public CScopedIndex(CTopicMap tm) {
    super(tm);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getAssociationThemes()
   */
  @Override
  public Collection<Topic> getAssociationThemes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.ASSOCIATION).iterator();
    while (h.hasNext()) {
      result.addAll(new CAssociation(sesameTopicMap, h.next()).getScope());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getAssociations(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Association> getAssociations(Topic theme) {
    Collection<Association> result = new HashSet<Association>();
    Iterator<Resource> h = getNodes(CREGAN.ASSOCIATION).iterator();
    while (h.hasNext()) {
      Association n = new CAssociation(sesameTopicMap, h.next());
      if (n.getScope().contains(theme))
        result.add(n);
      if (n.getScope().isEmpty() && theme == null)
        result.add(n);
    }
    return result;
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
    if (themes == null)
      throw new IllegalArgumentException(
          "getAssociations(null, boolean) is illegal");
    if (themes.length == 1)
      return getAssociations(themes[0]);
    Collection<Association> allScoped = sesameTopicMap.getAssociations();
    Iterator<Association> allScopedIterator = allScoped.iterator();
    Collection<Association> result = new HashSet<Association>();
    while (allScopedIterator.hasNext()) {
      Association topic = allScopedIterator.next();
      if (matchAll) {
        result.addAll(getAssociations(themes[0]));
        for (int i = 1; i < themes.length; i++)
          result.retainAll(getAssociations(themes[i]));
      } else {
        for (Topic theme : themes)
          if (topic.getScope().contains(theme))
            result.add(topic);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getNameThemes()
   */
  @Override
  public Collection<Topic> getNameThemes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.NAME).iterator();
    while (h.hasNext()) {
      result.addAll(new CName(sesameTopicMap, h.next()).getScope());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getNames(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Name> getNames(Topic theme) {
    Collection<Name> result = new HashSet<Name>();
    Iterator<Resource> h = getNodes(CREGAN.NAME).iterator();
    while (h.hasNext()) {
      Name n = new CName(sesameTopicMap, h.next());
      if (n.getScope().contains(theme))
        result.add(n);
      if (n.getScope().isEmpty() && theme == null)
        result.add(n);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getNames(org.tmapi.core.Topic[], boolean)
   */
  @Override
  public Collection<Name> getNames(Topic[] themes, boolean matchAll) {
    if (themes == null)
      throw new IllegalArgumentException("getNames(null, boolean) is illegal");
    if (themes.length == 1)
      return getNames(themes[0]);
    Collection<Name> allScoped = new HashSet<Name>();
    Iterator<Resource> h = getNodes(CREGAN.NAME).iterator();
    while (h.hasNext()) {
      allScoped.add(new CName(sesameTopicMap, h.next()));
    }
    Iterator<Name> allScopedIterator = allScoped.iterator();
    Collection<Name> result = new HashSet<Name>();
    while (allScopedIterator.hasNext()) {
      Name topic = allScopedIterator.next();
      if (matchAll) {
        result.addAll(getNames(themes[0]));
        for (int i = 1; i < themes.length; i++)
          result.retainAll(getNames(themes[i]));
      } else {
        for (Topic theme : themes)
          if (topic.getScope().contains(theme))
            result.add(topic);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getOccurrenceThemes()
   */
  @Override
  public Collection<Topic> getOccurrenceThemes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.OCCURRENCE).iterator();
    while (h.hasNext()) {
      result.addAll(new COccurrence(sesameTopicMap, h.next()).getScope());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getOccurrences(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Occurrence> getOccurrences(Topic theme) {
    Collection<Occurrence> result = new HashSet<Occurrence>();
    Iterator<Resource> h = getNodes(CREGAN.OCCURRENCE).iterator();
    while (h.hasNext()) {
      Occurrence n = new COccurrence(sesameTopicMap, h.next());
      if (n.getScope().contains(theme))
        result.add(n);
      if (n.getScope().isEmpty() && theme == null)
        result.add(n);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getOccurrences(org.tmapi.core.Topic[],
   * boolean)
   */
  @Override
  public Collection<Occurrence> getOccurrences(Topic[] themes, boolean matchAll) {
    if (themes == null)
      throw new IllegalArgumentException(
          "getAssociations(null, boolean) is illegal");
    if (themes.length == 1)
      return getOccurrences(themes[0]);
    Collection<Occurrence> allScoped = new HashSet<Occurrence>();
    Iterator<Resource> h = getNodes(CREGAN.OCCURRENCE).iterator();
    while (h.hasNext()) {
      allScoped.add(new COccurrence(sesameTopicMap, h.next()));
    }
    Iterator<Occurrence> allScopedIterator = allScoped.iterator();
    Collection<Occurrence> result = new HashSet<Occurrence>();
    while (allScopedIterator.hasNext()) {
      Occurrence topic = allScopedIterator.next();
      if (matchAll) {
        result.addAll(getOccurrences(themes[0]));
        for (int i = 1; i < themes.length; i++)
          result.retainAll(getOccurrences(themes[i]));
      } else {
        for (Topic theme : themes)
          if (topic.getScope().contains(theme))
            result.add(topic);
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getVariantThemes()
   */
  @Override
  public Collection<Topic> getVariantThemes() {
    Collection<Topic> result = new HashSet<Topic>();
    Iterator<Resource> h = getNodes(CREGAN.VARIANT).iterator();
    while (h.hasNext()) {
      result.addAll(new CVariant(sesameTopicMap, h.next()).getScope());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getVariants(org.tmapi.core.Topic)
   */
  @Override
  public Collection<Variant> getVariants(Topic theme) {
    if (theme == null)
      throw new IllegalArgumentException("getVariants(null) is illegal");
    Collection<Variant> result = new HashSet<Variant>();
    Iterator<Resource> h = getNodes(CREGAN.VARIANT).iterator();
    while (h.hasNext()) {
      Variant n = new CVariant(sesameTopicMap, h.next());
      if (n.getScope().contains(theme)
          || n.getParent().getScope().contains(theme))
        result.add(n);
      if (n.getScope().isEmpty() && theme == null)
        result.add(n);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.ScopedIndex#getVariants(org.tmapi.core.Topic[],
   * boolean)
   */
  @Override
  public Collection<Variant> getVariants(Topic[] themes, boolean matchAll) {
    if (themes == null)
      throw new IllegalArgumentException(
          "getVariants(null, boolean) is illegal");
    if (themes.length == 1)
      return getVariants(themes[0]);
    Collection<Variant> allScoped = new HashSet<Variant>();
    Iterator<Resource> h = getNodes(CREGAN.VARIANT).iterator();
    while (h.hasNext()) {
      allScoped.add(new CVariant(sesameTopicMap, h.next()));
    }
    Iterator<Variant> allScopedIterator = allScoped.iterator();
    Collection<Variant> result = new HashSet<Variant>();
    while (allScopedIterator.hasNext()) {
      Variant topic = allScopedIterator.next();
      if (matchAll) {
        result.addAll(getVariants(themes[0]));
        for (int i = 1; i < themes.length; i++)
          result.retainAll(getVariants(themes[i]));
      } else {
        for (Topic theme : themes)
          if (topic.getScope().contains(theme))
            result.add(topic);
      }
    }
    return result;
  }

}

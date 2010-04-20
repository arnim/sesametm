/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.tmapi.core.Construct;
import org.tmapi.core.Locator;
import org.tmapi.core.TopicMap;

import de.topicmapslab.sesametm.core.SesameLocator;
import de.topicmapslab.sesametm.live.utils.GenericLiveAdapter;
import de.topicmapslab.sesametm.utils.SesameTMUnsupportedOperationException;;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LConstruct extends GenericLiveAdapter implements Construct {

  SparqlTopicMap tm;

  public LConstruct(Locator l, SparqlTopicMap topicMap,
      LTopicMapSystem theTms) {
    super(l, theTms);
    tm = topicMap;
  }

  public LConstruct(Locator l, LTopicMapSystem theTms) {
    super(l, theTms);
    tm = (SparqlTopicMap) this;
  }

  public LConstruct(Locator l, SparqlTopicMap theTm) {
    super(l, theTm.topicMapgetTMS());
    tm = theTm;
  }

  public LConstruct(URI u, SparqlTopicMap topicMap) {
    super(u, topicMap.topicMapgetTMS());
    tm = topicMap;
  }

  /**
   * 
   * @param resources
   * @return
   */
  public Set<LTopic> generateTopicsFromResources(Set<Resource> resources) {
    Set<LTopic> topics = new HashSet<LTopic>();
    Iterator<Resource> iterator = resources.iterator();
    while (iterator.hasNext()) {
      Resource resource = iterator.next();
      topics
          .add(new LTopic(new SesameLocator(resource.stringValue()), tm));
    }
    return topics;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#addItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void addItemIdentifier(Locator itemIdentifier) {
    notSupported();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getId()
   */
  @Override
  public String getId() {
    return reference.stringValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getItemIdentifiers()
   */
  @Override
  public Set<Locator> getItemIdentifiers() {
    // TODO impl
    return new HashSet<Locator>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getParent()
   */
  @Override
  public Construct getParent() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#getTopicMap()
   */
  @Override
  public TopicMap getTopicMap() {
    return tm;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#remove()
   */
  @Override
  public void remove() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Construct#removeItemIdentifier(org.tmapi.core.Locator)
   */
  @Override
  public void removeItemIdentifier(Locator itemIdentifier) {
  }

  protected void notSupported() {
    throw new SesameTMUnsupportedOperationException();
  }

}

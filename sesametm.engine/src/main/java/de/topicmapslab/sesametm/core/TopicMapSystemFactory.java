/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.core;

import java.util.HashMap;
import java.util.Map;


import org.tmapi.core.FeatureNotRecognizedException;
import org.tmapi.core.FeatureNotSupportedException;
import org.tmapi.core.TMAPIException;
import org.tmapi.core.TopicMapSystem;

import de.topicmapslab.sesametm.cregan.core.CTopicMapSystem;
//import de.topicmapslab.sesametm.live.core.LTopicMapSystem;
import de.topicmapslab.sesametm.vocabularies.FEATURE;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public final class TopicMapSystemFactory extends org.tmapi.core.TopicMapSystemFactory {

  public final String MAPPING_PROPERTY = "de.topicmapslab.sesametm.mapping"; 
  
  
  private Map<String, Object> theProperties;
  private Map<String, Boolean> theFeatures;


  public TopicMapSystemFactory() {
    theProperties = new HashMap<String, Object>();
    theFeatures = new HashMap<String, Boolean>();
    theFeatures.put(FEATURE.AUTOMERGE, false);
    theFeatures.put(FEATURE.READ_ONLY, false);
    theFeatures.put(FEATURE.TYPE_INSTANCE_ASSOCS, false);
  }

  
  /**
   * {@inheritDoc}
   */
  @Override
public TopicMapSystem newTopicMapSystem() throws TMAPIException {
//    if (usedImplementation != null && usedImplementation.equalsIgnoreCase("live") )
//      return new LTopicMapSystem(
//          new HashMap<String, Object>(theProperties),
//          new HashMap<String, Boolean>(theFeatures));
//    else 
      return new CTopicMapSystem(
          new HashMap<String, Object>(theProperties),
          new HashMap<String, Boolean>(theFeatures));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getFeature(String featureName)
      throws FeatureNotRecognizedException {
    return theFeatures.get(featureName).booleanValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasFeature(String featureName) {
    return theFeatures.containsKey(featureName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFeature(String featureName, boolean enabled)
      throws FeatureNotSupportedException, FeatureNotRecognizedException {
    theFeatures.put(featureName, enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getProperty(String propertyName) {
    return theProperties.get(propertyName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(final String propertyName, Object value) {
    theProperties.put(propertyName, value);
  }

}

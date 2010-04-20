/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.core;

import java.util.HashMap;
import java.util.Map;

import org.tmapi.core.FeatureNotRecognizedException;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;

import de.topicmapslab.sesametm.vocabularies.PROPERTY;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */
public abstract class SesameTopicMapSystem implements TopicMapSystem {

  protected TopicMap topicMpap;
  private Map<String, Object> theProperties;
  private Map<String, Boolean> theFeatures;

  /**
   * Constructor
   * 
   * @param properties
   *          HashMap<String, Object> allows to set the Property value for a
   *          propertyName
   * @param features
   *          allows to set true ore false for a featureName
   * 
   */
  public SesameTopicMapSystem(HashMap<String, Object> properties,
      HashMap<String, Boolean> features) {
    theProperties = properties;
    theFeatures = features;
  }
  
  protected void setConnection(Object o) {
	  theProperties.put(PROPERTY.CONNECTION, o);
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
  public Object getProperty(String propertyName) {
    return theProperties.get(propertyName);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TopicMap getTopicMap(String s) {
    return getTopicMap(new SesameLocator(s));
  }



}

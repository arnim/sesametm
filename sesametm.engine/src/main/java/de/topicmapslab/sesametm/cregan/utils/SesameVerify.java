/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.cregan.utils;

import java.util.Collection;

import org.tmapi.core.Construct;
import org.tmapi.core.Locator;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Topic;

/**
 * 
 * Class for the checking if parameters are danded over correctly to functions
 * of {@link Construct}
 * 
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */
public class SesameVerify {

  Construct construct;

  /**
   * Default constructor
   * 
   * @param c
   *          a {@link Construct}
   */
  public SesameVerify(Construct c) {
    construct = c;
  }

  /**
   * Tests if this and other are governed by the same Topic Map
   * 
   * @param other
   *          a {@link Construct}
   * @throws ModelConstraintException
   */
  public void sameTopicMap(Construct other) {
    if (!other.getTopicMap().equals(construct.getTopicMap())) {
      throw new ModelConstraintException(other.getTopicMap(),
          "Model contraint violation");
    }
  }

  /**
   * Tests if the {@link Locator} locator is not <code>null</code>.
   * 
   * @param locator
   * @throws ModelConstraintException
   */
  public void parameterIsNotNull(Locator locator) {
    if (locator == null) {
      throw new ModelConstraintException(construct,
          "Locator == null is illegal");
    }
  }

  /**
   * Tests if the parameter {@link Topic} is not <code>null</code>.
   * 
   * @param topic
   * @throws ModelConstraintException
   */
  public void parameterIsNotNull(Topic topic) {
    if (topic == null) {
      throw new ModelConstraintException(construct, "Topic == null is illegal");
    }
  }

  /**
   * Tests if the parameter {@link String} is not <code>null</code>.
   * 
   * @param value
   * @throws ModelConstraintException
   */
  public void parameterIsNotNull(String value) {
    if (value == null) {
      throw new ModelConstraintException(construct, "Null is illegal");
    }
  }

  /**
   * Tests if the parameter {@link Topic[]} is not <code>null</code>.
   * 
   * @param scopes
   * @throws ModelConstraintException
   */
  public void parameterIsNotNull(Topic[] scopes) {
    if (scopes == null) {
      throw new ModelConstraintException(construct,
          "(Construct[])null is illegal");
    }
  }

  /**
   * Tests if the parameter {@link Collection<Topic>} is not <code>null</code>.
   * 
   * @param scopes
   * @throws ModelConstraintException
   */
  public void parameterIsNotNull(Collection<Topic> scopes) {
    if (scopes == null) {
      throw new ModelConstraintException(construct,
          "(Collection<Topic>)null) is illegal");
    }
  }

  /**
   * Tests if the parameter {@link Topic} is not <code>null</code>.
   * 
   * @param topic
   * @throws IllegalArgumentException
   */
  public void parameterIsNotIllegalArgument(Topic topic) {
    if (topic == null) {
      throw new IllegalArgumentException("Topic == null is illegal");
    }

  }

}

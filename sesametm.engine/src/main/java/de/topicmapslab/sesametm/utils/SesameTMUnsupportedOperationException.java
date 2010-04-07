/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.utils;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class SesameTMUnsupportedOperationException extends SesameTMException {


  private static final long serialVersionUID = 1L;

  /**
   * Default Message String
   */
  private final static String message = "This Topic Map is READ ONLY!";

  /**
   * 
   */
  public SesameTMUnsupportedOperationException() {
    super(message);
  }

  /**
   * @param message
   */
  public SesameTMUnsupportedOperationException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public SesameTMUnsupportedOperationException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public SesameTMUnsupportedOperationException(String message, Throwable cause) {
    super(message, cause);
  }

}

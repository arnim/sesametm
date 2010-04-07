/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.utils;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LiveException extends SesameTMException {


  private static final long serialVersionUID = 1L;

  /**
   * Default Message String
   */
  private final static String message = "The provided mapping vocabulary is malformed";

  /**
   * 
   */
  public LiveException() {
    super(message);
  }

  /**
   * @param message
   */
  public LiveException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public LiveException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public LiveException(String message, Throwable cause) {
    super(message, cause);
  }

}

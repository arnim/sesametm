/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.utils;

import org.tmapi.core.TMAPIRuntimeException;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class SesameTMException extends TMAPIRuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default Message String
   */
  private final static String message = "Something went wrong with SesameTM";

  /**
   * 
   */
  public SesameTMException() {
    super(message);
  }

  /**
   * @param message
   */
  public SesameTMException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public SesameTMException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public SesameTMException(String message, Throwable cause) {
    super(message, cause);
  }

}

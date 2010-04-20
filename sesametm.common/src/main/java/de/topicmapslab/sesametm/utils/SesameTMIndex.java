/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.utils;

import org.openrdf.model.URI;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.tmapi.index.Index;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public abstract class SesameTMIndex implements Index {

  protected RepositoryConnection con;
  protected URI base;

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.Index#close()
   */
  @Override
  public void close() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.Index#isAutoUpdated()
   */
  @Override
  public boolean isAutoUpdated() {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.Index#isOpen()
   */
  @Override
  public boolean isOpen() {
    boolean result = false;
    try {
      result = con.isOpen();
    } catch (RepositoryException e) {
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.Index#open()
   */
  @Override
  public void open() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.index.Index#reindex()
   */
  @Override
  public void reindex() {
  }

}

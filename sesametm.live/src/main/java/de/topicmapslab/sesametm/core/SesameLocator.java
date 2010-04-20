/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.tmapi.core.Locator;
import org.tmapi.core.MalformedIRIException;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class SesameLocator implements Locator {

  public URI locatorURI;
  String reference;

  public SesameLocator(String stringLocator) {
    try {
      locatorURI = ValueFactoryImpl.getInstance().createURI(stringLocator);
    } catch (Exception e) {
      throw new MalformedIRIException(
          "Expected an error TopicMap#createLocator() with the input '"
              + stringLocator + "'");
    }
    try {
      reference = URLDecoder.decode(stringLocator, "utf-8");
    } catch (UnsupportedEncodingException e) {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Locator#getReference()
   */
  @SuppressWarnings("deprecation")
  @Override
  public String getReference() {
    return URLDecoder.decode(locatorURI.stringValue());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Locator#resolve(java.lang.String)
   */
  @Override
  public Locator resolve(String arg0) {
    Locator l = null;
    try {
      l = new SesameLocator(java.net.URI.create(this.getReference()).resolve(
          arg0).toString());
    } catch (Exception e) {
      l = new SesameLocator(this.getReference() + arg0.replace("./", ""));
    }
    return l;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.tmapi.core.Locator#toExternalForm()
   */
  @Override
  public String toExternalForm() {
    return getReference().replace(" ", "%20");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return locatorURI.stringValue();
  }

  public URI getUri() {
    return locatorURI;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return locatorURI.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    try {
      return toExternalForm().equals(((Locator) o).toExternalForm());
    } catch (Exception e) {
      return false;
    }
  }

}

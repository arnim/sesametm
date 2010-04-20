/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.utils;

import java.net.URI;
import java.net.URISyntaxException;

import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class LValue {

  private String value;
  private URI type;
  LiteralImpl lv;


  public LValue(Value v) {    
    value = v.stringValue();
    type = extractType(v);
    try {
      lv = (LiteralImpl) v;
      value = lv.stringValue();
      try {
        org.openrdf.model.URI dt = lv.getDatatype();
        type = new URI(dt.stringValue());
      } catch (Exception e) {
        type = new URI("http://www.w3.org/2001/XMLSchema#string");
      }
    } catch (Exception e) {
    }
  }

  @Override
  public String toString() {
    char c = (char) 34;
    return c + value + c + "^^<" + type + ">";
  }
  

  public String stringValue() {
    char c = (char) 34;
    return c + value + c + "^^<" + type + ">";
  }

  /**
   * @param uri
   *          the uri to set
   */
  public void setType(URI uri) {
    this.type = uri;
  }

  /**
   * @return the uri
   */
  public URI getType() {
    return type;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  protected URI extractType(Value value) {
    URI result = null;
    String strValue = value.toString();
    int i = strValue.indexOf("^^<http://www.w3.org/2001/XMLSchema#") + 3;
    if (i > 3) {
      try {
        result = new URI(strValue.substring(i, strValue.length() - 1));
      } catch (StringIndexOutOfBoundsException e) {
        e.printStackTrace();
      } catch (URISyntaxException e) {
      }
    } else {
      try {
        result = new URI("http://www.w3.org/2001/XMLSchema#anyURI");
      } catch (URISyntaxException e) {
      }
    }
    return result;
  }

}

/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.topicmaps;

import java.io.InputStream;

public class Accessor {

  /**
   * Allows to read a file inside a JAR file.
   * 
   * @param fileName
   *          The File name of the File to be converted.
   * @return The file contend in String Representation.
   * @throws Exception
   */
  public InputStream convertStringToInputStream(String fileName)
      throws Exception {
    return getClass().getResourceAsStream(fileName);
  }

}

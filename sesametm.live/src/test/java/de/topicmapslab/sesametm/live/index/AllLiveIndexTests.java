/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.index;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllLiveIndexTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(
        "Test for de.topicmapslab.sesametm.live.index");

    // $JUnit-BEGIN$
    suite.addTestSuite(LTypeInstanceIndexTest.class);
    suite.addTestSuite(LLiteralIndexTest.class);
    suite.addTestSuite(LScopedIndexTest.class);

    // $JUnit-END$
    return suite;
  }

}

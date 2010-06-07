/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.core;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllLiveCoreTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(
        "Test for de.topicmapslab.sesametm.sesametm.core");
    // $JUnit-BEGIN$
    suite.addTestSuite(LTopicMapTest.class);

    suite.addTestSuite(LTopicTest.class);

    suite.addTestSuite(LOccurenceTest.class);

    suite.addTestSuite(LNameTest.class);

    suite.addTestSuite(LTypeTest.class);
    
//    suite.addTestSuite(SystemTest.class);
    
    suite.addTestSuite(LRemoteTest.class);

    // $JUnit-END$
    return suite;
  }

}

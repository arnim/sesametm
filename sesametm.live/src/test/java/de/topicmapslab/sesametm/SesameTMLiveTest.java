/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm;

import junit.framework.Test;
import junit.framework.TestSuite;
import de.topicmapslab.sesametm.live.core.AllLiveCoreTests;
import de.topicmapslab.sesametm.live.index.AllLiveIndexTests;


public class SesameTMLiveTest {

  public static Test suite() {
    TestSuite suite = new TestSuite("All Tests for SesameTM Live");
    
    suite.addTest(AllLiveCoreTests.suite());
    
    suite.addTest(AllLiveIndexTests.suite());

    return suite;
  }

}

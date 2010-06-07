/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm;

import de.topicmapslab.sesametm.cregan.SesameTMEngineTest;
import de.topicmapslab.sesametm.cregan.SesameTMTMAPIxTest;
import junit.framework.Test;
import junit.framework.TestSuite;


public class AllSesameTMEngineTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("All Tests for SesameTM Engine");

    suite.addTest(org.tmapi.AllTests.suite());
    
    suite.addTestSuite(SesameTMEngineTest.class);
    
    suite.addTestSuite(SesameTMTMAPIxTest.class);

    
    return suite;
  }

}

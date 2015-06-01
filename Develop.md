## Building SesameTM from the source ##


Check out the respective source files:
  * [SAIL-TMAPI (Nikunau)](http://code.google.com/p/sesametm/source/checkout?repo=sesame-sail-tmapi)
  * [SAIL-TMAPI Plugins](http://code.google.com/p/sesametm/source/checkout?repo=sesame-sail-plugins)
  * [Topic Maps engine](http://code.google.com/p/sesametm/source/checkout)



Running the following m2 command generates configuration files for your IDE:
  * Eclipse: 	          `mvn eclipse:eclipse`
  * Netbeans:            `mvn netbeans-freeform:generate-netbeans-project`
  * IntelliJ IDEA:	  `mvn idea:idea`


To build, test and install the project form command line execute:
```
mvn clean install --update-snapshots
```
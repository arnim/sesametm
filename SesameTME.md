# The SesameTM engine #


## This Page Assumes ##
This wiki is for beginners who want to get started with SesameTM.
It assumes that you have some experience with [Java™](http://java.sun.com/) and with the [TMAPI 2.0](http://www.tmapi.org/2.0/).
However, to get the most out of it, you should familiarize yourself with
the Resource Description Framework (RDF), too.
A good place to start with RDF is:
http://www.w3.org/TR/REC-rdf-syntax/



## Creating a new Topic Map with SesameTM ##
Download the latest [version](http://sesametm.googlecode.com/files/sesametm-engine-0.1.6.zip) and put the sesametm-engine-x.x.x.jar, the sesametm-common-x.x.x.jar and the Jar’s in the /lib folder into your CLASSPATH or use the [Maven Repository](Maven.md) to obtain SesameTM. Once this is done the following example should work
```
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

...
TopicMapSystemFactory tmSysFactory = TopicMapSystemFactory.newInstance();
TopicMapSystem tmSys = tmSysFactory.newTopicMapSystem();
TopicMap tm = tmSys.createTopicMap("http://www.example.com/topicMap");
Topic exampleTopic = tm.createTopic();
```


## Setting your own Connection ##
The `PROPERTY.CONNECTION` (de.topicmapslab.sesametm.vocabularies.connection) can be used to provide a
[RepositoryConnection](http://www.openrdf.org/doc/sesame2/2.3.0/apidocs/org/openrdf/repository/RepositoryConnection.html) in case a already existing RDF repository connection should be used or more control is needed.


```
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

...
Repository myRepository = new SailRepository(new MemoryStore());
myRepository.initialize();
RepositoryConnection con = myRepository.getConnection();
tmSysFactory.setProperty("de.topicmapslab.sesametm.vocabularies.connection", con);
```
For more information on Sesame visit the [User Guide for Sesame](http://www.openrdf.org/doc/sesame2/2.3.0/users/ch08.html)




## Importing and Exporting Topic Maps ##

### Using TMAPIX ###
While SesameTM itselfe porvides no I/O capabilities [Lars Heuer's](http://www.semagia.com/)
[Utilities for TMAPI compatible Topic Maps engines TMAPIX](http://code.google.com/p/tmapix) can be used.

Include the latest [tmapix-io.jar's](http://code.google.com/p/tmapix/downloads) on your CLASSPATH
and the following example should work:

```
import org.tmapix.io.XTM20TopicMapReader;
import org.tmapix.io.XTM20TopicMapWriter;
import org.tmapix.io.XTMTopicMapReader;

...
XTM20TopicMapReader reader = new XTM20TopicMapReader(tm, inputStream, "http://www.ex.com/some");
reader.read();

...
XTM20TopicMapWriter writer = new XTM20TopicMapWriter(System.out, "http://www.ex.com/some");
writer.write(tm);
```

### Using CTM Writer ###
A further possibility is to use the [CTM Writer](http://code.google.com/p/ctm-writer/).
Documentation on how to use it can be found [here](http://docs.topicmapslab.de/ctm-writer/).
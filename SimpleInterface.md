This part describes a interface to enable simplified access to [Sesame](http://www.openrdf.org/doc/sesame2/2.3.1/users/ch08.html) for use within Topic Map applications.

# Usage #
Use the [Maven Repository](Maven.md) to obtain SAIL-TMAPI.
Create your [TopicMapSystem](http://www.tmapi.org/2.0/api/org/tmapi/core/TopicMapSystem.html) and populate it as you like.
```
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;
import import org.tmapi.core.TopicMap;
...

TopicMapSystem tms = TopicMapSystemFactory.newInstance().newTopicMapSystem();
String baseIRI = "http://www.ex.com/tm";
tms.createTopicMap(baseIRI);

```

Next create the [TMConnector](http://code.google.com/p/sesametm/source/browse/src/main/java/de/topicmapslab/sesame/simpleinterface/TMConnector.java?repo=sesame-sail-tmapi).
```
import de.topicmapslab.sesame.simpleinterface.TMConnector;
...

TMConnector tmConnector= new TMConnector(tms);
```

Now you can use [getRDFN3](http://code.google.com/p/sesametm/source/browse/src/main/java/de/topicmapslab/sesame/simpleinterface/TMConnector.java?repo=sesame-sail-tmapi#132) to retrive the triples describing the Topic with the Locator reference.
```
tmConnector.getRDFN3(
			tm.createLocator("http://www.ex.com/TopicReference"),
			tm.createLocator(baseIRI),
			System.out);
```

The execution [SPARQL Queries](http://www.w3.org/TR/rdf-sparql-query/) is equally simple.

The available [SPARQLResultFormat](http://code.google.com/p/sesametm/source/browse/src/main/java/de/topicmapslab/sesame/simpleinterface/SPARQLResultFormat.java?repo=sesame-sail-tmapi)s are JSON, XML, RDF/XML, CSV, HTML and N3 depending on the query type.
```
import de.topicmapslab.sesame.simpleinterface.SPARQLResultFormat;
...

String queryString = "CONSTRUCT {?s ?p ?o . } WHERE  { ?s ?p ?o }";
tmConnector.executeSPARQL(
			baseIRI,
			queryString, 
			SPARQLResultFormat.N3,
			System.out);
```


An experimental feature is the ability to create and update Topic Maps via RDF Statements.
```
import org.openrdf.rio.RDFFormat;
...

String rdfN3 = "<http://www.es.org/s>	<http://www.es.org/p> 'object' . ";
tmConnector.addRDF(
			baseIRI,
			RDFFormat.N3,
			new StringBufferInputStream(rdfN3));
```
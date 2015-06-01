Nikunau is a read-only [Sesame](http://www.openrdf.org/) [Sail](http://www.openrdf.org/doc/sesame2/2.3.1/apidocs/org/openrdf/sail/Sail.html) implementation
to enable a RDF view on the  [ISO/IEC 13250 Topic Maps](http://www.isotopicmaps.org/) standard.
TmapiStore supports every  [TMAPI 2.0](http://www.tmapi.org/2.0/) compatible engine as back-end
and follows the the mapping approach developed by [NetworkedPlanet](http://www.networkedplanet.com/ontopic/2009/11/making_topic_maps_sparql.html).

## Usage ##
Download the latest [version](http://sesametm.googlecode.com/files/sesame-sail-tmapi-0.0.9.zip) and put the sesame-sail-tmapi-x.x.x.jar and the Jar’s in the /lib folder into your CLASSPATH or use the [Maven Repository](Maven.md) to obtain SAIL-TMAPI.
Create your [TopicMapSystem](http://www.tmapi.org/2.0/api/org/tmapi/core/TopicMapSystem.html) and populate it as you like.
```
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

...

TopicMapSystem tms = TopicMapSystemFactory.newInstance().newTopicMapSystem();
...
```


Next, initialize the TmapiStore
```
import org.openrdf.repository.Repository;
import org.openrdf.repository.sail.SailRepository;
import de.topicmapslab.sesame.sail.tmapi.TmapiStore;

TmapiStore tmapiStore = new TmapiStore(tms);
Repository myRepository = new SailRepository(tmapiStore);
myRepository.initialize();
```

**Thats it!**
Now you can use it as described in the   [Sesame User Guide](http://www.openrdf.org/doc/sesame2/2.3.1/users/ch08.html#d0e666).
**Alternatively** you can also use the new [Simple Sesame Interface](SimpleInterface.md).
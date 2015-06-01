#Experimental RDF2TMWriting

# Introduction #

Since Nikunau is a [Sail](http://www.openrdf.org/doc/sesame2/2.3.1/apidocs/org/openrdf/sail/Sail.html) implementation its api would allow the [adding of RDF to a repository](http://www.openrdf.org/doc/sesame2/users/ch08.html#d0e836), which in this case is a Topic Map System.
The general idea is to allow full R/W from the perspective of RDF into a Topic Map System without changing the semantics of the RDF between writing and reading. To accomplish this the following write **mapping assumption** have been made:
  * All Graphs are mapped to Topic Maps.
  * All Subjects are mapped to Subject Identifiers of Topics.
    * In case a Occurrence exists with the same IRI as the Subject it gets removed and a new Topic is created connected to its original parent Topic by an Association with a Role of the type as the original Occurrence had.
  * All Literals are mapped to Occurrences with a typing Topic having the Subject Identifier of the property predicate and the datatype analog to the RDF datatype.
  * Objects that are IRI's by themself are mapped to External Occurrences
  * rdf#type and rdfs#sameAs predicates are mapped to type-instance Associations and Topics with multiple SI's respectively.


## Usage ##

Follow the steps for setting up the TmapiStore as described in [Nikunau](Nikunau.md) and familiarize yourself with [adding RDF to a repository](http://www.openrdf.org/doc/sesame2/users/ch08.html#d0e836) in the [openRDF user guide](http://www.openrdf.org/documentation.jsp). Alternatively you may consider the following example creating a topic with the SI http://xmlns.com/foaf/0.1/Person connected to a topic with the SI http://www.ex.org/JOHN (upon a type-instance Association) that has the external Occurrence http://www.ex.org/HOMEPAGE of the type www.w3.org/TR/rdf-schema/seeAlso:
```
...
import org.openrdf.model.vocabulary.RDF
...
TmapiStore tmapiStore = new TmapiStore(tms);
Repository myRepository = new SailRepository(tmapiStore);
myRepository.initialize();
RepositoryConnection con = myRepository.getConnection();
ValueFactory vf = con.getValueFactory();
...
con.add(
    vf.createURI("http://www.ex.org/JOHN"), 
    RDF.TYPE, 
    vf.createURI("http://xmlns.com/foaf/0.1/Person"), 
    vf.createURI("http://www.base.org/IRI"));
con.add(
    vf.createURI("http://www.ex.org/JOHN"), 
    vf.createURI("www.w3.org/TR/rdf-schema/seeAlso"), 
    vf.createURI("http://www.ex.org/HOMEPAGE"), 
    vf.createURI("http://www.base.org/IRI"));
...
TopicMap tm = tms.getTopicMap(tms.createLocator("http://www.base.org/IRI"));
Topic john = tm.getTopicBySubjectIdentifier(tm.createLocator("http://www.base.org/IRI"));
Set<Occurrence> seeAlsoSet = john.getOccurrences(
    tm.getTopicBySubjectIdentifier(tm.createLocator("www.w3.org/TR/rdf-schema/seeAlso")));
```


## Warning ##
RDF2TMWriting has been experimentally created to enable a generic and reversible mapping from RDF to Topic Maps without any further configuration. However, this comes at some cost; the resulting Topic Map may not always be as well designed as it would be in the case it had been handcrafted in many hours of work.
SesameTM is a Topic Maps engine for RDF. 
It contains a full featured implementation of the TMAPI2.0 [1] 
building on a TMDM ontology [2] proposed by Cregan,
as well as Live-Mapping implementation of the TMAPI2.0 
building on the RDF2TM [3] vocabulary proposed by Garshol.
SesameTM builds on the Sesame [4] RDF framework.


Development:
Running the following m2 command generates configuration files for your IDE:
Eclipse: 		mvn eclipse:eclipse
Netbeans: 		mvn netbeans-freeform:generate-netbeans-project
IntelliJ IDEA:	mvn idea:idea


Testing:
 - SesameTM passes the TMAPI-Test-Suite. 
	=> http://sourceforge.net/projects/tmapi/files/

 - It has been tested against the RSpec example suite in (J)RTM
	=> Optain the RSpec examples:
		wget http://rubygems.org/downloads/rtm-0.3.0.gem
		mv rtm-0.3.0.gem rtm.zip
		tar -xf rtm.zip
		tar -xf data.tar.gz
	=> Execute the examples:
		jruby -S spec
	
 - Additionally SesameTM has its own JUnit tests to verify its 
   implementation specific characteristics. 
	=> mvn test


The SesameTM RTM integration Gem is available via rubygems [6].


1) http://www.tmapi.org/2.0/
2) http://www.cse.unsw.edu.au/~annec/index2.html
3) http://psi.ontopia.net/rdf2tm/
4) http://www.openrdf.org/
5) http://sourceforge.net/projects/tmapi/files/
6) http://rubygems.org/gems/rtm-sesametm

For further information see http://code.google.com/p/sesametm
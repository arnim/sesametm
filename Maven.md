If you use [Maven](http://maven.apache.org/) or  [Apache Ivy](http://ant.apache.org/ivy/) in your [Ant](http://ant.apache.org/)-based project, you should include the following repository:

```
  <repositories>
      <repository>
          <id>tmlab</id>
          <url>http://maven.topicmapslab.de/public/</url>
      </repository>
  </repositories>

```

<br /><br />
To use the **SAIL-TMAPI** include:

```
  <dependencies>
      ...
      <dependency>
          <groupId>de.topicmapslab</groupId>
          <artifactId>sesame-sail-tmapi</artifactId>
          <version>0.0.9-SNAPSHOT</version>
      </dependency>
      ...
  </dependencies>
```


<br /><br />
To use the **Topic Maps engine** include:

```
  <dependencies>
      ...
      <dependency>
          <groupId>de.topicmapslab.sesametm</groupId>
          <artifactId>sesametm-engine</artifactId>
          <version>0.1.6-SNAPSHOT</version>
      </dependency>
      ...
  </dependencies>
```
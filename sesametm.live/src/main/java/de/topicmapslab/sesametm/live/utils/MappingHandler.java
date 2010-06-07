/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 */

package de.topicmapslab.sesametm.live.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.memory.MemoryStore;

import de.topicmapslab.sesametm.vocabularies.RTM;

/**
 * @author Arnim Bleier
 * @email bleier@informatik.uni-leipzig.de
 */

public class MappingHandler {

  private RepositoryConnection con;
  final private String baseURI = "http://psi.ontopia.net/rdf2tm/";
  private Map<Resource, Set<Resource>> mapsToMap, theType, inScope;
  private Map<Resource, Resource> subjectRole, objectRole;

  public MappingHandler(String mapping) {
    File f = new File(mapping);
    if (f.exists())
      try {
        init(new FileInputStream(f));
      } catch (FileNotFoundException e) {
        try {
          URL url = new URL(mapping);
          URLConnection uc = url.openConnection();
          InputStream is = uc.getInputStream();
          init(is);
        } catch (Exception es) {
          es.printStackTrace();
        }
      }
  }

  public MappingHandler(InputStream mapping) {
    init(mapping);
  }

  public RepositoryConnection getRepositoryConnection() {
    return con;
  }

  public Set<Resource> mapsTo(Resource key) {
    Set<Resource> res = mapsToMap.get(key);
    if (res != null) {
      return res;
    } else {
      return new HashSet<Resource>();
    }
  }

  public Set<Resource> theType(Resource key) {
    Set<Resource> res = theType.get(key);
    if (res != null) {
      return res;
    } else {
      return new HashSet<Resource>();
    }
  }

  public Set<Resource> inScope(Resource key) {
    Set<Resource> res = inScope.get(key);
    if (res != null) {
      return res;
    } else {
      return new HashSet<Resource>();
    }
  }

  public Set<Resource> getObjects(Resource subject, URI predicate) {
    Set<Resource> resources = new HashSet<Resource>();
    RepositoryResult<Statement> re;
    try {
      re = con.getStatements(subject, predicate, null, true);
      Statement s;
      while (re.hasNext()) {
        s = re.next();
        try {
          resources.add((Resource) s.getObject());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (RepositoryException e1) {
      e1.printStackTrace();
    }
    return resources;
  }

  public Set<Resource> getSubjects(URI predicate, Value object) {
    Set<Resource> resources = new HashSet<Resource>();
    RepositoryResult<Statement> re;
    try {
      re = con.getStatements(null, predicate, object, true);

      Statement s;
      while (re.hasNext()) {
        s = re.next();
        try {
          resources.add(s.getSubject());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (RepositoryException e1) {
      e1.printStackTrace();
    }
    return resources;
  }

  public Resource objectRole(Resource key) {
    return objectRole.get(key);
  }

  public Resource subjectRole(Resource key) {
    return subjectRole.get(key);
  }

  private HashMap<Resource, Set<Resource>> getMappingSets(URI predicate)
      throws Exception {
    RepositoryResult<Statement> re = con.getStatements(null, predicate, null,
        true);
    HashMap<Resource, Set<Resource>> result = new HashMap<Resource, Set<Resource>>();
    Resource object, subject;
    Set<Resource> values;
    Statement s;
    while (re.hasNext()) {
      s = re.next();
      object = (Resource) s.getObject();
      subject = s.getSubject();
      values = result.get(object);
      if (values != null) {
        values.add(subject);
      } else {
        values = new HashSet<Resource>();
        values.add(subject);
        result.put(object, values);
      }
    }
    return result;
  }

  private HashMap<Resource, Resource> getMapping(URI predicate)
      throws Exception {
    HashMap<Resource, Resource> result = new HashMap<Resource, Resource>();
    RepositoryResult<Statement> re = con.getStatements(null, predicate, null,
        true);
    Statement s;
    while (re.hasNext()) {
      s = re.next();
      try {
        result.put(s.getSubject(), (Resource) s.getObject());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  /**
   * Initializes a Repository
   */
  private void init(InputStream mapping) {
    Repository myRepository = new SailRepository(new MemoryStore());
    try {
      myRepository.initialize();
      con = myRepository.getConnection();
      con.add(mapping, baseURI, RDFFormat.N3);
      mapsToMap = getMappingSets(RTM.MAPSTO);
      subjectRole = getMapping(RTM.SUBJECTROLE);
      objectRole = getMapping(RTM.OBJECTROLE);
      inScope = getMappingSets(RTM.INSCOPE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
    }
    return sb.toString();
  }

}

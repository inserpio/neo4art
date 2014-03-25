/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.inserpio.neo4art.spatial;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.query.CypherQueryEngine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lorenzo Speranzoni
 * @since Mar 23, 2014
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
@Transactional
@EnableTransactionManagement
public class SpatialQueriesTest
{
  @Autowired
  private Neo4jTemplate neo4jTemplate;

  @Test
  public void addMuseumToSpatialIndex()
  {
    GraphDatabaseService graphDatabaseService = this.neo4jTemplate.getGraphDatabaseService();
    
    IndexManager indexManager = graphDatabaseService.index();

    Map<String, String> config = Collections.unmodifiableMap(
        MapUtil.stringMap(SpatialIndexProvider.GEOMETRY_TYPE, LayerNodeIndex.POINT_GEOMETRY_TYPE,
                          IndexManager.PROVIDER, SpatialIndexProvider.SERVICE_NAME,
                          LayerNodeIndex.WKT_PROPERTY_KEY, "wkt") );
    
    Index<Node> index = indexManager.forNodes("museumLocation", config);
    
    Iterator<Node> museums = this.neo4jTemplate.query("MATCH (m:MUSEUM) RETURN m", null).to(Node.class).iterator();

    while (museums.hasNext())
    {
      Node museum = museums.next();
      
      if (museum.hasProperty("wkt"))
      {
        System.out.println("Adding " + museum.getProperty("name") + " to museumLocation index...");
      
        index.add(museum, "dummy", "value");
      }
      else
      {
        System.out.println(museum.getProperty("name") + " NOT ADDED to museumLocation index...");
      }
    }
  }

  @Test
  public void shouldExtractNationalGallery()
  {
    GraphDatabaseService graphDatabaseService = this.neo4jTemplate.getGraphDatabaseService();
    
    IndexManager indexManager = graphDatabaseService.index();

    Map<String, Object> params = new HashMap<String, Object>();
    params.put(LayerNodeIndex.POINT_PARAMETER, new Double[]{51.5086, -0.1283});
    params.put(LayerNodeIndex.DISTANCE_IN_KM_PARAMETER, 0.1);
    
    Map<String, String> config = Collections.unmodifiableMap(
        MapUtil.stringMap(SpatialIndexProvider.GEOMETRY_TYPE, LayerNodeIndex.POINT_GEOMETRY_TYPE,
                          IndexManager.PROVIDER, SpatialIndexProvider.SERVICE_NAME,
                          LayerNodeIndex.WKT_PROPERTY_KEY, "wkt") );
    
    String geoQuery = LayerNodeIndex.WITHIN_DISTANCE_QUERY;

    Index<Node> index = indexManager.forNodes("museumLocation", config);
    Assert.assertNotNull(index);
    IndexHits<Node> hits = index.query(geoQuery, params);
    Assert.assertTrue(hits.hasNext());    
    
    CypherQueryEngine engine = this.neo4jTemplate.queryEngineFor();
    Result<Map<String,Object>> result = engine.query("start ng=node:museumLocation('withinDistance:[51.5086,-0.1283,0.1]') return ng", null);
    Assert.assertTrue(result.iterator().hasNext());
  }
}

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

package it.inserpio.neo4art.springdata;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.impl.util.StringLogger;
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
  public void shouldExtractNationalGallery()
  {
    GraphDatabaseService graphDatabaseService = this.neo4jTemplate.getGraphDatabaseService();
    
    IndexManager indexManager = graphDatabaseService.index();

    Map<String, Object> params = new HashMap<String, Object>();
    
    params.put(LayerNodeIndex.POINT_PARAMETER, new Double[]{-0.1283, 51.5086});
    params.put(LayerNodeIndex.DISTANCE_IN_KM_PARAMETER, 10.0);

    /*
    Map<String, String> config = SpatialIndexProvider.SIMPLE_WKT_CONFIG;
    Index<Node> index = indexManager.forNodes("musuemLocation", config);
    assertNotNull(index);
    IndexHits<Node> hits = index.query(LayerNodeIndex.WITHIN_DISTANCE_QUERY, params);
    assertTrue(hits.hasNext());    
    */
    
    CypherQueryEngine engine = this.neo4jTemplate.queryEngineFor();
    Result<Map<String,Object>> result = engine.query("start ng=node:musuemLocation('withinDistance:[-0.1283,51.5086,10.0]') return ng", null);
    //assertTrue(result.iterator().hasNext());
    Assert.assertTrue(result.iterator().hasNext());
  }
}

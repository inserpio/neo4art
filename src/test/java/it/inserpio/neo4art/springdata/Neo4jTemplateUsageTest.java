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

import it.inserpio.neo4art.domain.Artist;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.query.CypherQueryEngine;
import org.springframework.data.neo4j.support.typerepresentation.LabelBasedNodeTypeRepresentationStrategy;
import org.springframework.data.neo4j.support.typerepresentation.LabelBasedStrategyCypherHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * This test to analyze database structure using Neo4jTemplate
 * 
 * @author Lorenzo Speranzoni
 * @since Mar 16, 2014
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
@Transactional
@EnableTransactionManagement
public class Neo4jTemplateUsageTest
{
  @Autowired
  private Neo4jTemplate neo4jTemplate;

  @Test
  public void shouldRetrieveArtistLabel()
  {
    CypherQueryEngine cypherQueryEngine = this.neo4jTemplate.queryEngineFor();
    
    LabelBasedStrategyCypherHelper labelBasedStrategyCypherHelper = new LabelBasedStrategyCypherHelper(cypherQueryEngine);
    
    Iterator<String> labels = labelBasedStrategyCypherHelper.getLabelsForNode(0).iterator();
    
    Assert.assertTrue(labels.hasNext());
    Assert.assertEquals("ARTIST", labels.next());
    Assert.assertTrue(labels.hasNext());
    Assert.assertEquals("_ARTIST", labels.next());
  }
  
  @Test
  public void shouldRetrieveArtistNodes()
  {
    long artists = this.neo4jTemplate.count(Artist.class);
    
    Assert.assertTrue(artists > 0);
  }
  
  @Test
  public void shouldRetrieveAllLabelNames()
  {
    Collection<String> labels = this.neo4jTemplate.getGraphDatabase().getAllLabelNames();
    
    System.out.println(labels);
    
    Assert.assertTrue(labels.contains("ARTIST"));
    Assert.assertTrue(labels.contains("CITY"));
    Assert.assertTrue(labels.contains("MUSEUM"));
    Assert.assertTrue(labels.contains("STAGE"));
    Assert.assertTrue(labels.contains("ART_MOVEMENT"));
    Assert.assertTrue(labels.contains("PERSON"));
    Assert.assertTrue(labels.contains("VISUAL_ART"));
    
    Assert.assertTrue(labels.contains(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "ARTIST"));
    Assert.assertTrue(labels.contains(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "CITY"));
    Assert.assertTrue(labels.contains(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "MUSEUM"));
    Assert.assertTrue(labels.contains(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "STAGE"));
    Assert.assertTrue(labels.contains(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "ART_MOVEMENT"));
    Assert.assertTrue(labels.contains(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "PERSON"));
    Assert.assertTrue(labels.contains(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "VISUAL_ART"));
  }
  
  @Test
  public void shouldBeLabelBased()
  {
    Assert.assertTrue(this.neo4jTemplate.isLabelBased());
  }
  
  @Test
  public void shouldExecuteCypherStatementAndRetrieveVincent()
  {
    Map<String, Object> params = new HashMap<String, Object>();
    
    params.put("last_name", "Van Gogh");
    
    Node vanGogh = this.neo4jTemplate.query("MATCH (a:ARTIST {last_name: {last_name}}) RETURN a", params).to(Node.class).single();
    
    Assert.assertEquals("Vincent", vanGogh.getProperty("first_name"));
    
    Iterator<Label> labels = vanGogh.getLabels().iterator();
    
    Assert.assertTrue(labels.hasNext());
    Assert.assertEquals("ARTIST", labels.next().name());
    Assert.assertEquals("_ARTIST", labels.next().name());
    Assert.assertFalse(labels.hasNext());
  }
}

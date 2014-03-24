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

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.typerepresentation.LabelBasedNodeTypeRepresentationStrategy;
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
public class ConvertToSpringDataTest
{
  public static final String CYPHER_ADD_SDN_LABEL_TO_NODE = "match (n:`%s`) set n:`" + LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + "%s`";
  
  @Autowired
  private Neo4jTemplate neo4jTemplate;

  @Test
  public void shouldConvertToSpringData()
  {
    Collection<String> labels = this.neo4jTemplate.getGraphDatabase().getAllLabelNames();
    
    for (String label : labels)
    {
      if (!label.equals(LabelBasedNodeTypeRepresentationStrategy.SDN_LABEL_STRATEGY) && !label.startsWith(LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX))
      {
        String addSdnLabelToNodeStatement = String.format(CYPHER_ADD_SDN_LABEL_TO_NODE , label, label);
        
        System.out.println("Adding label `" + LabelBasedNodeTypeRepresentationStrategy.LABELSTRATEGY_PREFIX + label + "` for Spring Data compatibility to nodes with label `" + label + "`.");

        this.neo4jTemplate.query(addSdnLabelToNodeStatement, null);
      }
    }
  }
}

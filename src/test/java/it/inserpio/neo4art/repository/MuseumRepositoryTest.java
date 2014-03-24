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
package it.inserpio.neo4art.repository;

import it.inserpio.neo4art.domain.Museum;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * ArtistReposity test class
 * 
 * @author Lorenzo Speranzoni
 * @since 12.02.2014
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
@Transactional
@EnableTransactionManagement
public class MuseumRepositoryTest
{
  @Autowired
  private MuseumRepository museumRepository;

  @Test
  public void shouldRetrieveMuseumsHostingVanGoghArtworks()
  {
    List<Museum> museums = this.museumRepository.findMuseumByArtist(0);

    Assert.assertNotNull(museums);
    Assert.assertFalse(CollectionUtils.isEmpty(museums));
    
    System.out.println(museums.size());
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void shouldRetrieveMuseumsBySpatialLocation()
  {
    List<Museum> museums = this.museumRepository.findWithinDistance("museumLocation", 51.507222,-0.1275, 10).as(List.class);
    
    //Assert.assertNotNull(museums);
    //Assert.assertFalse(CollectionUtils.isEmpty(museums));
    
    System.out.println(museums.size());
  }
}

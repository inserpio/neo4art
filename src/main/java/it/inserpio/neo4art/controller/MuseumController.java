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
package it.inserpio.neo4art.controller;

import it.inserpio.neo4art.domain.Museum;
import it.inserpio.neo4art.repository.MuseumRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST API:
 * 
 * /museums
 * /museums/1051
 * /museums/name/National%20Gallery
 * 
 * 
 * @author Lorenzo Speranzoni
 * @since Mar 19, 2014
 */
@Controller
@RequestMapping("/museums")
public class MuseumController
{
  @Autowired
  private MuseumRepository museumRepository;
  
  /**
   * Retrieve museum by id (i.e. nodeId)
   * 
   * @param museumId
   * @return
   */
  @RequestMapping(value="/{museumId}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
  public @ResponseBody Museum getMuseum(@PathVariable long museumId)
  {
    return this.museumRepository.findOne(museumId); 
  }

  /**
   * Retrieve museum by name
   * 
   * @param name museum's name
   * @return
   */
  @RequestMapping(value="/name/{name}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
  public @ResponseBody Museum getMuseumByName(@PathVariable String name)
  {
    return this.museumRepository.findMuseumByName(name);      
  }
  
  /**
   * Find museums hosting selected artist's artworks
   * 
   * @param artistId
   * @return
   */
  @RequestMapping(value="/museums/artist/{artistId}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
  public @ResponseBody List<Museum> getMuseumsForArtist(@PathVariable long artistId)
  {
    return this.museumRepository.findMuseumByArtist(artistId); 
  }
  
  /**
   * Retrieve all museums
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(method=RequestMethod.GET, produces={"application/xml", "application/json"})
  public @ResponseBody List<Museum> getAllMuseums()
  {
    return this.museumRepository.findAll().as(List.class);
  }
}

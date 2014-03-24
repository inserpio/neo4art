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
package it.inserpio.neo4art.domain;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 * 
 * @author Lorenzo Speranzoni
 *
 */
@NodeEntity
@TypeAlias("MUSEUM")
public class Museum extends AbstractEntity
{
  @Indexed(unique=true)
  private String name;

  private String address;
  
  private String director;

  private String wikipedia;

  private String website;

  private double latitude;

  private double longitude;
  
  @Indexed(indexName="museumLocation", indexType=IndexType.POINT)
  private String wkt;
  
  public Museum()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getDirector()
  {
    return director;
  }

  public void setDirector(String director)
  {
    this.director = director;
  }

  public String getWikipedia()
  {
    return wikipedia;
  }

  public void setWikipedia(String wikipedia)
  {
    this.wikipedia = wikipedia;
  }

  public String getWebsite()
  {
    return website;
  }

  public void setWebsite(String website)
  {
    this.website = website;
  }

  public double getLatitude()
  {
    return latitude;
  }

  public void setLatitude(double latitude)
  {
    this.latitude = latitude;
    
    this.updateWkt();
  }

  public double getLongitude()
  {
    return longitude;
  }

  public void setLongitude(double longitude)
  {
    this.longitude = longitude;
        
    this.updateWkt();
  }

  /**
   * @return the wkt
   */
  public String getWkt()
  {
    return wkt;
  }

  private void updateWkt()
  {
    this.wkt = String.format("POINT( %.2f %.2f )", this.getLongitude(), this.getLatitude());
  }
  public void setWkt(double longitude, double latitude)
  {
    this.setLongitude(longitude);
    this.setLatitude(latitude);
    
    this.updateWkt();
  }
  
  @Override
  public String toString()
  {
    return "Museum [name=" + name + ", address=" + address + ", director=" + director + ", wikipedia=" + wikipedia + ", website=" + website + ", latitude=" + latitude + ", longitude=" + longitude + "]";
  }

}

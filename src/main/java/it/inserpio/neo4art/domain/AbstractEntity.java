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

import org.springframework.data.neo4j.annotation.GraphId;

/**
 * 
 * @author Lorenzo Speranzoni
 *
 */
public abstract class AbstractEntity
{
  @GraphId
  private Long id;

  public Long getId()
  {
    return id;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }

    if (id == null || obj == null || !getClass().equals(obj.getClass()))
    {
      return false;
    }
    
    return id.equals(((AbstractEntity) obj).id);
  }

  @Override
  public int hashCode()
  {
    return id == null ? 0 : id.hashCode();
  }
}

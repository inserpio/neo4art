/**
 * Copyright 2015 the original author or authors.
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

package org.neo4art.domain;

/**
 * Visual Art domain object
 * 
 * @author Lorenzo Speranzoni
 * @since 4 Mar 2015
 */
public enum VisualArt {

  DRAWING     ("Drawing"),
  PAINTING    ("Painting"),
  SCULPTURE   ("Sculpture"),
  PRINTMAKING ("Printmaking"),
  DESIGN      ("Design"),
  CRAFTS      ("Crafts"),
  PHOTOGRAPHY ("Photography"),
  VIDEO       ("Video"),
  FILMMAKING  ("Filmmaking"),
  ARCHITECTURE("Architecture");

  private final String name;

  private VisualArt(final String name) {
      this.name = name;
  }

  @Override
  public String toString() {
      return name;
  }
}

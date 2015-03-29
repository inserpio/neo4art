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
 * Art domain object
 * 
 * Major constituents of the arts include:
 * 
 * <ul>
 *   <li>@see <a href="http://en.wikipedia.org/wiki/Visual_arts">Visual Arts</a></li>
 *   
 *   <ul>
 *     <li>Drawing</li>
 *     <li>Painting</li>
 *     <li>Sculpture</li>
 *     <li>Printmaking</li>
 *     <li>Design</li>
 *     <li>Crafts</li>
 *     <li>Photography</li>
 *     <li>Video</li>
 *     <li>Filmmaking</li>
 *     <li>Architecture</li>
 *   </ul>
 *   
 *   <li>@see <a href="http://en.wikipedia.org/wiki/Performing_arts">Performing Arts</a></li>
 *   
 *   <ul>
 *     <li>Ballet</li>
 *     <li>Dance</li>
 *     <li>Music</li>
 *     <li>Opera</li>
 *     <li>Theatre</li>
 *     <li>Circus skills</li>
 *     <li>Magic</li>
 *     <li>Mime</li>
 *     <li>Puppetry</li>
 *     <li>Ventriloquism</li>
 *     <li>Speech</li>
 *   </ul>
 *   
 *   <li>@see <a href="http://en.wikipedia.org/wiki/The_arts#Literary_arts">Literary Arts</a></li>
 *   
 *   <ul>
 *     <li>Language</li>
 *     <li>Literature</li>
 *     <li>Prose</li>
 *     <li>Drama</li>
 *     <li>Poetry</li>
 *     <li>Comics</li>
 *     <li>Novel</li>
 *     <li>Short stories</li>
 *     <li>Epics</li>
 *   </ul>
 *   
 * </ul>
 * 
 * @author Lorenzo Speranzoni
 * @since 4 Mar 2015
 */
public interface Art {
  
  String getName();
}

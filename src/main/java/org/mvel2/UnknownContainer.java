/*
 * Copyright 2019 MVEL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mvel2;

/**
 * An interface that can be implemented for containers that have knowledge about
 *  what information they have and don't have, to be usable in MVEL "contains" statements.
 * @author MalcolmOdd
 */
public interface UnknownContainer {
  /**
   * Test if the given value is contained. This is expected to return either Boolean.TRUE, Boolean.FALSE 
   *   or an object that implements the Unknown interface if it is not known at this time whether the object
   *   is in the set or not.
   * @param obj The object to look for
   * @return Boolean.TRUE if the object is in the container, Boolean.FALSE if it isn't, or an "Unknown" object
   */
  Object contains(Object obj);
}

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
 * Interface tagging unknown values. This can be implemented to provide guidance for figuring out the value.
 * @author Luc Thuot
 */
public interface Unknown {
  public static final Unknown UNKNOWN = new Unknown() {
    @Override
    public String toString() {
      return "UNKNOWN";
    }
  };
}

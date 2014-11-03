/******************************************************************
 * See the NOTICE file distributed with this work for additional  *
 * information regarding Copyright ownership.  The author/authors *
 * license this file to you under the terms of the Apache License *
 * Version 2.0 (the "License"); you may not use this file except  *
 * in compliance with the License.  You may obtain a copy of the  *
 * License at:                                                    *
 *                                                                *
 *     http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                                *
 * Unless required by applicable law or agreed to in writing,     *
 * software distributed under the License is distributed on an    *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,   *
 * either express or implied.  See the License for the specific   *
 * language governing permissions and limitations under the       *
 * License.                                                       *
 ******************************************************************/

package codes.reactive.scalatime
package temporal

import util.Try

/** Enriches a [[TemporalAccessor]] with additional methods. */
final class RichTemporalAccessor(val underlying: TemporalAccessor) extends AnyVal {

  /** Queries this object using the specified [[TemporalQuery]] strategy object.
    *
    * @throws DateTimeException - if unable to query.
    * @throws ArithmeticException - if numeric overflow occurs.
    */
  def >>[A](query: TemporalQuery[A]): A = underlying.query(query)

  /** Queries this object using the specified [[TemporalQuery]] strategy object. */
  def >>?[A](query: TemporalQuery[A]): Try[A] = Try(underlying.query(query))

  /** Obtains the value of the specified field as a `Long` by querying for the value of the specified field.
    *
    * @throws DateTimeException - if a value for the field cannot be obtained.
    * @throws ArithmeticException - if numeric overflow occurs.
    */
  def >> (field: TemporalField): Long = underlying.getLong(field)

  /** Tries to obtain the value of the specified field as a `Long` by querying for the value of the specified field. */
  def >>? (field: TemporalField): Long = underlying.getLong(field)
}
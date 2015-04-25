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
package impl

import org.scalatest.{Matchers, Outcome, fixture}
import temporal.TemporalAdjuster


class TemporalOpsSuite extends fixture.FunSuite with Matchers {


  override type FixtureParam = (TemporalOps, ZonedDateTime)

  override protected def withFixture(test: OneArgTest): Outcome = {
    val temporal = ZonedDateTime.of(2015, 5, 25, 7, 6, 30, 0, ZoneId.EAT)
    withFixture(test.toNoArgTest(new FixtureParam(new TemporalOps(temporal), temporal)))
  }


  test("`+` obtains an instance of the same type as the boxed type with the TemporalAmount added") {
    _._1 + Period.days(1) shouldBe ZonedDateTime.of(2015, 5, 26, 7, 6, 30, 0, ZoneId.EAT)
  }

  test("`+` obtains an instance of the same type as the boxed type with the amount of TemporalUnits added") {
    _._1 +(1, ChronoUnit.Days) shouldBe ZonedDateTime.of(2015, 5, 26, 7, 6, 30, 0, ZoneId.EAT)
  }

  test("`-` obtains an instance of the same type as the boxed type with the TemporalAmount subtracted") {
    _._1 - Period.days(1) shouldBe ZonedDateTime.of(2015, 5, 24, 7, 6, 30, 0, ZoneId.EAT)
  }

  test("`-` obtains an instance of the same type as the boxed type with the amount of TemporalUnits subtracted") {
    _._1 -(1, ChronoUnit.Days) shouldBe ZonedDateTime.of(2015, 5, 24, 7, 6, 30, 0, ZoneId.EAT)
  }

  test("`=~` obtains an instance of the same type as the boxed type with a TemporalAdjuster applied.") { t ⇒
    (t._1 ~= TemporalAdjuster.firstDayOfMonth) shouldBe ZonedDateTime.of(2015, 5, 1, 7, 6, 30, 0, ZoneId.EAT)
  }

  test("`=~` obtains an instance of the same type as the boxed type with an adjustment applied.") { t ⇒
    (t._1 ~=(ChronoField.DayOfMonth, 1)) shouldBe ZonedDateTime.of(2015, 5, 1, 7, 6, 30, 0, ZoneId.EAT)
  }

  test("`from` obtains the amount of time until the boxed Temporal from the specified Temporal in terms of the specified units.") { t ⇒
    t._1 from(t._2.minusDays(1), ChronoUnit.Days) shouldBe 1L
  }
}

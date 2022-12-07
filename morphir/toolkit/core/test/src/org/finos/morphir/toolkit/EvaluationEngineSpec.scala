package org.finos.morphir
package toolkit

import org.finos.morphir.testing.MorphirBaseSpec

import org.finos.morphir.ir.{Type => T}
import org.finos.morphir.ir.{Value => V}

import zio.{test => _, _}
import zio.test._
import zio.test.TestAspect.{ignore, tag}
import org.finos.morphir.testing.MorphirBaseSpec
import EvaluationContext.{Variables, VariableRef}
import V._
import zio.prelude.fx._
import org.finos.morphir.ir.Type

object EvaluationEngineSpec extends MorphirBaseSpec with TypedEvaluationEngineSpec {
  def spec = suite("EvaluationEngineSpec")(typedEvaluationEngineSuite)  
}

trait TypedEvaluationEngineSpec {self: MorphirBaseSpec => 
  def typedEvaluationEngineSuite = suite("For TypedValue")(unitSuite).provide(ZLayer.succeed(EvaluationEngine.typed))

  def unitSuite: Spec[EvaluationEngine[Unit,Type.Type[Unit]] ,EvaluationError] = suite("Unit")(
    test("Should be possible to evaluate a Unit value") {
      val value:TypedValue = V.unit(T.unit)
      val context = EvaluationEngine.Context.Typed.root
      for {
        actual <- EvaluationEngine.evaluateZIO(value, context)
      } yield assertTrue(actual == ())
    }
  )
}

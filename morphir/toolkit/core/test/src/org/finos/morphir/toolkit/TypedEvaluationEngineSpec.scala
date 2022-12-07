package org.finos.morphir
package toolkit

import org.finos.morphir.testing.MorphirBaseSpec
import org.finos.morphir.ir.{Type => T}
import org.finos.morphir.ir.{Value => V}
import zio.{test => _, _}
import zio.prelude.fx._
import zio.test._
import zio.test.TestAspect.{ignore, tag}
import EvaluationEngine._
import V._

trait TypedEvaluationEngineSpec { self: MorphirBaseSpec =>
  def typedEvaluationEngineSuite =
    suite("For TypedValue")(listSuite, literalSuite, unitSuite, variableSuite).provide(
      ZLayer.succeed(EvaluationEngine.typed)
    )

  def listSuite = suite("List")(
    suite("Of Literals")(
      test("Should evaluate a List of Booleans") {
        val value: TypedValue = V.listOf(
          ir.sdk.Basics.boolType,
          V.boolean(true) :> ir.sdk.Basics.boolType,
          V.boolean(false) :> ir.sdk.Basics.boolType
        )
        val context = EvaluationEngine.Context.Typed.createRoot()
        for {
          actual <- evaluateZIO(value, context)
        } yield assertTrue(actual == List(true, false))
      },
      test("Should evaluate a List of Ints") {
        val value: TypedValue = V.listOf(
          ir.sdk.Basics.intType,
          V.int(10) :> ir.sdk.Basics.intType,
          V.int(20) :> ir.sdk.Basics.intType,
          V.int(30) :> ir.sdk.Basics.intType,
          V.int(40) :> ir.sdk.Basics.intType
        )
        val context = EvaluationEngine.Context.Typed.createRoot()
        for {
          actual <- EvaluationEngine.evaluateZIO(value, context)
        } yield assertTrue(actual == List(10, 20, 30, 40))
      }
    )
  )

  def literalSuite = suite("Literal")(
    test("Should evaluate True to true") {
      val value: TypedValue = V.boolean(true) :> ir.sdk.Basics.boolType
      val context           = EvaluationEngine.Context.Typed.createRoot()
      for {
        actual <- EvaluationEngine.evaluateZIO(value, context)
      } yield assertTrue(actual == true)
    },
    test("Should evaluate False to false") {
      val value: TypedValue = V.boolean(false) :> ir.sdk.Basics.boolType
      val context           = EvaluationEngine.Context.Typed.createRoot()
      for {
        actual <- EvaluationEngine.evaluateZIO(value, context)
      } yield assertTrue(actual == false)
    },
    test("Should evaluate a String Literal to its value") {
      check(Gen.string) { strValue =>
        val value: TypedValue = V.string(strValue) :> ir.sdk.String.stringType
        val context           = EvaluationEngine.Context.Typed.createRoot()
        for {
          actual <- EvaluationEngine.evaluateZIO(value, context)
        } yield assertTrue(actual == strValue)
      }
    }
  )

  def unitSuite: Spec[EvaluationEngine[Unit, T.Type[Unit]], EvaluationError] = suite("Unit")(
    test("Should be possible to evaluate a Unit value") {
      val value: TypedValue = V.unit(T.unit)
      val context           = EvaluationEngine.Context.Typed.createRoot()
      for {
        actual <- EvaluationEngine.evaluateZIO(value, context)
      } yield assertTrue(actual == ())
    }
  )

  def variableSuite = suite("Variable")(
    test("Should be possible to resolve a variable that has the Scala unitValue") {
      val varName = Name.fromString("testVar")
      val value   = V.variable(varName) :> T.unit
      val context = EvaluationEngine.Context.Typed.createRoot(
        Var(varName) := ()
      )
      for {
        actual <- EvaluationEngine.evaluateZIO(value, context)
        _      <- Console.printLine(s"Actual has a type of ${actual.getClass.getSimpleName}")
      } yield assertTrue(actual == ())
    },
    test("Should be possible to resolve a variable that has a Scala boolean value") {
      val trueVarName  = Name.fromString("trueVar")
      val falseVarName = Name.fromString("falseVar")
      val trueValue    = V.variable(trueVarName) :> ir.sdk.Basics.boolType
      val falseValue   = V.variable(falseVarName) :> ir.sdk.Basics.boolType

      val context = EvaluationEngine.Context.Typed.createRoot(
        Var(trueVarName)  := true,
        Var(falseVarName) := false
      )
      for {
        actualTrueValue  <- EvaluationEngine.evaluateZIO(trueValue, context)
        actualFalseValue <- EvaluationEngine.evaluateZIO(falseValue, context)
      } yield assertTrue(actualTrueValue == true, actualFalseValue == false)
    }
  )
}

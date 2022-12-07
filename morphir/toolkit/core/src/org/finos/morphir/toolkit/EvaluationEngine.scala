package org.finos.morphir
package toolkit

import ir.Value.{TypedValue, Value}
import ir.Type.UType
import Value.Folder
import org.finos.morphir.ir.{FQName, Name, Type}
import org.finos.morphir.ir.Literal.Lit
import org.finos.morphir.ir.Value.Pattern
import zio._
import org.finos.morphir.ir.Literal.Literal._
import EvaluationContext.{Variables, VariableRef}
import zio.prelude.fx._
import EvaluationEngine._

abstract class EvaluationEngine[TA, VA]
    extends Folder[scala.Unit, TA, VA, Step[TA,VA]] {
  self =>
  import EvaluationEngine._

  def evaluate(value:Value[TA,VA]):Step[TA,VA] = 
    value.foldContext(())(self)

  def applyCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      function: Step[TA, VA],
      argument: Step[TA, VA]
  ): Step[TA, VA] = visitApply(value, attributes, function, argument)

  def constructorCase(context: Unit, value: Value[TA, VA], attributes: VA, name: FQName): Step[TA, VA] =
    visitConstructor(value, attributes, name)

  def destructureCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      pattern: Pattern[VA],
      valueToDestruct: Step[TA, VA],
      inValue: Step[TA, VA]
  ): Step[TA, VA] = visitDestructure(value, attributes, pattern, valueToDestruct, inValue)

  def fieldCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      subjectValue: Step[TA, VA],
      fieldName: Name
  ): Step[TA, VA] = visitField(value, attributes, subjectValue, fieldName)

  def fieldFunctionCase(context: Unit, value: Value[TA, VA], attributes: VA, fieldName: Name): Step[TA, VA] =
    visitFieldFunction(value, attributes, fieldName)

  def ifThenElseCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      condition: Step[TA, VA],
      thenBranch: Step[TA, VA],
      elseBranch: Step[TA, VA]
  ): Step[TA, VA] = visitIfThenElse(value, attributes, condition, thenBranch, elseBranch)

  def lambdaCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      argumentPattern: Pattern[VA],
      body: Step[TA, VA]
  ): Step[TA, VA] = visitLambda(value, attributes, argumentPattern, body)

  def letDefinitionCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      valueName: Name,
      valueDefinition: (Chunk[(Name, VA, Type.Type[TA])], Type.Type[TA], Step[TA, VA]),
      inValue: Step[TA, VA]
  ): Step[TA, VA] = visitLetDefinition(value, attributes, valueName, valueDefinition, inValue)

  def letRecursionCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      valueDefinitions: Map[Name, (Chunk[(Name, VA, Type.Type[TA])], Type.Type[TA], Step[TA, VA])],
      inValue: Step[TA, VA]
  ): Step[TA, VA] = visitLetRecursion(value, attributes, valueDefinitions, inValue)

  def listCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      elements: Chunk[Step[TA, VA]]
  ): Step[TA, VA] = visitList(value, attributes, elements)

  def literalCase(context: Unit, value: Value[TA, VA], attributes: VA, literal: Lit): Step[TA, VA] =
    visitLiteral(value, attributes, literal)

  def patternMatchCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      branchOutOn: Step[TA, VA],
      cases: Chunk[(Pattern[VA], Step[TA, VA])]
  ): Step[TA, VA] = visitPatternMatch(value, attributes, branchOutOn, cases)

  def recordCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      fields: Chunk[(Name, Step[TA, VA])]
  ): Step[TA, VA] = visitRecord(value, attributes, fields)
  def referenceCase(context: Unit, value: Value[TA, VA], attributes: VA, name: FQName): Step[TA, VA] =
    visitReference(value, attributes, name)

  def tupleCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      elements: Chunk[Step[TA, VA]]
  ): Step[TA, VA] = visitTuple(value, attributes, elements)

  override def unitCase(context: Unit, value: Value[TA, VA], attributes: VA): Step[TA, VA] =
    visitUnit(value, attributes)

  override def updateRecordCase(
      context: Unit,
      value: Value[TA, VA],
      attributes: VA,
      valueToUpdate: Step[TA, VA],
      fieldsToUpdate: Map[Name, Step[TA, VA]]
  ): Step[TA, VA] = visitUpdateRecord(value, attributes, valueToUpdate, fieldsToUpdate)

  override def variableCase(context: Unit, value: Value[TA, VA], attributes: VA, name: Name): Step[TA, VA] =
    visitVariable(value, attributes, name)

  def visitApply(
      value: Value[TA, VA],
      attributes: VA,
      function: Step[TA, VA],
      argument: Step[TA, VA]
  ): Step[TA, VA] = ???

  def visitConstructor(value: Value[TA, VA], attributes: VA, name: FQName): Step[TA, VA] = ???

  def visitDestructure(
      value: Value[TA, VA],
      attributes: VA,
      pattern: Pattern[VA],
      valueToDestruct: Step[TA, VA],
      inValue: Step[TA, VA]
  ): Step[TA, VA] = ???
  def visitField(
      value: Value[TA, VA],
      attributes: VA,
      subjectValue: Step[TA, VA],
      fieldName: Name
  ): Step[TA, VA] = ???

  def visitFieldFunction(value: Value[TA, VA], attributes: VA, fieldName: Name): Step[TA, VA] = ???

  def visitIfThenElse(
      value: Value[TA, VA],
      attributes: VA,
      condition: Step[TA, VA],
      thenBranch: Step[TA, VA],
      elseBranch: Step[TA, VA]
  ): Step[TA, VA] = ???

  def visitLambda(
      value: Value[TA, VA],
      attributes: VA,
      argumentPattern: Pattern[VA],
      body: Step[TA, VA]
  ): Step[TA, VA] = ???

  def visitLetDefinition(
      value: Value[TA, VA],
      attributes: VA,
      valueName: Name,
      valueDefinition: (Chunk[(Name, VA, Type.Type[TA])], Type.Type[TA], Step[TA, VA]),
      inValue: Step[TA, VA]
  ): Step[TA, VA] = ???

  def visitLetRecursion(
      value: Value[TA, VA],
      attributes: VA,
      valueDefinitions: Map[Name, (Chunk[(Name, VA, Type.Type[TA])], Type.Type[TA], Step[TA, VA])],
      inValue: Step[TA, VA]
  ): Step[TA, VA] = ???

  def visitList(value: Value[TA, VA], attributes: VA, elements: Chunk[Step[TA, VA]]): Step[TA, VA] = ???

  def visitLiteral(value: Value[TA, VA], attributes: VA, literal: Lit): Step[TA, VA] = ???

  def visitPatternMatch(
      value: Value[TA, VA],
      attributes: VA,
      branchOutOn: Step[TA, VA],
      cases: Chunk[(Pattern[VA], Step[TA, VA])]
  ): Step[TA, VA] = ???

  def visitRecord(value: Value[TA, VA], attributes: VA, fields: Chunk[(Name, Step[TA, VA])]): Step[TA, VA] =
    ???

  def visitReference(value: Value[TA, VA], attributes: VA, name: FQName): Step[TA, VA] = ???

  def visitTuple(value: Value[TA, VA], attributes: VA, elements: Chunk[Step[TA, VA]]): Step[TA, VA] = ???

  def visitUnit(value: Value[TA, VA], attributes: VA): Step[TA, VA] = Step.succeed(())

  def visitUpdateRecord(
      value: Value[TA, VA],
      attributes: VA,
      valueToUpdate: Step[TA, VA],
      fieldsToUpdate: Map[Name, Step[TA, VA]]
  ): Step[TA, VA] = ???

  def visitVariable(value: Value[TA, VA], attributes: VA, name: Name): Step[TA, VA] = ???

}

object EvaluationEngine {

  def evaluate[TA:Tag,VA:Tag](value:Value[TA,VA]):ZStep[EvaluationEngine[TA,VA], TA,VA] = Step.serviceWithPure[EvaluationEngine[TA,VA]] { engine =>
    engine.evaluate(value)
  }

  def evaluateZIO[TA:Tag,VA:Tag](value:Value[TA,VA]):ZIO[EvaluationEngine[TA,VA] with Context[TA,VA], EvaluationError, EvalResult] = 
    ZIO.serviceWithZIO[EvaluationEngine[TA,VA]] { engine =>
      ZIO.serviceWithZIO[Context[TA,VA]] { context =>
        val runnable = engine.evaluate(value).provideService(engine).provideState(context)    
        ZIO.fromEither(runnable.runEither)
      }
    } 

  def evaluateZIO[TA:Tag,VA:Tag](value:Value[TA,VA], context:Context[TA,VA]):ZIO[EvaluationEngine[TA,VA], EvaluationError, EvalResult] = 
    evaluateZIO(value).provideSome[EvaluationEngine[TA,VA]](ZLayer.succeed(context))

  def typed:EvaluationEngine[scala.Unit, UType] = new EvaluationEngine[scala.Unit,UType] {}

  type EvalResult = Any
  type StepCompanion

  type Step[TA,VA] =  ZStep[Any, TA, VA]
  val Step = ZPure.asInstanceOf[ZPure.type with StepCompanion]

  type ZStep[-R, TA, VA] = ZPure[EngineEvent, Context[TA, VA], Context[TA, VA], R, EvaluationError, EvalResult]
  val ZStep = ZPure.asInstanceOf[ZPure.type with StepCompanion]

  implicit class StepCompanionOps(val self:ZPure.type with StepCompanion) extends AnyVal {
    def foo = ()
  }
  

  final case class Context[+TA,+VA](parent:Option[Context[TA,VA]]){ self => }
  object Context {
    def root[TA,VA]:Context[TA,VA] = Context(None)
    type Typed = Context[scala.Unit, UType]
    object Typed {
      def root:Typed = Context[scala.Unit, UType](None)
    }
  }

  final case class Variables[+TA,+VA](bindings:Map[Name, VarRef])

  sealed trait VarRef
}

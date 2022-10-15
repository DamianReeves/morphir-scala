package org.finos.morphir
package ir
package internal
package types

trait TypeModule extends AllTypeSyntax {
  final type Constructors[+A] = internal.types.Constructors[A]
  final val Constructors: internal.types.Constructors.type = internal.types.Constructors

  final type Definition[+A] = internal.types.Definition[A]
  final val Definition: internal.types.Definition.type = internal.types.Definition

  final type Field[+A] = ir.types.Field[A]
  final val Field: ir.types.Field.type = ir.types.Field

  final type Specification[+A] = internal.types.Specification[A]
  final val Specification: internal.types.Specification.type = internal.types.Specification

  final type Type[+A] = types.Type[A]
  final val Type = types.Type

  final type UConstructors = internal.types.Constructors[Any]
  final val UConstructors: internal.types.Constructors.type = internal.types.Constructors

  final type UDefinition = internal.types.Definition[Any]
  final val UDefinition: internal.types.Definition.type = internal.types.Definition

  final type UType = types.Type.UType
  final val UType = types.Type.UType

  final def collectVariables[A](tpe: Type[A]): Set[Name]    = tpe.collectVariables
  final def collectReferences[A](tpe: Type[A]): Set[FQName] = tpe.collectReferences

  final def definitionToSpecification[A](definition: Definition[A]): Specification[A] = definition.toSpecification
  final def eraseAttributes[A](typeDef: Definition[A]): UDefinition                   = typeDef.eraseAttributes

  final def mapSpecificationAttributes[A, B](f: A => B)(spec: Specification[A]): Specification[B] = spec.map(f)
  final def mapSpecificationAttributes[A](spec: Specification[A]): Specification.MapSpecificationAttributes[A] =
    Specification.mapSpecificationAttributes(spec)

  def mapDefinitionAttributes[A, B](f: A => B, defn: Definition[A]): Definition[B] = defn.map(f)

  final def mapTypeAttributes[A, B](f: A => B, tpe: Type[A]): Type[B]     = tpe.mapAttributes(f)
  final def mapTypeAttributes[A](tpe: Type[A]): Type.MapTypeAttributes[A] = Type.mapTypeAttributes(tpe)

  def toString[A](tpe: Type[A]): String  = tpe.toString
  def typeAttributes[A](tpe: Type[A]): A = tpe.attributes
}

object TypeModule extends TypeModule
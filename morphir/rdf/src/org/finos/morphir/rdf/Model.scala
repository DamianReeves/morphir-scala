package org.finos.morphir.rdf

trait Model[+F[+_], +IRI] {
  def iriRef: IriRef[IRI]
  def blankNode: F[BlankNode[IRI]]
  def literal: F[Literal[IRI]]
  def resource: F[Resource[IRI]]
  def property: F[Property[IRI]]
}

object Model {}

final case class Resource[+IRI](iri: IRI, properties: Map[IRIString, Property[IRI]]) extends RdfNode[IRI]
object Resource {}

final case class Property[+IRI](iri: IRI) extends RdfNode[IRI]
object Property {}

trait RdfNode[+IRI] {
  def iri: IRI
}

sealed trait PropertyValue
object PropertyValue {
  final case class ResourcePropertyValue[IRI](resource: Resource[IRI]) extends PropertyValue
  final case class LiteralPropertyValue[+T](literal: Literal[T])       extends PropertyValue
}

sealed trait Literal[+T]
object Literal {
  final case class TypedLiteral[+T](value: T, dataType: IriRef[Nothing]) extends Literal[T]
  final case class LanguageLiteral[+T](value: T, language: String)       extends Literal[T]
  final case class PlainLiteral[+T](value: T)                            extends Literal[T]
}

final case class BlankNode[+IRI](label: String) extends RdfNode[IRI] {
  def iri: IRI = ???
}

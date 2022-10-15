package org.finos.morphir
package ir
package internal
package types

import zio.Chunk
import org.finos.morphir.syntax.NamingSyntax
import org.finos.morphir.ir.{FQName, Name}
import org.finos.morphir.testing.MorphirBaseSpec
import zio.test._
import Type._
object FoldingTypeSpec extends MorphirBaseSpec with NamingSyntax {
  def spec = suite("FoldingTypeSpec")(
    extensibleRecordSuite,
    functionSuite,
    recordSuite,
    referenceSuite,
    sizeSuite,
    tupleSuite,
    unitSuite,
    variableSuite
  )

  def extensibleRecordSuite = suite("ExtensibleRecord")(
    suite("Misc")(
      test("When calling foldLeft it should work as expected") {
        val fieldA = field("fieldA", variable("A"))
        val fieldB = field("fieldB", variable("B"))
        val fieldC = field("fieldC", variable("C"))
        val sut    = extensibleRecord("RecordWithThreeFields", Chunk(fieldA, fieldB, fieldC))
        val result = sut.foldLeft(0) { case (acc, _) =>
          acc + 1
        }
        assertTrue(result == 4)
      }
    ),
    suite("Without Attributes")(
      test("When constructing using the constructor accepting a Name and Chunk") {
        val firstField  = field("first", variable("hello"))
        val secondField = field("second", variable("world"))
        val tupleField  = field("tupleField", tuple(variable("v3"), variable("v4")))
        val recordName  = Name.fromString("MyRecord")
        val sut         = extensibleRecord(recordName, Chunk(firstField, secondField, tupleField))
        assertTrue(sut.toString == "{ myRecord | first : hello, second : world, tupleField : (v3, v4) }")
      }
    )
  )

  def functionSuite = suite("Function")(
    suite("Misc")(
      test("When calling foldLeft it should work as expected") {
        val inputParam = variable("input")
        val outputType = variable("output")
        val sut        = function(inputParam, outputType)
        val result = sut.foldLeft(0) { case (acc, _) =>
          acc + 1
        }
        assertTrue(result == 3)
      }
    ),
    suite("Without Attributes")(
      test("testing simple non-curried function") {
        val param      = variable("Input")
        val returnType = variable("Output")
        val sut        = function(param, returnType)
        assertTrue(
          sut == Function((), param, returnType),
          sut.toString == "input -> output"
        )
      }
    ),
    suite("With Attributes")()
  )

  def recordSuite = suite("Record")(
    suite("Misc")(
      test("When calling foldLeft it should work as expected") {
        val fieldA = field("fieldA", variable("A"))
        val fieldB = field("fieldB", variable("B"))
        val fieldC = field("fieldC", variable("C"))
        val sut    = record(fieldA, fieldB, fieldC)
        val result = sut.foldLeft(0) { case (acc, _) =>
          acc + 1
        }
        assertTrue(result == 4)
      }
    ),
    suite("Without Attributes")(
      test("When constructing using the constructor accepting a Chunk of fields") {
        val firstField  = field("first", variable("hello"))
        val secondField = field("second", variable("world"))
        val tupleField  = field("tupleField", tuple(variable("v3"), variable("v4")))
        val sut         = record(Chunk(firstField, secondField, tupleField))
        assertTrue(sut.toString == "{ first : hello, second : world, tupleField : (v3, v4) }", sut.size == 6)
      }
    )
  )

  def referenceSuite = suite("Reference")(
    suite("Misc")(
      test("When calling foldLeft it should work as expected") {
        val varA = variable("a")
        val varB = variable("b")
        val varC = variable("c")
        val sut  = reference("Morphir.Sdk.Bool", varA, varB, varC)
        val result = sut.foldLeft(0) { case (acc, _) =>
          acc + 1
        }
        assertTrue(result == 4)
      }
    ),
    suite("Without Attributes")(
      test("testing construction given a FQName and Chunk of types") {
        val v1     = variable("v1")
        val v2     = variable("v2")
        val v3     = tuple(variable("v3"), variable("v4"))
        val fqn1   = FQName.fqn("packageName", "moduleName", "localName")
        val actual = reference(fqn1, Chunk(v1, v2, v3))
        assertTrue(
          actual == Reference(
            (),
            fqn1,
            Variable((), "v1"),
            Variable((), "v2"),
            Tuple((), Variable((), "v3"), Variable((), "v4"))
          ),
          actual.attributes == (),
          actual.toString() == "PackageName.ModuleName.LocalName v1 v2 (v3, v4)"
        )
      }
    ),
    suite("With Attributes")(
      test("testing construction given attributes, FQName and Chunk no type parameters") {
        val refName = pkg("packageName") % "moduleName" % "localName"
        val actual  = reference(Source.Location.default, refName)
        assertTrue(
          actual.attributes == Source.Location.default,
          actual.collectReferences == Set(refName),
          actual == Reference(Source.Location.default, refName)
        )
      }
    )
  )

  def sizeSuite = suite("size")(
    test("size of Unit") {
      val sut    = Type.unit
      val actual = sut.size
      assertTrue(actual == 1)
    },
    test("size of Variable") {
      val sut    = variable("x")
      val actual = sut.size
      assertTrue(actual == 1)
    },
    test("size of simple Reference") {
      val sut    = reference("x")
      val actual = sut.size
      assertTrue(actual == 1)
    },
    test("size of Reference with a single typeParam") {
      val sut    = Reference[Any]((), FQName.fromString("x"), Chunk(Type.Variable[Any]((), Name.fromString("y"))))
      val actual = sut.size
      assertTrue(actual == 2)
    }
  )

  def tupleSuite = suite("Tuple")(
    test("testing emptyTuple constructor") {
      val sut = Type.emptyTuple("Attributes")
      assertTrue(
        sut.size == 1,
        sut.toString == "()",
        sut.attributes == "Attributes",
        sut == Tuple("Attributes", Chunk.empty)
      )
    },
    test("testing tuple constructor when given a Chunk") {
      val helloVar = variable("hello")
      val worldVar = variable("world")
      val chunk    = Chunk(helloVar, worldVar)
      val actual   = tuple(chunk)
      assertTrue(
        actual.size == 3,
        actual.toString == "(hello, world)",
        actual.attributes == (),
        actual == Tuple((), chunk)
      )
    },
    test("testing tuple constructor when given multiple un-attributed elements") {
      val var1   = variable("one")
      val var2   = variable("two")
      val var3   = variable("three")
      val actual = tuple(var1, var2, var3)
      assertTrue(
        actual.size == 4,
        actual.toString == "(one, two, three)",
        actual.attributes == (),
        actual == Tuple((), Chunk(var1, var2, var3))
      )
    },
    test("testing tuple with attributes constructor") {
      val varA   = variable("A", "a")
      val varB   = variable("B", "b")
      val varC   = variable("C", "c")
      val actual = tuple("(a, b, c)", varA, varB, varC)
      assertTrue(
        actual.size == 4,
        actual.toString == "(a, b, c)",
        actual.attributes == "(a, b, c)",
        actual == Tuple("(a, b, c)", Chunk(varA, varB, varC))
      )
    },
    test("When calling foldLeft it should work as expected") {
      val varA = variable("a")
      val varB = variable("b")
      val varC = variable("c")
      val sut  = tuple(varA, varB, varC)
      val result = sut.foldLeft(0) { case (acc, _) =>
        acc + 1
      }
      assertTrue(result == 4)
    },
    test("When calling foldLeft with an empty tuple it should work as expected") {
      val sut = emptyTuple(())
      val result = sut.foldLeft(0) { case (acc, _) =>
        acc + 1
      }
      assertTrue(result == 1)
    },
    test("When calling foldLeft with a nested empty tuple it should work as expected") {
      val sut = tuple((), emptyTuple(()))
      val result = sut.foldLeft(0) { case (acc, _) =>
        acc + 1
      }
      assertTrue(result == 2)
    }
  )

  def unitSuite = suite("Unit")(
    test("Unit has size 1") {
      val actual = Type.unit.size
      assertTrue(actual == 1)
    },
    test("Unit toString should produce the expected result") {
      val actual = Type.unit.toString
      assertTrue(actual == "()")
    },
    test("When calling foldLeft it should work as expected") {
      val actual = Type.unit.foldLeft(0) { case (acc, _) => acc + 1 }
      assertTrue(actual == 1)
    }
  )

  def variableSuite = suite("Variable")(
    test("Variable has size 1") {
      val actual = variable("x").size
      assertTrue(actual == 1)
    },
    test("testing first variable constructor") {
      val actual = variable("FizzBuzz")
      assertTrue(
        // actual.satisfies { case Variable(_, name) => name.toString == "[fizz, buzz]" },
        actual == Variable[Any]((), "FizzBuzz"),
        actual.toString == "fizzBuzz",
        actual.size == 1
      )
    },
    test("When calling foldLeft it should work as expected") {
      val actual = Type.variable("foo").foldLeft(0) { case (acc, _) => acc + 1 }
      assertTrue(actual == 1)
    }
  )
}
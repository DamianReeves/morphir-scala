package org.finos.morphir.convert

trait From[T, Self]:
  def from(t: T): Self
object From:
  given [T, Self](using conversion: Conversion[T, Self]): From[T, Self] = conversion(_)

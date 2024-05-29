package org.finos.morphir.convert

trait Into[Self, T]:
  extension (self: Self) def into: T
object Into:
  given [Self, T](using instance: From[Self, T]): Into[Self, T] = (input: Self) => instance.from(input)

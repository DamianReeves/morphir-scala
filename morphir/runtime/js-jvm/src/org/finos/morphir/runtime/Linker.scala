package org.finos.morphir.runtime
import org.finos.morphir.convert.*
import org.finos.morphir.naming.*
import org.finos.morphir.runtime.Linker.NativeFunction0

final case class Linker private (map: Map[Linker.ImportKey, Linker.Definition]) {
  def registerHostFunction0[Result](module: QualifiedModuleName)(fn: () => Result)(using
      conversion: Into[Result, RTValue]
  ): Linker = registerHostFunction0(module)(NativeFunction0(() => conversion.into(fn())))

  def registerHostFunction0(module: QualifiedModuleName)(fn: NativeFunction0): Linker = ???

  def registerFunction1[T1, Result](module: QualifiedModuleName)(fn: T1 => Result): Linker = ???
}

object Linker:
  opaque type NativeFunction0 <: () => RTValue = () => RTValue
  object NativeFunction0:
    def apply(fn: () => RTValue): NativeFunction0 = fn
    // extension (fn: NativeFunction0) def apply(): RTValue = fn()

  type ImportKey    = NodeID
  type LinkerTarget = RTValue
  enum Definition:
    case Undefined
end Linker

enum ImportResolver:
  import Linker.*
  case ByMapping(mapping: Map[Linker.ImportKey, Linker.Definition])

  def moduleDefinition(key: ImportKey): Linker.Definition = this match
    case ByMapping(mapping) => mapping.getOrElse(key, Linker.Definition.Undefined)

package org.finos.morphir

import zio.prelude._

package object json {
  type JsonText = JsonText.Type
  object JsonText extends Newtype[String] {}
}

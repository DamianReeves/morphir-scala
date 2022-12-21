package org.finos.morphir.develop.frontend.web

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import org.scalajs.dom.Element
import slinky.core.facade._

trait ReactRoot extends js.Object {
  def render(component: ReactElement): ReactInstance
}

@js.native
@JSImport("react-dom/client", JSImport.Namespace, "ReactDOM")
object ReactDOMClient extends js.Object:
  def createRoot(target: Element): ReactRoot = js.native

package org.finos.morphir
package develop
package frontend

import org.finos.morphir.develop.frontend.components.WebApp
import org.scalajs.dom.document
import shared.model.*
import slinky.core.*
import slinky.core.facade.ReactElement
import slinky.core.facade.Hooks.*
import slinky.web.html.*


object FrontEndApp:

  def main(args: Array[String]): Unit =
    val appNode = document.getElementById("app")
    val root = frontend.web.ReactDOMClient.createRoot(appNode)
    root.render(WebApp())


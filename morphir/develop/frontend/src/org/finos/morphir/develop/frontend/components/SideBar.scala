package org.finos.morphir.develop.frontend
package components

import org.scalajs.dom._
import slinky.core._
import slinky.core.facade._
import slinky.core.facade.Hooks._
import slinky.web.ReactDOM
import slinky.web.html._
object SideBar:
  def apply() = component(Props())
  case class Props()
  private val component = FunctionalComponent[Props] { props =>
    div(
      div("Home"),
      div("Workspaces"),
      div("Packages")
    )
  }



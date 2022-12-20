package org.finos.morphir.develop.frontend.components

import slinky.core.FunctionalComponent
import slinky.web.html.div

object NavigationHeader:
  def apply() = component(Props())
  case class Props()
  private val component = FunctionalComponent[Props] { props =>
    div(
    )
  }

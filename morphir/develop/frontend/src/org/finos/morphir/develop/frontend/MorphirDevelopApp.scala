package org.finos.morphir.develop.frontend

import slinky.core.FunctionalComponent
import slinky.web.html.div

object MorphirDevelopApp:
  import components.*
  def apply(props: Props) = component(props)
  case class Props()
  private val component = FunctionalComponent[Props] { props =>
    div(
      SideBar()
    )
  }

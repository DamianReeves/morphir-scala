package org.finos.morphir.develop.frontend.components

import slinky.core.FunctionalComponent
import slinky.core.facade.ReactElement
import slinky.web.html.{div, h1}

object ContentHeader:
  def apply(): ReactElement                      = apply(title = None)
  def apply(title: String): ReactElement         = apply(title = Option(title))
  def apply(title: Option[String]): ReactElement = component(Props(title = title))
  case class Props(title: Option[String])
  private val component = FunctionalComponent[Props] { props =>
    div(
      h1(props.title)
    )
  }

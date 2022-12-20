package org.finos.morphir.develop.frontend.components

import org.finos.morphir.develop.shared.model.PageModel
import slinky.core.FunctionalComponent
import slinky.web.html.div

object PageLayout:
  def apply(model:PageModel) = component(Props(model = model))
  case class Props(model:PageModel)
  private val component = FunctionalComponent[Props] { props =>
    div(
      NavigationHeader(),
      SideBar(),
      PageContent()
    )
  }

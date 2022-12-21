package org.finos.morphir.develop.frontend.components

import org.finos.morphir.develop.shared.model.*
import slinky.core.*
import slinky.web.html.*

object PageLayout:
  def apply(pages:Vector[PageModel], selectedPage:PageModel, onPageSelectionChanged:(PageModel, PageModel) => Any = (_,_) => ()) =
    component(Props(pages = pages, selectedPage = selectedPage, onPageSelectionChanged = onPageSelectionChanged))
  case class Props(pages:Vector[PageModel], selectedPage:PageModel, onPageSelectionChanged:(PageModel,PageModel) => Any)
  private val component = FunctionalComponent[Props] { props =>
    import props._
    div(
      NavigationHeader(),
      SideBar(pages = pages, onPageSelected = newSelection => {
        if(selectedPage != newSelection) {
          println(s"Page selection changed from $selectedPage to $newSelection")
          onPageSelectionChanged(selectedPage, newSelection)
        }
      }),
      PageContent(model = selectedPage)
    )
  }

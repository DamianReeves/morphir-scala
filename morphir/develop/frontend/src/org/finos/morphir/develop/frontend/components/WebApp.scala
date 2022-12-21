package org.finos.morphir.develop.frontend.components

import org.finos.morphir.develop.frontend.components
import org.finos.morphir.develop.shared.model._
import slinky.core.*
import slinky.core.facade.Hooks.*
import slinky.core.facade.ReactElement
import slinky.web.html.*


object WebApp:
  import components.*
  def apply():ReactElement = apply(Props())
  def apply(props: Props):ReactElement = component(props)
  case class Props()
  private val component = FunctionalComponent[Props] { props =>
    val (pages, updatePages) = useState[Vector[PageModel]](Vector(PageModel.Home, PageModel.WorkspacePage(title = Some("Workspaces"))))
    val (selectedPage, updateSelectedPage) = useState[PageModel](PageModel.Home)
    div(
      PageLayout(pages = pages, selectedPage = selectedPage, onPageSelectionChanged = (previous,selected) =>{
        updateSelectedPage(selected)
      })
    )
  }

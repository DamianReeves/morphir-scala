package org.finos.morphir.develop.frontend
package components

import org.finos.morphir.develop.shared.model._
import org.scalajs.dom._
import slinky.core._
import slinky.core.facade._
import slinky.core.facade.Hooks._
import slinky.web.ReactDOM
import slinky.web.html._
object SideBar:
  def apply(pages:Vector[PageModel], onPageSelected: PageModel => Any = _ => ()) = component(Props(pages = pages, onPageSelected = onPageSelected))
  case class Props(pages:Vector[PageModel], onPageSelected: PageModel => Any)
  private val component = FunctionalComponent[Props] { props =>
    import props._
    div(
      pages.zipWithIndex.map { (page, idx) =>
        SideBarItem(title = page.title.getOrElse("N/A"), onClick = () => {
          println(s"Clicked on idx: $idx. Page is ${page.title}")
          onPageSelected(page)
        }).withKey(s"page-$idx")
      }
    )
  }

object SideBarItem:
  def apply(title:String, onClick:() => Any) = component(Props(title = title, onClick = onClick))
  final case class Props(title:String, onClick:() => Any)
  private val component = FunctionalComponent[Props] { props =>
    div(
      onClick := ( _ => props.onClick() ),
      props.title
    )
  }

package org.finos.morphir.develop
package frontend.components

import org.finos.morphir.develop.shared.SharedConfig
import shared.model.PageModel
import slinky.core.FunctionalComponent
import slinky.core.facade.Hooks.*
import slinky.core.facade.ReactElement
import slinky.web.html.*
import sttp.client3.quick.*

object PageContent:
  def apply(model:PageModel): ReactElement = apply(model, title = None)

  def apply(model:PageModel, title: String): ReactElement = apply(model, title = Option(title))

  def apply(model:PageModel, title: Option[String]): ReactElement =
    val props = Props(model = model, title = title.orElse(model.title))
    component(props)
  case class Props(model:PageModel, title: Option[String])
  private val component = FunctionalComponent[Props] { props =>
    val title = props.title.getOrElse("N/A")
    val (content, updateContent) = useState(title)
    useEffect(() => {
      def handleContentChanged(newContent:String) =
        updateContent(newContent)

      getContent(s"http://localhost:${SharedConfig.serverPort}/greet?name=${title}", handleContentChanged)
    }, Seq(title))
    div(
      ContentHeader(title = title),
      content
    )

  }

  private def getContent(uri: String, callback: String => Unit) =
    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
    simpleHttpClient
      .send(quickRequest.get(uri"$uri"))
      .map { response =>
        callback(response.body)
      }

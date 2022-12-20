package org.finos.morphir.develop.frontend

import org.finos.morphir.develop.shared.SharedConfig
import org.scalajs.dom._
import slinky.core._
import slinky.core.facade.Hooks._
import slinky.web.ReactDOM
import slinky.web.html._
import sttp.client3.quick._
object MorphirDevelopUI:
  case class Props(name:String)

  val component = FunctionalComponent[Props] { props =>
    val (label, updateLabel) = useState("N/A")
    useEffect(() =>{
      def handleLabelChange(newLabel:String) =
        updateLabel(newLabel)

      getLabel(s"http://localhost:${SharedConfig.serverPort}/greet?name=${props.name}", handleLabelChange)
    })
    div(
      h1(s"Hello, ${props.name}!"),
      div(s"$label")
    )
  }
  def apply(name:String) = component(Props(name = name))

  def make(name:String) =
    ReactDOM.render(
      MorphirDevelopUI(name = name),
      document.getElementById("app")
    )

  def getLabel(uri:String, callback: String => Unit ) =
    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
    simpleHttpClient
      .send(quickRequest.get(uri"$uri"))
      .map { response =>
        callback(response.body)
      }

  def queryBackend(
                    uri: String,
                    callback: (Node, String, String, String) => Node,
                    node: Node,
                    nodeType: String,
                  ) =
    println(s"Querying backend: $uri...")
    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
    simpleHttpClient
      .send(quickRequest.get(uri"$uri"))
      .map { response =>
        callback(node, response.body, nodeType, "")
      }

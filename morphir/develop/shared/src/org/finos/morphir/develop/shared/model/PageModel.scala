package org.finos.morphir.develop.shared.model

final case class PageModel (title:Option[String])
object PageModel:
  def apply():PageModel = PageModel(title = None)
  def apply(title:String):PageModel = PageModel(title = Option(title))

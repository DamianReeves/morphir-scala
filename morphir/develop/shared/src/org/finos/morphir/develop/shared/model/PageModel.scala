package org.finos.morphir.develop.shared.model

import org.finos.morphir.develop.shared.model.PageModel.WorkspacePage

enum PageModel extends PageModelLike:
  self =>
  case Home
  case WorkspacePage(override val title:Option[String])

  override def title: Option[String] = self match
    case Home => Some("Home")
    case WorkspacePage(title) => title

object PageModel

trait PageModelLike extends MaybeHasTitle with Product with Serializable:
  def title:Option[String]

trait MaybeHasTitle:
  def title:Option[String]
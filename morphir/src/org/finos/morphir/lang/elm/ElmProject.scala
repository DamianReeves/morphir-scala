package org.finos.morphir.lang.elm
import semver.Version

sealed trait ElmProject extends Product with Serializable
object ElmProject {
  final case class Application(properties: ElmApplication) extends ElmProject
  final case class Package(properties: ElmPackage)         extends ElmProject
}

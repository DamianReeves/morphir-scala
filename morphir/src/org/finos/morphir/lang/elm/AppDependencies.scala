package org.finos.morphir.lang.elm
import zio.json._
import semver.Version

final case class AppDependencies(direct: Map[String, Version], indirect: Map[String, Version])
object AppDependencies {
  implicit val codec: JsonCodec[AppDependencies] = DeriveJsonCodec.gen[AppDependencies]
}

package org.finos.morphir.lang.elm
import zio.json._
import zio.json.ast.Json

import semver.Version

@jsonMemberNames(KebabCase)
final case class ElmApplication(
    sourceDirectories: Vector[String],
    elmVersion: Version,
    dependencies: AppDependencies,
    testDependencies: AppDependencies,
    other: Map[String, Json]
)

object ElmApplication {
  implicit val codec: JsonCodec[ElmApplication] = DeriveJsonCodec.gen[ElmApplication]
}

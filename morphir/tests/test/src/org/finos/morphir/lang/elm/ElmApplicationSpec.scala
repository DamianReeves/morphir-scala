package org.finos.morphir.lang.elm

import org.finos.morphir._
import org.finos.morphir.testing._
import semver.Version
import zio.json._
import zio.json.ast.Json
import zio.test._

object ElmApplicationSpec extends MorphirBaseSpec with ZioJsonSpec {
  def spec = suite("ElmApplicationSpec")(
    suite("JSON")(
      jsonRoundtripSuite("It supports JSON roundtrip encoding")(
        ElmApplication(
          sourceDirectories = Vector("src"),
          elmVersion = Version(0, 19, 1),
          dependencies = AppDependencies(
            direct = Map("direct" -> Version(1, 0, 0)),
            indirect = Map("indirect" -> Version(1, 0, 0))
          ),
          testDependencies = AppDependencies(
            direct = Map("direct" -> Version(1, 0, 0)),
            indirect = Map("indirect" -> Version(1, 0, 0))
          ),
          other = Map("extra" -> Json.Arr(Json.Str("alpha"), Json.Str("beta")))
        )
      )
    )
  )
}

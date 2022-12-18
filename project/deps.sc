import mill._, scalalib._

object Deps {
  case object com {
    case object lihaoyi {
      val geny       = ivy"com.lihaoyi::geny::${Versions.geny}"
      val mainargs   = ivy"com.lihaoyi::mainargs::${Versions.`mainargs`}"
      val `os-lib`   = ivy"com.lihaoyi::os-lib::${Versions.`os-lib`}"
      val sourcecode = ivy"com.lihaoyi::sourcecode::0.3.0"
      val pprint     = ivy"com.lihaoyi::pprint::0.8.1"
    }
    case object softwaremill {
      case object common {
        val tagging = ivy"com.softwaremill.common::tagging::2.3.3"
      }
      case object sttp {
        case object client3 {
          val core = ivy"com.softwaremill.sttp.client3::core::${Versions.sttp}"
        }
      }
    }
  }
  case object dev {
    case object zio {
      val `izumi-reflect`     = ivy"dev.zio::izumi-reflect::${Versions.`izumi-reflect`}"
      val zio: Dep            = ivy"dev.zio::zio::${Versions.zio}"
      val `zio-cli`           = ivy"dev.zio::zio-cli::${Versions.`zio-cli`}"
      val `zio-http`          = ivy"dev.zio::zio-http::${Versions.`zio-http`}"
      val `zio-json`: Dep     = ivy"dev.zio::zio-json::${Versions.`zio-json`}"
      val `zio-json-golden`   = ivy"dev.zio::zio-json-golden::${Versions.`zio-json`}"
      val `zio-prelude`       = ivy"dev.zio::zio-prelude::${Versions.`zio-prelude`}"
      val `zio-process`       = ivy"dev.zio::zio-process::${Versions.`zio-process`}"
      val `zio-schema`        = ivy"dev.zio::zio-streams::${Versions.`zio-schema`}"
      val `zio-streams`       = ivy"dev.zio::zio-streams::${Versions.zio}"
      val `zio-test`          = ivy"dev.zio::zio-test::${Versions.zio}"
      val `zio-test-magnolia` = ivy"dev.zio::zio-test-magnolia::${Versions.zio}"
      val `zio-test-sbt`      = ivy"dev.zio::zio-test-sbt::${Versions.zio}"
    }
  }
  case object io {
    case object bullet {
      def `borer-core`(scalaVersion: String): Dep = ivy"io.bullet::borer-core::${Versions.borer(scalaVersion)}"
      def `borer-core`(scalaVersionParts: Seq[String]): Dep =
        ivy"io.bullet::borer-core::${Versions.borer(scalaVersionParts)}"

      def `borer-derivation`(scalaVersion: String): Dep =
        ivy"io.bullet::borer-derivation::${Versions.borer(scalaVersion)}"

      def `borer-derivation`(scalaVersionParts: Seq[String]): Dep =
        ivy"io.bullet::borer-derivation::${Versions.borer(scalaVersionParts)}"
    }
    case object `get-coursier` {
      val coursier = ivy"io.get-coursier::coursier::${Versions.coursier}"
    }
    case object lemonlabs {
      val `scala-uri` = ivy"io.lemonlabs::scala-uri:4.0.3"
    }
  }

  case object me {
    case object shadaj {
      case object slinky {
        val core = ivy"me.shaday::slinky-core::${Versions.slinky}"
        val hot = ivy"me.shaday::slinky-hot::${Versions.slinky}"
        val native = ivy"me.shaday::slinky-native::${Versions.slinky}"
        val web = ivy"me.shaday::slinky-web::${Versions.slinky}"
      }
    }
  }

  case object org {
    case object `scala-lang` {
      def `scala3-compiler`(scalaVersion: String): Dep = ivy"org.scala-lang::scala3-compiler:$scalaVersion"
    }
    case object scalameta {
      val munit: mill.scalalib.Dep = ivy"org.scalameta::munit::${Versions.munit}"
      println(s"$munit")
      val `munit-scalacheck` =
        ivy"org.scalameta::munit-scalacheck::${Versions.munit}"

    }

    case object typelevel {
      val `paiges-core` = ivy"org.typelevel::paiges-core::${Versions.paiges}"
    }
  }
}

object Versions {

  def borer(scalaVersion: String): String =
    borer(scalaVersion.split('.'))

  def borer(scalaVersionParts: Seq[String]): String =
    scalaVersionParts match {
      case Seq("3", _, _)    => "1.10.1"
      case Seq("2", "13", _) => "1.8.0"
      case _                 => "1.6.3"
    }

  def semanticDb(partialVersion: Option[(Int, Int)]): String =
    partialVersion match {
      case Some((2, _)) => "4.5.11"
      case _            => "4.5.11"
    }

  val coursier        = "2.1.0-RC3-1"
  val geny            = "1.0.0"
  val `izumi-reflect` = "2.2.2"
  val munit         = "1.0.0-M4"
  val mainargs      = "0.3.0"
  val `os-lib`      = "0.9.0"
  val paiges        = "0.4.2"
  val slinky        = "0.7.2"
  val sttp          = "3.8.5"
  val zio           = "2.0.5"
  val `zio-cli`     = "0.3.0-M02"
  val `zio-http`    = "0.0.3"
  val `zio-json`    = "0.4.2"
  val `zio-prelude` = "1.0.0-RC16"
  val `zio-process` = "0.7.1"
  val `zio-schema`  = "0.4.1"
}

object ScalaVersions {
  val all      = Seq(scala213, scala3x)
  def scala213 = "2.13.10"
  def scala3x  = "3.2.1"

  val scalajs = "1.12.0"
}

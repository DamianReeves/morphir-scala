package org.finos.morphir
package develop
package frontend

object FrontEndApp:

  def main(args: Array[String]): Unit =
    MorphirDevelopUI.make(name = "Slinky")

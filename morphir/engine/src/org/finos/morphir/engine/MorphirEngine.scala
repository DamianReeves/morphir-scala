package org.finos.morphir.engine
import com.dokar.quickjs.*
import kotlinx.coroutines.Dispatchers

trait MorphirEngine {}

object Demo {
  def main(args: Array[String]): Unit =
    // val quickJs = new QuickJs().create(Dispatchers.getDefault())
    println("Hello, world!")
}

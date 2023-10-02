package org.finos.morphir.rdf.nquads

sealed trait NQuadsAST

object NQuadsAST {
  final case class NQuadsDocument()  extends NQuadsAST
  final case class NQuadsStatement() extends NQuadsAST

  sealed trait Subject extends NQuadsAST
  

}

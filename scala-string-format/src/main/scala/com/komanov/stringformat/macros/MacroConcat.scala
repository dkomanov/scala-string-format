package com.komanov.stringformat.macros

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

object MacroConcat {

  implicit class SuperFastInterpolator(sc: StringContext) {
    def sfi(args: Any*): String = macro sfiImpl
  }

  def sfiImpl(c: Context)(args: c.Expr[Any]*): c.Expr[String] = {
    import c.universe.{Name => _, _}

    // helper functions

    def callAppend(expressions: List[c.universe.Tree]): c.universe.Tree = {
      if (expressions.isEmpty) {
        q"new StringBuilder(len)"
      } else {
        Apply(Select(callAppend(expressions.tail), TermName("append")), List(expressions.head))
      }
    }

    def plusLen(const: Int, names: List[TermName]): c.universe.Tree = {
      if (names.isEmpty) {
        Literal(Constant(const))
      } else {
        Apply(Select(Select(Ident(names.head), TermName("length")), TermName("$plus")), List(plusLen(const, names.tail)))
      }
    }

    val rawParts = c.prefix.tree match {
      case Apply(_, List(Apply(_, list))) => list
    }

    val rawPartsString = rawParts.map {
      case Literal(Constant(rawPart: String)) => StringContext.treatEscapes(rawPart)
    }

    if (args.isEmpty) {
      return c.Expr(Literal(Constant(rawPartsString.mkString(""))))
    }

    var initialLength = rawPartsString.map(_.length).sum

    val (valDeclarations, lenNames, arguments) = args.zipWithIndex.map {
      case (e, index) =>
        val name = TermName("__local" + index)
        e.actualType match {
          case tt if tt.typeSymbol.asClass.isPrimitive =>
            // A kind of optimization to not calculate primitive length in advance, let the StringBuilder
            // to deal with primitive toString (it's better than i.e. Int.toString static method).
            initialLength += 9
            (Nil, Nil, e.tree)
          case tt if tt <:< typeOf[CharSequence] =>
            val expr =
              q"""
                val $name = {
                  val tmp = $e
                  if (tmp eq null) "null" else tmp
               }"""
            (List(expr), List(name), Ident(name))
          case _ =>
            val expr =
              q"""
                val $name = {
                  val tmp = $e
                  if (tmp eq null) "null" else tmp.toString
               }"""
            (List(expr), List(name), Ident(name))
        }
    }.unzip3

    val allParts: List[c.universe.Tree] = rawPartsString.map(s => Literal(Constant(s))).zipAll(arguments, null, null).flatMap {
      case (raw, null) => List(raw)
      case (raw, arg) if raw != null => List(raw, arg)
    }

    // code generation

    val plusLenExpr = plusLen(initialLength, lenNames.flatten.toList)

    val stats = valDeclarations.flatten.toList ++
      List(q"val len = $plusLenExpr") ++
      List(Apply(Select(callAppend(allParts.reverse), TermName("toString")), Nil))

    c.Expr(
      q"..$stats"
    )
  }

}

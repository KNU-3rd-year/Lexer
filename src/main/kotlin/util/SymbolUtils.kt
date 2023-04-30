package util

import token.Operator
import token.Punctuation
import token.Token

val characters = listOf('\'', '+', '-', '*', '/', '=', '<', '>', '[', ']', '.', ',', '(', ')', ':', '^', '@',
    '{', '}', '$', '#', '&', '%', '|', ';')

val operators = mapOf(
    ":=" to Operator.Assign,
    "&" to Operator.BitAnd,
    "|" to Operator.BitOr,
    "/" to Operator.Divide,
    "=" to Operator.Equal,
    "<=" to Operator.EqualLess,
    ">=" to Operator.EqualMore,
    "<" to Operator.Less,
    "-" to Operator.Minus,
    "%" to Operator.Mod,
    ">" to Operator.More,
    "*" to Operator.Multiply,
    "<>" to Operator.NotEqual,
    "+" to Operator.Plus,
)

val punctuation = mapOf(
    "[" to Punctuation.BracketL,
    "]" to Punctuation.BracketR,
    ":" to Punctuation.Colon,
    "," to Punctuation.Comma,
    "." to Punctuation.Dot,
    "(" to Punctuation.ParenthesesL,
    ")" to Punctuation.ParenthesesR,
    ";" to Punctuation.Semicolon,
)

fun Char.isSymbol(): Boolean = characters.contains(this)
fun String.isSymbolToken(): Boolean = operators.containsKey(this) || punctuation.containsKey(this)
fun String.toSymbolTokenOrNull(): Token.Symbol? = operators[this] ?: punctuation[this]
fun Token.Symbol.getString(): String {
    val map = operators.plus(punctuation)
    return map.keys.first { this == map[it] }
}
package util

import token.*

val reservedWords = mapOf(
    "and" to And,
    "begin" to Begin,
    "const" to Const,
    "do" to Do,
    "else" to Else,
    "end" to End,
    "for" to For,
    "if" to If,
    "not" to Not,
    "or" to Or,
    "program" to Program,
    "then" to Then,
    "var" to Var,
    "while" to While,
)

fun String.isReservedWord(): Boolean = reservedWords.contains(this.lowercase())
fun String.toReservedWordTokenOrNull(): Token.ReservedWord? = reservedWords[this.lowercase()]
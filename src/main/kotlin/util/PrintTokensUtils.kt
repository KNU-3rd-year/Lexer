package util

import token.Operator
import token.Punctuation
import token.Token

fun Token.toPrintableString(): String {
    return when (this) {
        is Token.ReservedWord -> "Token ReservedWord: ${this::class.simpleName}"
        is Token.Identifier -> "Token Identifier: $name"
        is Operator -> "Token Operator: ${this::class.simpleName}"
        is Punctuation -> "Token Punctuation: ${this::class.simpleName}"
        is Token.Comment -> "Token Comment (${this::class.simpleName}): $commentedText"
        is Token.Constant -> "Token Constant (${this::class.simpleName}): $value"
        is Token.Error -> "Token Error at [$line:$column]: $reason"
    }
}
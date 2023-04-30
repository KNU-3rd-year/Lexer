package token

import token.Token.*

sealed interface Operator : Symbol {
    object Assign : Operator
    object BitAnd : Operator
    object BitOr : Operator
    object Divide : Operator
    object Equal : Operator
    object EqualLess : Operator
    object EqualMore : Operator
    object Less : Operator
    object Minus : Operator
    object Mod : Operator
    object More : Operator
    object Multiply : Operator
    object NotEqual : Operator
    object Plus : Operator
}

sealed interface Punctuation : Symbol {
    object BracketL : Punctuation
    object BracketR : Punctuation
    object Colon : Punctuation
    object Comma : Punctuation
    object Dot : Punctuation
    object ParenthesesL : Punctuation
    object ParenthesesR : Punctuation
    object Semicolon : Punctuation
}
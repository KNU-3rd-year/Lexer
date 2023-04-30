package token

import token.Token.*

class IntegerNumber(value: String) : Constant(value)
class FloatNumber(value: String) : Constant(value)
class CharacterString(value: String) : Constant(value)
sealed class PascalBoolean(value: String) : Constant(value) {
    object PascalTrue : PascalBoolean("true")
    object PascalFalse : PascalBoolean("false")
}
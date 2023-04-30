package util

import lexer.PascalCharacterReader
import state_machine.State
import token.*

fun getFinalErrorState(comment: String, getPosition: () -> PascalCharacterReader.ReaderPosition): State.Final {
    val position = getPosition()
    return State.Final(token = Token.Error(comment, position.line, position.column))
}

fun Char.isHexDigit(): Boolean = ('a'..'f').contains(this.lowercaseChar())
fun Char.lowercaseChar(): Char = this.lowercase().first()

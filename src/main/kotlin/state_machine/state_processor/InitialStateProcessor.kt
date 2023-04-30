package state_machine.state_processor

import lexer.PascalCharacterReader
import state_machine.*
import token.CharacterString
import token.IntegerNumber
import token.MultilineBrace
import token.Token
import util.getFinalErrorState
import util.isSeparator
import util.isSymbol
import util.toSymbolTokenOrNull

class InitialStateProcessor(
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<State.Initial> {

    override fun process(state: State.Initial, character: Char?): State {
        val char = character ?: throw IllegalStateException("Can't process a token if no code.")

        return when {
            char.isSeparator() -> state
            char.isLetter() -> State.Identifier(token = Token.Identifier(char.toString()))
            char.isDigit() -> State.Constant(token = IntegerNumber(char.toString()))
            char.isSymbol() -> {
                when (char) {
                    '\'' -> State.Constant(token = CharacterString(char.toString()))
                    '{' -> State.Comment(token = MultilineBrace(commentedText = ""))
                    '$' -> State.Constant(token = IntegerNumber(value = "$"))
                    else -> State.Symbol(token = char.toString().toSymbolTokenOrNull()!!)
                }
            }
            else -> getFinalErrorState("Unexpected character \"$char\"", getPosition = getPosition)
        }
    }
}
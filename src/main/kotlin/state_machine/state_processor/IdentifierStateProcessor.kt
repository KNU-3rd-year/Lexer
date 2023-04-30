package state_machine.state_processor

import lexer.PascalCharacterReader
import state_machine.*
import token.PascalBoolean
import token.Token
import util.*

class IdentifierStateProcessor(
    val gotoPrevChar: () -> Unit,
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<State.Identifier> {

    override fun process(state: State.Identifier, character: Char?): State {
        val char = character ?: return State.Final(token = convertToToken(state.token.name))

        return when {
            char.isSeparator() -> State.Final(token = convertToToken(state.token.name))
            char.isLetter() -> addCharToIdentifierName(state, char)
            char.isDigit() -> addCharToIdentifierName(state, char)
            char.isSymbol() -> {
                gotoPrevChar()
                State.Final(token = convertToToken(state.token.name))
            }
            else -> getFinalErrorState("Unexpected character \"$char\"", getPosition = getPosition)
        }
    }

    private fun convertToToken(identifierValue: String): Token {
        return when {
            identifierValue.isReservedWord() -> identifierValue.toReservedWordTokenOrNull()!!
            identifierValue == "true" -> PascalBoolean.PascalTrue
            identifierValue == "false" -> PascalBoolean.PascalFalse
            else -> Token.Identifier(name = identifierValue)
        }
    }

    private fun addCharToIdentifierName(state: State.Identifier, char: Char): State.Identifier {
        val newIdentifier = state.token.name.plus(char)
        return State.Identifier(token = Token.Identifier(newIdentifier))
    }
}
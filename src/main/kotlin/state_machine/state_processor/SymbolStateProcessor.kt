package state_machine.state_processor

import lexer.PascalCharacterReader
import state_machine.*
import token.MultilineBrace
import token.MultilineParentheses
import token.SingleLine
import util.*

class SymbolStateProcessor(
    val gotoPrevChar: () -> Unit,
    val readUntilCurrentLineEnd: () -> String,
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<State.Symbol> {

    override fun process(state: State.Symbol, character: Char?): State {
        val char = character ?: return State.Final(token = state.token)

        return when {
            char.isSeparator() -> State.Final(token = state.token)
            char.isLetter() || char.isDigit() -> {
                gotoPrevChar()
                State.Final(token = state.token)
            }
            char.isSymbol() -> {
                val tokenString = state.token.getString()
                val newTokenString = tokenString.plus(char)
                when {
                    newTokenString.isSymbolToken() -> State.Symbol(token = newTokenString.toSymbolTokenOrNull()!!)
                    newTokenString.isCommentOpening() || newTokenString == commentSingleLine -> convertOpeningCommentToToken(newTokenString)
                    else -> {
                        gotoPrevChar()
                        State.Final(token = state.token)
                    }
                }
            }
            else -> getFinalErrorState("Unexpected character \"$char\"", getPosition = getPosition)
        }
    }

    private fun convertOpeningCommentToToken(openingComment: String): State {
        require(openingComment.isCommentOpening() || openingComment == commentSingleLine)

        return when (openingComment) {
            "//" -> State.Final(token = SingleLine(commentedText = readUntilCurrentLineEnd()))
            "{" -> State.Comment(token = MultilineBrace(commentedText = ""))
            "(*" -> State.Comment(token = MultilineParentheses(commentedText = ""))
            else -> throw IllegalStateException("Comment starts with $openingComment but //, { or (* was expected.")
        }
    }
}
package state_machine.state_processor

import lexer.PascalCharacterReader
import state_machine.State
import state_machine.StateProcessor
import util.getFinalErrorState
import token.MultilineBrace
import token.MultilineParentheses
import token.SingleLine

class CommentStateProcessor(
    val getNextChar: () -> Char,
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<State.Comment> {

    override fun process(state: State.Comment, character: Char?): State {
        require(state.token !is SingleLine)

        val char = character ?: return if (state.token is SingleLine) {
            State.Final(token = state.token)
        } else {
            getFinalErrorState("Multiline comment is not closed.", getPosition = getPosition)
        }

        return when (char) {
            '}' -> {
                if (state.token is MultilineBrace) {
                    State.Final(token = state.token)
                } else {
                    addStringToCurrentState(state, char.toString())
                }
            }
            '*' -> {
                val nextChar = getNextChar()
                if (nextChar == ')' && state.token is MultilineParentheses) {
                    State.Final(token = state.token)
                } else {
                    addStringToCurrentState(state, char.plus(nextChar.toString()))
                }
            }
            else -> addStringToCurrentState(state, char.toString())
        }
    }

    private fun addStringToCurrentState(state: State.Comment, string: String): State.Comment {
        val newCommentedText = state.token.commentedText + string
        return when (state.token) {
            is MultilineBrace -> State.Comment(token = MultilineBrace(commentedText = newCommentedText))
            is MultilineParentheses -> State.Comment(token = MultilineParentheses(commentedText = newCommentedText))
            else -> throw IllegalAccessException()
        }
    }
}
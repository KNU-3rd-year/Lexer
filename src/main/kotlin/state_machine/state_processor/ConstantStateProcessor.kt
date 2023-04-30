package state_machine.state_processor

import lexer.PascalCharacterReader
import state_machine.*
import token.CharacterString
import token.FloatNumber
import token.IntegerNumber
import util.*
import kotlin.text.lowercaseChar

class ConstantStateProcessor(
    val gotoPrevChar: () -> Unit,
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<State.Constant> {
    private val characterStringStateProcessor = CharacterStringStateProcessor(getPosition)
    private val floatNumberStateProcessor = FloatNumberStateProcessor(gotoPrevChar, getPosition)
    private val integerNumberStateProcessor = IntegerNumberStateProcessor(gotoPrevChar, getPosition)

    override fun process(state: State.Constant, character: Char?): State {
        return when (state.token) {
            is CharacterString -> characterStringStateProcessor.process(state.token, character)
            is FloatNumber -> floatNumberStateProcessor.process(state.token, character)
            is IntegerNumber -> integerNumberStateProcessor.process(state.token, character)
            else -> throw IllegalAccessException()
        }
    }
}

class CharacterStringStateProcessor(
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<CharacterString> {

    override fun process(token: CharacterString, character: Char?): State {
        val char = character ?: return getFinalErrorState("Character string is not closed.", getPosition = getPosition)

        return when (char) {
            '\'' -> State.Final(token = token)
            '\n' -> getFinalErrorState("Character string is not closed.", getPosition = getPosition)
            else -> State.Constant(token = CharacterString(value = token.value + char))
        }
    }
}

class IntegerNumberStateProcessor(
    val gotoPrevChar: () -> Unit,
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<IntegerNumber> {

    override fun process(token: IntegerNumber, character: Char?): State {
        val char = character ?: return State.Final(token = token)

        return when {
            char.isSeparator() -> State.Final(token = token)
            char.isLetter() -> {
                when {
                    token.value.startsWith('$') && char.isHexDigit() -> State.Constant(token = IntegerNumber(value = token.value + char)) // $ indicates that the number is in the base16
                    char.lowercaseChar() == 'e' -> State.Constant(token = FloatNumber(value = token.value + char))
                    else -> getFinalErrorState("Unexpected character \"$char\"", getPosition = getPosition)
                }
            }
            char.isDigit() -> State.Constant(token = IntegerNumber(value = token.value + char))
            char.isSymbol() -> {
                if (char == '.') {
                    State.Constant(token = FloatNumber(value = token.value + char))
                } else {
                    gotoPrevChar()
                    State.Final(token = token)
                }
            }
            else -> getFinalErrorState("Unexpected character \"$char\"", getPosition = getPosition)
        }
    }
}

class FloatNumberStateProcessor(
    val gotoPrevChar: () -> Unit,
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<FloatNumber> {

    override fun process(token: FloatNumber, character: Char?): State {
        val char = character ?: return State.Final(token = token)

        return when {
            char.isSeparator() -> State.Final(token = token)
            char.isDigit() -> State.Constant(token = FloatNumber(value = token.value + char))
            char.isLetter() || char.isSymbol() -> when {
                char.lowercase().first() == 'e' -> {
                    if (token.value.lowercase().contains('e')) {
                        getFinalErrorState("There can be only one scale factor (e/E) in a real number.", getPosition)
                    } else {
                        State.Constant(token = FloatNumber(value = token.value + char))
                    }
                }
                char == '-' && token.value.lowercase().last() == 'e' -> State.Constant(token = FloatNumber(value = token.value + char))
                else -> {
                    gotoPrevChar()
                    State.Final(token = token)
                }
            }
            else -> getFinalErrorState("Unexpected character \"$char\"", getPosition = getPosition)
        }
    }
}

/*
class PascalBooleanStateProcessor(
    val getPosition: () -> PascalCharacterReader.ReaderPosition,
) : StateProcessor<PascalBoolean> {
    override fun process(token: PascalBoolean, character: Char?): State {
        TODO("Not yet implemented")
    }
}*/

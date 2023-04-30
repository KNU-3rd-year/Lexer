package state_machine

import lexer.StateMachine
import lexer.PascalCharacterReader
import state_machine.state_processor.*
import token.Token

class StateMachineImpl(
    private val reader: PascalCharacterReader,
) : StateMachine {
    private val commentStateProcessor = CommentStateProcessor(reader::getNext, reader::getPosition)
    private val constantStateProcessor = ConstantStateProcessor(gotoPrevChar = reader::gotoPrevChar, reader::getPosition)
    private val identifierStateProcessor = IdentifierStateProcessor(gotoPrevChar = reader::gotoPrevChar, reader::getPosition)
    private val initialStateProcessor = InitialStateProcessor(reader::getPosition)
    private val symbolStateProcessor = SymbolStateProcessor(gotoPrevChar = reader::gotoPrevChar, reader::readUntilCurrentLineEnd, reader::getPosition)

    override fun parse(): Token {
        var state: State = State.Initial
        while (state !is State.Final) {
            state = when (state) {
                is State.Initial -> initialStateProcessor.process(state, reader.getNextOrNull())

                is State.Identifier -> identifierStateProcessor.process(state, reader.getNextOrNull())
                is State.Symbol -> symbolStateProcessor.process(state, reader.getNextOrNull())
                is State.Comment -> commentStateProcessor.process(state, reader.getNextOrNull())
                is State.Constant -> constantStateProcessor.process(state, reader.getNextOrNull())

                is State.Final -> throw IllegalStateException("Final state is terminate and should never be reachable")
            }
        }
        return state.token
    }
}
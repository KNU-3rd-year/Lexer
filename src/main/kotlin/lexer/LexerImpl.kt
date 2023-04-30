package lexer

import main.Lexer
import state_machine.StateMachineImpl
import token.Token

class LexerImpl : Lexer {
    override fun parce(reader: PascalCharacterReader): List<Token> {
        val stateMachine: StateMachine = StateMachineImpl(reader)
        val tokens: MutableList<Token> = mutableListOf()
        while (true) {
            val token = stateMachine.parse()
            tokens.add(token)

            if (token is Token.Error || !reader.hasNext()) {
                break
            }
        }
        return tokens
    }
}
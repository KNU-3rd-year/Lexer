package lexer

import token.Token

interface StateMachine {
    fun parse(): Token
}
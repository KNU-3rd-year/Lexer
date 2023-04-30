package main

import lexer.PascalCharacterReader
import token.Token

interface Lexer {
    fun parce(reader: PascalCharacterReader): List<Token>
}
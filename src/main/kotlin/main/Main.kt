package main

import lexer.LexerImpl
import reader.PascalCharacterReaderImpl
import util.toPrintableString

fun main(args: Array<String>) {
    val fileName = "HelloWorld.pas"
    val lexer: Lexer = LexerImpl()
    val tokens = lexer.parce(PascalCharacterReaderImpl(fileName))
    tokens.forEach {
        println(it.toPrintableString())
    }
}
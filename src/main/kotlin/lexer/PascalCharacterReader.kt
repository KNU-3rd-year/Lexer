package lexer

interface PascalCharacterReader {
    fun hasNext(): Boolean
    fun getNextOrNull(): Char?
    fun getNext(): Char
    fun readUntilCurrentLineEnd(): String

    fun hasPrev(): Boolean
    fun gotoPrevChar() // return to the previous position if possible

    fun getPosition(): ReaderPosition

    data class ReaderPosition(
        val line: Int,
        val column: Int,
    )
}
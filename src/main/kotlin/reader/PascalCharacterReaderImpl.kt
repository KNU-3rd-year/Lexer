package reader

import lexer.PascalCharacterReader
import java.io.File

class PascalCharacterReaderImpl(fileName: String) : PascalCharacterReader {
    private val file = File(fileName)
    private val lines = file.readLines()
    private var currentLine = 0
    private var currentColumn = 0

    override fun hasNext(): Boolean {
        if (currentLine == lines.size) {
            return false
        }

        val isNextCharInLine = currentColumn < lines[currentLine].length
        val isNextNotEmptyLine = currentLine < lines.size //&& lines.getOrNull(currentLine + 1)?.isNotEmpty() ?: false
        return isNextCharInLine || isNextNotEmptyLine
    }

    override fun getNextOrNull(): Char? {
        return if (hasNext()) {
            getNext()
        } else {
            null
        }
    }

    override fun getNext(): Char {
        val isNextCharInLine = currentColumn < lines[currentLine].length
        return if (isNextCharInLine) {
            val char = lines[currentLine][currentColumn]
            currentColumn++
            char
        } else {
            currentLine += 1
            currentColumn = 0
            '\n'
        }
    }

    override fun readUntilCurrentLineEnd(): String {
        val substring = lines[currentLine].substring(currentColumn)
        currentLine += 1
        currentColumn = 0
        return substring
    }

    override fun hasPrev(): Boolean {
        return currentColumn > 0 || currentLine > 0
    }

    override fun gotoPrevChar() {
        if (currentColumn > 0) {
            currentColumn--
        } else if (currentLine > 0) {
            currentLine--
            currentColumn = lines[currentLine].length - 1
        }
    }

    override fun getPosition(): PascalCharacterReader.ReaderPosition {
        return PascalCharacterReader.ReaderPosition(
            line = currentLine,
            column = currentColumn,
        )
    }
}
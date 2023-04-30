package util

val separators = listOf(' ', '\n', '\t')

fun Char.isSeparator(): Boolean = separators.contains(this)
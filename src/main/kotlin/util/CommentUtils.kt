package util

val commentOpening = listOf("(*", "{")
val commentClosing = listOf("*)", "}")
val commentSingleLine = "//"

fun String.isCommentOpening(): Boolean = commentOpening.contains(this)
fun String.isCommentClosing(): Boolean = commentClosing.contains(this)
fun String.isCommentSingleLine(): Boolean = commentSingleLine == this
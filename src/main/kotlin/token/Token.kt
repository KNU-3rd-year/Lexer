package token

/**
 * Tokens are the basic lexical building blocks of source code:
 * they are the “words” of the language: characters are combined into tokens according to the rules of the programming language.
 * There are five classes of tokens:
 * - reserved words (These are words which have a fixed meaning in the language. They cannot be changed or redefined.)
 * - identifiers (These are names of symbols that the programmer defines. They can be changed and re-used. They are subject to the scope rules of the language.)
 * - operators (These are usually symbols for mathematical or other operations: +, -, * and so on. Together with punctuation tokens they are a sub-category of symbol.)
 * - separators (This is usually white-space. In this lexer program this is comments)
 * - constants (Numerical or character constants are used to denote actual values in the source code, such as 1 (integer constant) or 2.3 (float constant) or “String constant” (a string: a piece of text).)
 */
sealed interface Token {
    /**
     * Reserved words are part of the Pascal language, and as such, cannot be redefined by the programmer. Throughout the syntax diagrams they will be denoted using a bold typeface. Pascal is not case sensitive so the compiler will accept any combination of upper or lower case letters for reserved words.
     */
    sealed interface ReservedWord : Token

    /**
     * Identifiers denote programmer defined names for specific constants, types, variables, procedures and functions, units, and programs. All programmer defined names in the source code – excluding reserved words – are designated as identifiers.
     * Identifiers consist of between 1 and 127 significant characters (letters, digits and the underscore character), of which the first must be a letter (a–z or A–Z), or an underscore (_). The following diagram gives the basic syntax for identifiers.
     *
     * Like Pascal reserved words, identifiers are case insensitive.
     *
     * As of version 2.5.1 it is possible to specify a reserved word as an identifier by prepending it with an ampersand (&).
     */
    data class Identifier(val name: String) : Token

    sealed interface Symbol : Token

    /**
     * Comments are pieces of the source code which are completely discarded by the compiler. They exist only for the benefit of the programmer, so he can explain certain pieces of code. For the compiler, it is as if the comments were not present.
     */
    sealed class Comment(val commentedText: String) : Token

    /**
     * Numerical or character constants are used to denote actual values in the source code, such as 1 (integer constant) or 2.3 (float constant) or “String constant” (a string: a piece of text).
     */
    sealed class Constant(val value: String) : Token

    data class Error(val reason: String, val line: Int, val column: Int) : Token


}
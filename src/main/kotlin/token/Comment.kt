package token

import token.Token.*

class SingleLine(commentedText: String) : Comment(commentedText) // //
class MultilineBrace(commentedText: String) : Comment(commentedText) // { }
class MultilineParentheses(commentedText: String) : Comment(commentedText) // (* *)
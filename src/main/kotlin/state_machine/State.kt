package state_machine

import token.Token

sealed interface State {
    object Initial : State

    data class Identifier(val token: Token.Identifier) : State
    data class Symbol(val token: Token.Symbol) : State
    data class Comment(val token: Token.Comment) : State
    data class Constant(val token: Token.Constant) : State

    data class Final(val token: Token) : State // terminator state: can be one of above or an ERROR state.
}

/*

Initial ---letter---> Identifier
Initial ---digit---> Constant
Initial ---symbol---> Symbol
Initial ---separator---> Initial

Identifier ---letter---> Identifier or ReservedWord
Identifier ---digit---> Identifier
Identifier ---symbol---> Final
Identifier ---separator---> Final

Symbol ---letter---> Constant (it can be a string, for example "hello") or Final
Symbol ---digit---> Constant (it can be a hex-digit, for example $A12B) or Final
Symbol ---symbol---> Symbol or Final
Symbol ---separator---> Final

Comment ---letter---> Comment
Comment ---digit---> Comment
Comment ---symbol---> Comment or Final
Comment ---separator---> Comment

Constant ---letter---> Constant or Final (Error)
Constant ---digit---> Constant
Constant ---symbol---> Constant or Final (Error)
Constant ---separator---> Final

 */
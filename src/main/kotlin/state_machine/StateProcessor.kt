package state_machine

interface StateProcessor<S> {
    fun process(state: S, character: Char?): State
}
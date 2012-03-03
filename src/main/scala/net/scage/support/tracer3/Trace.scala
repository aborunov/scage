package net.scage.support.tracer3

import net.scage.support.ScageId._
import net.scage.support.{State, Vec}

trait TraceTrait {
  type ChangerType <: TraceTrait // changer type must be the type of actual Trace's child in client code
  def changeState(changer:ChangerType, state:State) // maybe 'changeState' is not the right name..
  def state:State

  val id = nextId
  private[tracer3] var _location = Vec(0, 0)
  def location:Vec = _location
}

trait Trace extends TraceTrait {
  type ChangerType = Trace
}

object Trace {
  def apply(changeState:(Trace, State) => Unit = (changer, state) => {}, state: => State = State()) = {
    val (_changeState, _state) = (changeState, state)
    new Trace {
      def changeState(changer:Trace, state:State) {_changeState(changer, state)}
      def state:State = _state
    }
  }
}
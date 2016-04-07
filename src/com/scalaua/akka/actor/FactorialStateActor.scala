package com.scalaua.akka.actor

import akka.actor.{ActorRef, Props, Actor}
import com.scalaua.akka.actor.FactorialStateActor.Answer

object FactorialStateActor {
  case class Answer(i: Int)
}

class FactorialStateActor extends Actor {

  override def receive: Receive = {
    case i: Int if i == 0 || i == 1 =>
      sender() ! Answer(1)
    case i: Int =>
      val child = context.actorOf(Props(new FactorialStateActor()))
      child ! i - 1
      context.become(waitingForAnAnswer(sender(), i))
  }

  def waitingForAnAnswer(replyTo: ActorRef, top: Int): Receive = {
    case Answer(a) =>
      replyTo ! FactorialStateActor.Answer(a * top)
      context.unbecome()
  }

}

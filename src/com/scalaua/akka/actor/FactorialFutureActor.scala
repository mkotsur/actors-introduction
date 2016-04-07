package com.scalaua.akka.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

class FactorialFutureActor extends Actor {

  implicit val timeout = Timeout(1 second)
  implicit val globalExecutionContext = ExecutionContext.global

  override def receive: Receive = {
    case i: Int if i == 0 || i == 1 =>
      sender() ! 1
    case i: Int =>
      val replyTo = sender()
      (self ? (i - 1)).foreach {
        case answer: Int => replyTo ! (answer * i)
      }
  }
}

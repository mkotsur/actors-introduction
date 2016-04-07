package com.scalaua.akka

import akka.actor.{ActorRef, ActorSystem, Props}
import com.scalaua.akka.actor.FactorialFutureActor
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

object FactorialApp extends App {

  implicit val timeout = Timeout(10 seconds)

  val actorSystem = ActorSystem("MyApp")

  val factorialActor = actorSystem.actorOf(Props(new FactorialFutureActor()))

  print(s"Created ${factorialActor.path}")

  private val result: Future[Any] = factorialActor ? 15
  result foreach println

  actorSystem.terminate()
}

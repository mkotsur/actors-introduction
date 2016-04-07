package com.scalaua.akka.actor

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSuiteLike}


class FactorialActorTest extends TestKit(ActorSystem("MySpec"))
  with FunSuiteLike
  with ImplicitSender
  with BeforeAndAfterAll {

  private def normalFactorial(i: Int): Int = {
    if (i <= 1) 1 else i * normalFactorial(i - 1)
  }

  test("Future: factorial should work for 0") {
    val fAcotor = system.actorOf(Props(new FactorialFutureActor()))
    fAcotor ! 0
    expectMsg(1)
  }

  test("Future: factorial should work for 1") {
    val fAcotor = system.actorOf(Props(new FactorialFutureActor()))
    fAcotor ! 1
    expectMsg(1)
  }

  test("Future: factorial should work for 1+") {

    val fAcotor = system.actorOf(Props(new FactorialFutureActor()))

    Range(0, 10).foreach {
      case i =>
        fAcotor ! i
        expectMsg(normalFactorial(i))
    }
  }
  test("State: factorial should work for 0") {
    val fAcotor = system.actorOf(Props(new FactorialStateActor()))
    fAcotor ! 0
    expectMsg(FactorialStateActor.Answer(1))
  }

  test("State: factorial should work for 1") {
    val fAcotor = system.actorOf(Props(new FactorialStateActor()))
    fAcotor ! 1
    expectMsg(FactorialStateActor.Answer(1))
  }

  test("State: factorial should work for 1+") {

    val fAcotor = system.actorOf(Props(new FactorialStateActor()))

    Range(0, 10).foreach {
      case i =>
        fAcotor ! i
        expectMsg(FactorialStateActor.Answer(normalFactorial(i)))
    }
  }

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

}

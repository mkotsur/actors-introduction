package com.scalaua.akka

import java.time.LocalDateTime
import java.time.temporal.{ChronoUnit, TemporalUnit}

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.scalaua.akka.theatre.DelayedEventActor.StartSale
import com.scalaua.akka.theatre.{DelayedEventActor, EventActor, TicketDeskActor}
import com.scalaua.akka.theatre.TicketDeskActor.BookTicket

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

object TheatreApp extends App {
  implicit val timeout = Timeout(10 seconds)

  val mySystem = ActorSystem("TheatreApp")

//  val myProps: Props = Props(new TicketDeskActor())
//  val ticketDesk: ActorRef = mySystem.actorOf(myProps, "ticket-desk")
//
//  println(s"Created ${ticketDesk.path}")
//  // Created akka://TheatreApp/user/ticket-desk
//
//  ticketDesk ! BookTicket("JustinBieber", 1, 10)

  val delayedEvent = mySystem.actorOf(Props(new DelayedEventActor("AcDc", LocalDateTime.now().plus(1, ChronoUnit.MINUTES))), "delayed-event")
//  delayedEvent ! StartSale
  delayedEvent ! 123

  mySystem.terminate()
}

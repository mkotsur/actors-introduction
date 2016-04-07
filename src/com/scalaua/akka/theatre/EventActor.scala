package com.scalaua.akka.theatre

import akka.actor.{Actor, ActorLogging}
import akka.actor.Actor.Receive
import com.scalaua.akka.theatre.TicketDeskActor.BookTicket

class EventActor(eventId: String) extends Actor with ActorLogging {

  override def preStart(): Unit = {
    log.info(s"Starting an event actor for $eventId")
    // [akka://TheatreApp/user/ticket-desk/event-JustinBieber] Starting an event actor for JustinBieber
  }

  override def receive: Receive = {
    case BookTicket(_, seat, row) =>
      log.info(s"Booking a ticket")
  }

}

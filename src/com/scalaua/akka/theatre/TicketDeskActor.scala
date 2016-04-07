package com.scalaua.akka.theatre

import akka.actor.{Actor, ActorLogging, Props}
import com.scalaua.akka.theatre.TicketDeskActor.BookTicket

object TicketDeskActor {

  case class BookTicket(eventId: String, row: Int, seat: Int)

  case class TicketBooked(ticketId: Long)

}

class TicketDeskActor extends Actor {

  override def receive: Receive = {
    case msg @ BookTicket(eventId, _, _) =>
      val evtActorName = s"event-$eventId"
      val evtActorProps = Props(new EventActor(eventId))

      val eventActorRef = context.child(evtActorName).getOrElse {
        context.actorOf(evtActorProps, evtActorName)
      }

      eventActorRef forward msg
  }

}

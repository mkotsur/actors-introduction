package com.scalaua.akka.theatre

import java.time.{Duration, LocalDateTime}

import akka.actor.{ActorLogging, Cancellable}
import com.scalaua.akka.theatre.DelayedEventActor.{SaleIsNotStartedYet, StartSale}
import com.scalaua.akka.theatre.TicketDeskActor.BookTicket

import scala.concurrent.duration._
import scala.language.postfixOps

object DelayedEventActor {
  case object SaleIsNotStartedYet
  case object StartSale
}

class DelayedEventActor(eventId: String, saleStarts: LocalDateTime)
  extends EventActor(eventId) with ActorLogging {

  var cancellable: Option[Cancellable] = None

  override def preStart(): Unit = {
    super.preStart()
    val delay = Duration.between(saleStarts, LocalDateTime.now())
    import context.dispatcher
    cancellable = Some(
      context.system.scheduler.scheduleOnce(delay.toNanos nanos, self, StartSale)
    )
  }

  override def postStop(): Unit = {
    super.postStop()
    cancellable.foreach(_.cancel())
  }

  override def receive: Receive = {
    case StartSale =>
      context.become(super.receive)
    case msg: BookTicket =>
      log.warning(s"Trying to book a ticket too early")
      sender() ! SaleIsNotStartedYet
  }

}

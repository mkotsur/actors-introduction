package com.scalaua.akka

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object MyApp extends App {

  val price: Future[Int] = Future.successful(10)
  val price2: Future[String] = Future.successful("Hello")

  implicit val c = scala.concurrent.ExecutionContext.global

  val all = Future.sequence(Seq(price, price2))

  all.onSuccess { case Seq(price1, _) =>
      println(price1)
  }

  Await.ready(all, 10 seconds)
}

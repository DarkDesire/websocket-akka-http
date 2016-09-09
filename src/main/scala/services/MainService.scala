package services

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

object MainService {
  def route: Route = pathEndOrSingleSlash {
    complete("Welcome to websocket server")
  }
}
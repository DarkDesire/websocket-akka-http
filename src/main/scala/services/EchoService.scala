package services

import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow

object EchoService{
  def route: Route = path("ws-echo") {
    get {
      handleWebSocketMessages(echoService)
    }
  }

  val echoService: Flow[Message, Message, _] = Flow[Message].map {
    case TextMessage.Strict(txt) => TextMessage("ECHO: " + txt)
    case BinaryMessage.Strict(byteString) => TextMessage(s"ECHO: ${byteString.utf8String}")
    case _ => TextMessage("Message type unsupported")
  }
}
package services

import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws.Message
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import chat.ChatRooms

object ChatService {
  val echo = Flow[Message]

  def route(implicit actorSystem: ActorSystem, materializer: Materializer): Route =
    pathPrefix("ws-chat" / IntNumber) { chatId =>
      parameter("name") { userName =>
        handleWebSocketMessages(ChatRooms.findOrCreate(chatId).websocketFlow(userName))
      }
    }
}

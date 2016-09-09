package chat

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.stream.{FlowShape, KillSwitches, OverflowStrategy, ThrottleMode}
import akka.stream.scaladsl._

import scala.concurrent.duration._

class ChatRoom(roomId: Int, actorSystem: ActorSystem) {

  private[this] val chatRoomActor = actorSystem.actorOf(Props(classOf[ChatRoomActor], roomId))

  def websocketFlow(user_id: String): Flow[Message, Message, _] =
    Flow.fromGraph(
      GraphDSL.create(Source.actorRef[ChatMessage](bufferSize = 1000, OverflowStrategy.fail)) {
        implicit builder =>
          chatSource =>

            import GraphDSL.Implicits._
            implicit val executionContext = actorSystem.dispatcher

            var stopper = builder.add(KillSwitches.single[ChatEvent]) // ****1

            val fromWebsocket = builder.add(
              Flow[Message]
                .watchTermination()(
                  (_, te) => te.onSuccess {
                    case cause => // ****2
                  })
                .throttle(1, 15 millis, 1, ThrottleMode.shaping) // ****3
                //.buffer(1, OverflowStrategy.backpressure)
                //.delay(1 seconds, DelayOverflowStrategy.backpressure)
                .collect {
                case TextMessage.Strict(txt) => IncomingMessage(user_id, txt)
                case BinaryMessage.Strict(byteString) => IncomingMessage(user_id, byteString.utf8String)
              }
            )

            val actorAsSource = builder.materializedValue.map(actor => UserJoined(user_id, actor)) // ****4
            val chatActorSink = Sink.actorRef[ChatEvent](chatRoomActor, UserLeft(user_id)) // ****5
            val mergeToChat = builder.add(Merge[ChatEvent](2))

            actorAsSource ~> mergeToChat.in(0)
            fromWebsocket ~> mergeToChat.in(1)

            mergeToChat ~> stopper ~> chatActorSink //****6

            val backToWebsocket = builder.add(
              Flow[ChatMessage].groupedWithin(10, 300 millis).mapConcat {
                msgs => msgs map {
                  case ChatMessage(author, text) => TextMessage(s"[$author]: $text")
                }
              }
            )

            chatSource ~> backToWebsocket

            FlowShape(fromWebsocket.in, backToWebsocket.out)
      }
    )

  def sendMessage(message: ChatMessage): Unit = chatRoomActor ! message

}

object ChatRoom {
  def apply(roomId: Int)(implicit actorSystem: ActorSystem) = new ChatRoom(roomId, actorSystem)
}
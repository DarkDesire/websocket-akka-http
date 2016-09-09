import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import services.{ChatService, EchoService, MainService}
import scala.io.StdIn

object HttpServer extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val config = system.settings.config
  val address = config.getString("app.address")
  val port = config.getInt("app.port")

  val route = MainService.route ~
    EchoService.route ~
    ChatService.route

  val bindingFuture = Http().bindAndHandle(route, address, port)

  println(s"Server online at http://${address}:${port}/\nAddress for WS: ws://${address}:${port}/ws-chat/XXX?name=YYYY\nWhere XXX channel name and YYY your nickname\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}

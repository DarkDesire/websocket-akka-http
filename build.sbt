name := "WebSocketServer"

version := "1.0"

scalaVersion := "2.11.8"

// disable using the Scala version in output paths and artifacts
crossPaths := false

libraryDependencies ++= Seq (
  "com.typesafe.akka" %% "akka-actor" % "2.4.10",
  "com.typesafe.akka" %% "akka-stream" % "2.4.10",
  "com.typesafe.akka" %% "akka-http-core" % "2.4.10",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.10"
)
# websocket-akka-http
[Ru]

Предыстория. Необходимо было посмотреть на рабочую демку websocket для последней версии Akka 2.4.10. Гугл выдал http://blog.scalac.io/2015/07/30/websockets-server-with-akka-http.html. Делал по туторилу, но в итоге оказалась не рабочей для последней версии. В гугле начал искать решение и наткнулся на http://stackoverflow.com/questions/37018992/how-to-stop-a-flow-by-killswitch-in-a-graphdsl-and-how-to-find-the-inner-actor . В итоге сборки получился нормальный билд под последнюю версию с реализацией функционала по туториалу, а именно создание комнат для пользователей по запросу и broadcast в комнате.

+ Написал клиент на Unity3D для тестирования сервера(исходники: [github.com/DarkDesire/Unity3D-WebSocket-Client](https://github.com/DarkDesire/Unity3D-WebSocket-Client)), хотя подходит любой другой способ с поддержкой Web Socket. Например, Google расширение **Dark WebSocket Terminal** [Скачать можно в здесь](https://chrome.google.com/webstore/detail/dark-websocket-terminal/dmogdjmcpfaibncngoolgljgocdabhke?hl=ru)

[Eng]

Background. I wanted to look at the working demo for websocket server for the latest version Akka 2.4.10. Google issued http://blog.scalac.io/2015/07/30/websockets-server-with-akka-http.html. I did everything by tutorial, but at the end found that it's not working for the latest version. In google started to look for a solution and came across http://stackoverflow.com/questions/37018992/how-to-stop-a-flow-by-killswitch-in-a-graphdsl-and-how-to-find-the-inner-actor . As a result, made the assembly that builds under the latest version and functionality the same that was in tutorial, namely the creation of rooms for users of on-demand and broadcast messages in the room.

+ Wrote Unity3D client for testing server(source: [github.com/DarkDesire/Unity3D-WebSocket-Client](https://github.com/DarkDesire/Unity3D-WebSocket-Client)), although you can use any other client that supports Web Socket. For example, Google extension **Dark WebSocket Terminal**. [Download it from here ](https://chrome.google.com/webstore/detail/dark-websocket-terminal/dmogdjmcpfaibncngoolgljgocdabhke?hl=ru)

- Akka version 2.4.10 [actor, streams, http-core, http-experimental]
- Scala version: 2.11.8
- SBT version: 0.13.12
- Maintained: yes , use **sbt assembly** 


## Гифка наглядности / Gif for clarity
![1](http://storage5.static.itmages.ru/i/16/0910/h_1473466589_1238271_3494c8a828.gif)

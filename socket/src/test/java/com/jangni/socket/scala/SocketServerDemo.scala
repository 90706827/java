package com.jangni.socket.scala

object SocketServerDemo extends App {

    new SocketServer(new DateListener).start()
}

package com.jangni.socket.scala

object SocketClientDemo extends App with IContext {

 val client = new SocketClient("127.0.0.1",8989,60000)

  client.connect()


  val jobContext = new JobContext()
  jobContext.thridLsId = "1234567890"
  client.post(jobContext)
  println(s"${jobContext.respCode}-${jobContext.respDesc}")



}

package com.jangni.socket.scala

import scala.xml.XML

object test extends App with IContext {
  val req = "<body></body>"
  val jobContext = new JobContext()
  val xml = XML.loadString(req)
  xml.child.foreach {
    node =>
      jobContext.toValues(node.label, node.text)
  }

  jobContext.respCode = "00"
  jobContext.respDesc = "交易成功"

  val respMsg = jobContext.contextValues
    .filter {
      case (_, value) => value != null && value.nonEmpty
    }
    .map {
      case (key, value) => s"<$key>$value</$key>"
    }
    .mkString("<body>", "", "</body>")
    .replaceAll(">[\\s]+<", "><")
  println(respMsg)

}

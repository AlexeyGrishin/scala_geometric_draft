package ru.graale.gamr.fw.input

abstract case class InputObject();

case class MouseClicked(x: Double, y: Double) extends InputObject;
case class MouseMovedTo(x: Double, y: Double) extends InputObject;
case class KeyPressed(code: scala.swing.event.Key.Value) extends InputObject;
case class ConnectionClosed() extends InputObject;
abstract case class Other() extends InputObject;

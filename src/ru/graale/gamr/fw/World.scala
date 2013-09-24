package ru.graale.gamr.fw

trait World {

  type E;

  def += (o: Any) {
    o match {
      case e: E => add(e)
      case _ => //ignore
    }
  }

  def add (o: E);

  def -= (o: Any) {
    o match {
      case e: E => remove(e)
      case _ => //ignore
    }
  }

  def remove (o: E);
}

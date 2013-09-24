package ru.graale.gamr.fw

import collection.mutable.{HashSet, LinkedList}

trait Scene extends Updateable {
  type O;
  private var _world: Option[World] = None

  private val objects = new HashSet[O]
  private val updateableObjects = new HashSet[Updateable]

  def world_=(w: World) { _world = Option(w)}
  def world = _world.get

  def += (ob: O) {
    objects += ob
    if (ob.isInstanceOf[Updateable]) updateableObjects += ob.asInstanceOf[Updateable]
    _world.foreach(_ += ob)
  }

  def -= (ob: O) {
    objects -= ob
    if (ob.isInstanceOf[Updateable]) updateableObjects -= ob.asInstanceOf[Updateable]
    _world.foreach(_ -= ob)
  }

  def list = objects.toList

  def update(ticks: Int) {
    updateableObjects.foreach(_.update(ticks))
  }

}

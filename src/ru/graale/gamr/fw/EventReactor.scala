package ru.graale.gamr.fw

import collection.mutable.{HashSet, LinkedList, Queue}


trait EventReactor[E] {

  private val events: Queue[E] = new Queue[E];
  type Reactor = (E) => Unit
  private val reactors: HashSet[Reactor] = new HashSet[Reactor]();

  def add(in: E) {
    events.synchronized {
      events.enqueue(in)
    }
  }

  def += (in: E) = add(in)

  def react(reactor: Reactor) {
    reactors +=(reactor)
  }

  def update() {
    val allEvents = events.synchronized {events.dequeueAll(x => true)}
    allEvents.foreach(e => reactors.foreach(_(e)))
  }
}

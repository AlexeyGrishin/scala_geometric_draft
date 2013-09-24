package ru.graale.gamr.fw

import collection.mutable.{HashSet, LinkedList}
import input.InputObject


class Game(scene: Scene, logic: GameLogick) extends Updateable {

  logic.game = this;

  val players = new HashSet[Player]();

  var _currentScene: Scene = scene
  
  def currentScene = _currentScene
  
  def currentScene_=(scene: Scene) {
    _currentScene = scene
    //call listeners
  }

  def update(ticks: Int) {
    logic.beforeUpdate(ticks);
    currentScene.update(ticks);
    players.foreach(_.update(ticks))
    logic.afterUpdate(ticks);
  }

  def render() {
    players.foreach(_.render())
  }

  def += (player: Player) {
    players += player
  }

  def -= (player: Player) {
    players -= player
  }

}


object Game {
  case class SceneChanged(newScene: Scene) extends InputObject;
}
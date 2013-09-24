package ru.graale.gamr.fw

class GameLogick {

  var game: Game = null;

  def beforeUpdate(ticks: Int) {
    // do nothing by default
  }

  def afterUpdate(ticks: Int) {
    // do nothing by default
  }

  def switchScene(scene: Scene) {
    game.currentScene = scene
  }

}

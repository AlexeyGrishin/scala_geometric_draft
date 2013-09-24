package ru.graale.gamr.fw

import input.InputObject
import ru.graale.gamr.fw.Player.SimpleView

class PlayerLogick(game: Game, val name: String) extends Updateable with EventReactor[InputObject] {

  var _view: SimpleView = null;

  def update(ticks: Int) {
    update();
    prepareView(ticks);
  }

  def input(in: InputObject) {this += in}

  protected def prepareView(ticks: Int) {
    _view = new SimpleView(
      ticks,
      name,
      game.currentScene.list
    )
  }
  
  def view: Player.View = _view
}

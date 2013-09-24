package ru.graale.gamr.fw

import input.InputObject
import Player._;

class Player(val logic: PlayerLogick) extends EventReactor[InputObject] with Updateable {

  private var _renderer: ViewRenderer = NullRenderer
  private var _view: View = null;

  def renderer = _renderer
  def renderer_=(renderer: ViewRenderer) {_renderer = if (renderer != null) renderer; else NullRenderer}

  def view: View = _view;
  def view_=(view: View) {_view = view}

  react {logic.input(_)}

  def update(ticks: Int) {
    update()
    logic.update(ticks)
    view = logic.view
  }

  def render() {
    val v = view;
    if (v != null) {
      _renderer.render(v)
    }
  }  
  
}


object Player {

  trait View {
    def gameState: Any
    def playerState: Any
    def viewObjects: List[Any]
  }

  type ViewRenderer = {
    def render(view: View): Unit;
  }

  object NullRenderer {
    def render(view: View): Unit = {};
  }
  
  class SimpleView(val gameState: Any, val playerState: Any, val viewObjects: List[Any]) extends View;
  
}

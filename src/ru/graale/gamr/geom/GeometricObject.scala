package ru.graale.gamr.geom

import figures.Figure

import GeometricObject._
import collection.mutable.HashSet
;
trait GeometricObject {

  private var _figure: Figure = produceFigure;
  //private val listeners = new HashSet[Listener];

  def figure = _figure;

  protected def figure_=(fig: Figure) {
    val oldFigure = _figure
    //listeners.map(_.)
    _figure = fig
  }

  protected def produceFigure: Figure;

  //def += (listener: Listener) = listeners += listener
  //def -= (listener: Listener) = listeners -= listener
  
  
  
}

object Type extends Enumeration {
  type Type = Value
  val Moved, Resized, Reshaped, Other = Value
}

object GeometricObject {
  import Type._;
  
  type Listener = {
    def onFigureChange(o: GeometricObject, f: Figure, typ: Type): Boolean;
  }

  type Callback = {
    def onCollision(o: GeometricObject)
  }
  

  
}
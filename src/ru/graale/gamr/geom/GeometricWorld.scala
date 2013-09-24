package ru.graale.gamr.geom

import collection.mutable.{HashSet, MutableList}
import figures.Figure
import ru.graale.gamr.fw.World

class GeometricWorld extends World {
  type E = GeometricObject

  private val allObjects: HashSet[GeometricObject] = new HashSet[GeometricObject]
  private var allFigures: List[Figure] = null;

  def add (o: GeometricObject) {
    allObjects += o
    allFigures = allObjects.map(_.figure).toList
  }

  def remove (o: GeometricObject) {
    allObjects -= o;
    allFigures = allObjects.map(_.figure).toList
  }

  def figures = allFigures

  def inFigure(point: Point) = allFigures.count(_.contains(point)) > 0


}
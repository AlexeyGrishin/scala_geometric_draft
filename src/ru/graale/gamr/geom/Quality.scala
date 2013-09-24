package ru.graale.gamr.geom

sealed case class Quality(quality: Double);
case object Best extends Quality(Double.PositiveInfinity);
case object Normal extends Quality(100.0);
case object Worst extends Quality(0.0);


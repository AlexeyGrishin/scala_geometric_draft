package ru.graale.gamr.fw

import java.util.concurrent.{TimeUnit, Executors}
import java.util.logging.{Level, Logger}


class GameCycle(game: Game) extends Runnable {

  val scheduler = Executors.newSingleThreadScheduledExecutor();
  val FPS = 25;
  val MSPF = 1000 / 25;

  lazy val startTicks = System.currentTimeMillis();
  val logger = Logger.getLogger("Game")

  def run() {
    try {
      val sTicks = startTicks;
      val ticks = (System.currentTimeMillis() - sTicks).toInt;
      game.update(ticks);
      game.render();
    }
    catch {
      case e: Exception => logger.log(Level.SEVERE, "Unexpected error", e)
    }
  }

  def start() {
    scheduler.scheduleAtFixedRate(this, 0, MSPF, TimeUnit.MILLISECONDS);
  }

}

package ru.graale.gamr.geom

case class ExDouble(value: Double) {

  def ~(v: Double): Boolean = WithEpsilon.compare(value, v) == 0
  def !~(v: Double): Boolean = WithEpsilon.compare(value, v) != 0
  def <~ (v: Double): Boolean = WithEpsilon.compare(value, v) <= 0
  def >~ (v: Double): Boolean = WithEpsilon.compare(value, v) >= 0

  def !~(v: ExDouble): Boolean = this !~ v.value
  def ~(v: ExDouble): Boolean = this ~ v.value
  def <~(v: ExDouble): Boolean = this <~ v.value
  def >~(v: ExDouble): Boolean = this >~ v.value

  def ==(v: Double) = value == v
  def <=(v: Double) = value <= v
  def >=(v: Double) = value >= v
  def <(v: Double) = value < v
  def >(v: Double) = value > v

  def ==(v: ExDouble) = value == v.value
  def <=(v: ExDouble) = value <= v.value
  def >=(v: ExDouble) = value >= v.value
  def <(v: ExDouble) = value < v.value
  def >(v: ExDouble) = value > v.value

  def ^(pow: Int): Double = (1 to pow).map(_.toDouble).reduce((acc, v) => acc * value) * value

  def toInt = scala.math.round(value)
  def toDouble = value
  def toLong = scala.math.round(value)
  def roundInt = scala.math.round(value).toInt
  def zerify: ExDouble = if (scala.math.abs(value) < WithEpsilon.epsilon) WithEpsilon.ZERO; else this
  override def toString = value.toString
}

object WithEpsilon {

  val EPSILON = 0.0001;
  val ZERO = ExDouble(0.0)

  private val _epsilon: ThreadLocal[Double] = new ThreadLocal[Double]() {
    override def initialValue: Double = EPSILON;
  }

  def epsilon = _epsilon.get()

  def compare(value1: Double, value2: Double) = {
    val diff = value1 - value2
    if (scala.math.abs(diff) < epsilon) 0; else diff
  }

  def withEplison(e: Double)(f: => scala.Unit) {
    val oldE = _epsilon.get()
    _epsilon.set(e)
    try {
      f;
    }
    finally {
      _epsilon.set(oldE);
    }
  }

  def apply(e: Double) (f: => scala.Unit) = withEplison(e) {f}

  implicit def double2ex(v: Double) = ExDouble(v);
  implicit def ex2double(v: ExDouble) = v.value;
}
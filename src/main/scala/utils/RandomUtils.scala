package utils

import scala.util.Random

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */
object RandomUtils {
  def choice[A](choices: A*): A = Random.shuffle(choices.toList).head

  def chooseWithIndex(choices: Any*) = {
    val rnd = Random.nextInt(choices.size)

    (choices(rnd), rnd)
  }
}

package ga

import nn.NeuralNetworkModel

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */
object BreedingTests extends App {
  val s1 = Specimen(NeuralNetworkModel(List(64, 64), 2, "relu", "rmsprop", 0.0))
  val s2 = Specimen(NeuralNetworkModel(List(64, 64), 2, "elu", "adam", 0.0))
  val s3 = s1 breed s2

  println(s3)
}

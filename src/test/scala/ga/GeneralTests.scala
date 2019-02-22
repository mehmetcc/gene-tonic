package ga

import nn.NeuralNetworkModel
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.learning.config.RmsProp

import scala.util.Random

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */
object GeneralTests {
  def main(args: Array[String]): Unit = {
    val pop: Population = Population.createRandomPopulation(10)
    val petri : Population = pop.evolve(2, 0.5, 0.7)
  }

}

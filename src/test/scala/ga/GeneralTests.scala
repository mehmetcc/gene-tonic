package ga

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */
object GeneralTests {
  def main(args: Array[String]): Unit = {
    val pop: Population = Population.createRandomPopulation(10)
    val petri: Population = pop.evolve(2, 0.5, 0.7)
  }

}

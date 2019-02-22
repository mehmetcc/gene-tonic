package ga

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */
object BreedingTests {
  def main(args: Array[String]): Unit = {
    val pop = Population.createRandomPopulation(10)
    val newPop = pop.evolve(GAConfigurations.retain, GAConfigurations.selectionProbability, GAConfigurations.mutationRate)
    newPop.specimens.foreach(println)
  }
}

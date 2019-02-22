package ga

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */
object GASolver {

  /**
    * Create randomly generated population and solve according to the Genetic algorithm
    *
    * @return the optimized population
    */
  def solve(): Population = {
    val initialPopulation = Population.createRandomPopulation(GAConfigurations.populationSize)
    iterate(GAConfigurations.iteration, initialPopulation)
  }

  def iterate(iteration: Int, population: Population): Population = {
    def go(currIter: Int, currPop: Population): Population = {
      if (currIter == 0)
        currPop
      else go(currIter - 1, currPop.evolve(GAConfigurations.retain,
        GAConfigurations.selectionProbability,
        GAConfigurations.mutationRate))
    }
    go(iteration, population)
  }

  /**
    * Give a custom population and solve according to the Genetic algorithm
    *
    * @param initialPopulation the initial population for the GA
    * @return the optimized population
    */
  def solve(initialPopulation: Population): Population = {
    iterate(GAConfigurations.iteration, initialPopulation)
  }

}

package ga

import nn._

import scala.util.Random

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */

/**
  * Population class
  * A generic class for all population types possible
  * When mutated or cross-bred, it would return another population class with same parameters
  *
  * @param populationSize: size of the population
  */
case class Population(populationSize: Int, specimens: List[Specimen]) {
  /**
    * Sort by fitness in decreasing order
    *
    * @return Sorted Population in decreasing order
    */
  def sortByFitness(): Population = Population(populationSize, specimens.sortBy(_.fitness).reverse)

  /**
    * Evolves the population by selecting the best, then by randomly selecting, mutating and cross breeding some of the
    * remaining specimens
    * @param retain retain size of the best specimens
    * @param selectionProbability probability of selecting randomly
    * @param mutationRate the mutation probability
    * @return evolved specimen
    */
  def evolve(retain: Int, selectionProbability: Double, mutationRate: Double): Population = {
    // selecting the best by the fitness
    val retainedSpeciesAfterFitnessSelection = this.sortByFitness().specimens.take(retain)

    val remainingAfterFirstSelection = this.specimens.sortBy(_.fitness).take(this.specimens.size - retain)

    // selecting random elements by the selection probability
    val retainedSpeciesAfterRandomSelection = remainingAfterFirstSelection.filter {
      _ => selectionProbability >= Random.nextDouble()
    }

    val remainingAfterSecondSelection = specimens.filterNot{
      retainedSpeciesAfterFitnessSelection ++ retainedSpeciesAfterRandomSelection contains(_)
    }

    // selecting randomly by mutating them
    val retainedSpeciesAfterMutation = for (e <- remainingAfterSecondSelection if mutationRate >= Random.nextDouble()) yield e.mutate()

    // last but not least, let's make cross breeding and end the evolution
    val remainingAfterThirdSelection = specimens.filterNot{
      retainedSpeciesAfterFitnessSelection ++ retainedSpeciesAfterRandomSelection ++ retainedSpeciesAfterMutation contains(_)
    }

    val finalSpeciesList: List[Specimen] = retainedSpeciesAfterFitnessSelection ++
      retainedSpeciesAfterRandomSelection ++
      retainedSpeciesAfterMutation ++
      Population.breedInBatches(remainingAfterThirdSelection.size, remainingAfterThirdSelection)


    Population(populationSize, finalSpeciesList)
  }

}

object Population {

  /**
    * Creates a random population of specimens
    * @param populationSize the size of the total population
    * @return the newly created population
    */
  def createRandomPopulation(populationSize: Int): Population = {
    val populationList = List.fill(populationSize)(Specimen(NeuralNetworkModel.createRandomNeuralNetworkModel()))
    Population(populationSize, populationList)
  }

  /**
    * A helper method for cross-breeding
    * @param size of the current population
    * @param toBreed the list of specimens to be bred
    * @return a list of new specimens
    */
  def breedInBatches(size: Int, toBreed: List[Specimen]): List[Specimen] = {

    def go(iterSize: Int, currList: List[Specimen]): List[Specimen] = {

      val firstIndex = Random.nextInt(toBreed.size)
      val secondIndex = Random.nextInt(toBreed.size)

      if (iterSize == 0) {
        (toBreed(0) breed toBreed(1)) :: currList
      } else {

        if (toBreed(firstIndex) == toBreed(secondIndex)) {
          go(size - 1, (toBreed(Random.nextInt(toBreed.size)) breed toBreed(secondIndex)) :: currList)
        } else {
          go(size - 1, (toBreed(firstIndex) breed toBreed(secondIndex)) :: currList)
        }
      }


      go(toBreed.size, toBreed)
    }

    go(size, toBreed)
  }
}



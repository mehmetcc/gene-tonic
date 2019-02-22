import ga.GASolver

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */

class Application extends App {
  val population = GASolver.solve().sortByFitness()
  population.specimens.foreach(println)
}
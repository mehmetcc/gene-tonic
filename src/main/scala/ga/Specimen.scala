package ga

import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.learning.config.{Adam, IUpdater, RmsProp}
import utils.RandomUtils.{choice, chooseWithIndex}

import nn._

import scala.util.Random

/**
  * Specimen class
  * A repr. class for a single specimen,
  * that many of them would consist a population
  *
  * @param model: A Neural Network model, might be created randomly
  */
case class Specimen(val model: NeuralNetworkModel) {


  /**
    * Cross breeding two neural networks
    *
    * Notice that parameters are gender-neutral, as it should be ^^
    *
    * @param other: The network to cross-breed with
    */
  def breed(other: Specimen): Specimen = {
    // ToDo
    // naming conventions for this function is actually legacy, might have to change it
    /**
      * I was actually going to go with "operator overloading" in a sense that A~B does cross-breeding
      * but I couldn't come up with an elegant operator for mutation, which is a single parameter method
      * so I guess this is a ToDo as well, finding a bearable solution for method naming problem
      */
    val that = other.model
    val layerSize = choice[Int](model.layerSize, that.layerSize)
    val nodes= choice[List[Int]](model.nodes, that.nodes).take(layerSize)
    val activation = choice[String](model.activation, that.activation)
    val optimizer = choice[String](model.optimizer, that.optimizer)

    // return
    Specimen(NeuralNetworkBuilder.build(NeuralNetworkModel(nodes, layerSize, activation, optimizer, 0.0)))
  }

  /**
    * Mutating a single neural network
    */
  def mutate(): Specimen = {
    val choosenAttributeWithIndex = chooseWithIndex(List(model.nodes,
      model.layerSize,
      model.activation,
      model.optimizer))

    choosenAttributeWithIndex._2 match {
      case 0 => {
        val rndIndex = Random.nextInt(model.layerSize)
        val nn = NeuralNetworkModel(model.nodes.updated(rndIndex, choice[Int](64, 128, 256)),
          model.layerSize,
          model.activation,
          model.optimizer,
          model.accuracy)
        Specimen(NeuralNetworkBuilder.build(nn))
      }
      case 1 => Specimen(model)
      case 2 => {
        val nn = NeuralNetworkModel(model.nodes,
          model.layerSize,
          choice[String]("relu", "elu"),
          model.optimizer,
          model.accuracy)
        Specimen(NeuralNetworkBuilder.build(nn))
      }
      case 3 => {
        val nn = NeuralNetworkModel(model.nodes,
          model.layerSize,
          model.activation,
          choice[String]("adam", "rmsprop"),
          model.accuracy)
        Specimen(NeuralNetworkBuilder.build(nn))
      }
      case _ => Specimen(model)
    }
  }

  override def toString(): String = "Nodes: " + model.nodes + "\n" +
    "Layer Size: " + model.layerSize + "\n" +
    "Activation function: " + model.activation + "\n" +
    "Optimizer: " + model.optimizer + "\n" +
    "Accuracy: " + model.accuracy

  /**
    * Pretty straightforward fitness function
    *
    * @return The accuracy
    */
  def fitness = model.accuracy
}

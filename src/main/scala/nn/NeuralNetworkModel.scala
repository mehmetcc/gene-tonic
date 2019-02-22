package nn

import com.typesafe.scalalogging.LazyLogging
import org.deeplearning4j.datasets.iterator.impl.{ListDataSetIterator, MnistDataSetIterator}
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.Updater
import org.deeplearning4j.optimize.listeners.ScoreIterationListener

import scala.util.Random
import org.deeplearning4j.nn.conf.Updater.{ADAM, RMSPROP}
import org.deeplearning4j.scalnet.layers.core.Dense
import org.deeplearning4j.scalnet.models.Sequential
import org.deeplearning4j.scalnet.regularizers.L2
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction

/**
  * DAVID DAVENPORT'UN ASKERLERİYİZ
  *
  * @author Mehmet Can Altuntaş
  *         github.com/mehmetcc
  */



case class NeuralNetworkModel(nodes: List[Int],
                              layerSize: Int,
                              activation: String,
                              optimizer: String,
                              accuracy: Double)

object NeuralNetworkModel {

  /**
    * Creates a random neural network model
    * @return randomly created neural network model
    */
  def createRandomNeuralNetworkModel(): NeuralNetworkModel = {
    val layerSize = Random.shuffle(Array(1, 2, 3, 4).toList).head
    val nodes = List.fill(layerSize)(Random.shuffle(Array(64, 128, 256).toList).head)
    val activation = Random.shuffle(Array("relu", "elu").toList).head
    val optimizer = Random.shuffle(Array("adam", "rmsprop").toList).head
    val accuracy: Double = 0

    return NeuralNetworkBuilder.build(NeuralNetworkModel(nodes, layerSize, activation, optimizer, accuracy))
  }

}

object NeuralNetworkBuilder extends LazyLogging {

  /**
    * Build a dl4j neural network from the given specifications
    * @param nnModel contains the necessary information for creating and training the neural network
    * @return the neural network model, this time with accuracy calculated
    */
  def build(nnModel: NeuralNetworkModel): NeuralNetworkModel = {

    val nodes = nnModel.nodes
    val layerSize = nnModel.layerSize
    val activationF = nnModel.activation
    val optimizer = nnModel.activation

    logger.info("Reading data set...")
    // download and load the MNIST images as tensors
    val mnistTrain = new MnistDataSetIterator(NeuralNetworkConfigurations.batchSize, true, NeuralNetworkConfigurations.seed)
    val mnistTest = new MnistDataSetIterator(NeuralNetworkConfigurations.batchSize, false, NeuralNetworkConfigurations.seed)

    logger.info("Build model...")
    val model: Sequential = Sequential(rngSeed = NeuralNetworkConfigurations.seed)

    if (activationF == "relu") {
      // input layer
      model.add(Dense(nodes(0),
        NeuralNetworkConfigurations.height * NeuralNetworkConfigurations.width,
        activation = Activation.RELU,
        regularizer = L2(NeuralNetworkConfigurations.learningRate * NeuralNetworkConfigurations.decay)))
      // hidden layers
      for (i <- 1 to layerSize-2) {
        model.add(Dense(nodes(i),
                  activation = Activation.RELU,
                  regularizer = L2(NeuralNetworkConfigurations.learningRate * NeuralNetworkConfigurations.decay)))
      }
      // output layer
      model.add(Dense(NeuralNetworkConfigurations.outputSize, activation = Activation.SOFTMAX))
    }
    else {
      // input layer
      model.add(Dense(nodes(0),
        NeuralNetworkConfigurations.height * NeuralNetworkConfigurations.width,
        activation = Activation.ELU,
        regularizer = L2(NeuralNetworkConfigurations.learningRate * NeuralNetworkConfigurations.decay)))
      // hidden layers

      for (i <- 1 to layerSize - 1) {
        model.add(Dense(nodes(i),
          activation = Activation.ELU,
          regularizer = L2(NeuralNetworkConfigurations.learningRate * NeuralNetworkConfigurations.decay)))
      }
      // output layer
      model.add(Dense(NeuralNetworkConfigurations.outputSize, activation = Activation.SOFTMAX))
    }

    if (optimizer == "adam")
      model.compile(LossFunction.MCXENT, OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT, Updater.ADAM)
    else
      model.compile(LossFunction.MCXENT, OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT, Updater.RMSPROP)

    logger.info("Train model...")
    model.fit(mnistTrain, NeuralNetworkConfigurations.epochs, List(new ScoreIterationListener(NeuralNetworkConfigurations.scoreFrequency)))

    logger.info("Evaluate model...")
    logger.info(s"Train accuracy = ${model.evaluate(mnistTrain).accuracy}")
    logger.info(s"Test accuracy = ${model.evaluate(mnistTest).accuracy}")

    NeuralNetworkModel(nodes, layerSize, activationF, optimizer, model.evaluate(mnistTrain).accuracy())
  }
}
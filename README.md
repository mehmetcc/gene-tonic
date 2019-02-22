# gene-tonic

gene-tonic is a genetic algorithm implementation for optimizing neural networks.It was inspired by this wonderful article: https://blog.coast.ai/lets-evolve-a-neural-network-with-a-genetic-algorithm-code-included-8809bece164

# How to Run

Running is pretty straightforward. You just have to import GASolver then run 
```scala
GASolver.solve()
```
for creating a random population and solving according to that random population. If you want to generate your own population, you might want to model your neural network first: 
```scala
...
// somewhere in your main
val nn1 = NeuralNetworkModel(List[64, 128, 256], // nodes based on their positions on the respective layer size
                             3, // layer size
                             "relu", // activation function. Only relu and elu is supported as of now
                             "adam", // updater (or optimizer, in Keras terminology) Only ADAM and RMSPROP is supported
                             0.0) // accuracy, initially set to 0
...
```

After that, we have to compile the model and generate a single Specimen.
```scala
Specimen(NeuralNetworkBuilder.build(NeuralNetworkModel(nodes, layerSize, activation, optimizer, 0.0)))
```
After having as many specimens as we want, we have to construct a specimen list and pass it into a Population object:
```scala
...
// somewhere in your main
val specimens: List[Specimen] = S1 :: S2 :: S3 ..... 
val population: Population = Population(specimens.size, specimens)
// run the GASolver
GASolver.solve(population)
```

# Configurations
There are two configurations in  nn and ga subdirectories, which are essentially Objects that contain several fields. In the future I would like to implement a JSON based approach.

# Running different datasets
Currently gene-tonic is only able to solve MNIST dataset. You can change the code so that it would allow different datasets other than MNIST.

# Benchmarks
```scala
// ToDo
```

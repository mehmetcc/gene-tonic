name := "gene-tonic"

version := "1.0"

scalaVersion := "2.11.12"

// https://mvnrepository.com/artifact/org.deeplearning4j/scalnet
libraryDependencies += "org.deeplearning4j" %% "scalnet" % "1.0.0-alpha"

// https://mvnrepository.com/artifact/org.nd4j/nd4j-native-platform
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "1.0.0-alpha"

// logging
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"

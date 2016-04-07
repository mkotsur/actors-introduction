name := "My Project"

version := "1.0"

scalaVersion := "2.11.7"

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.2"

libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.2"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"


resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

scalaVersion := "2.10.4"

libraryDependencies += "org.scala-sbt" % "scripted-plugin" % sbtVersion.value

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")
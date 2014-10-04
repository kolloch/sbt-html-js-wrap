sbtPlugin := true

organization := "net.eigenvalue"

name := "sbt-html-js-wrap"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.10.4"

resolvers += Classpaths.sbtPluginSnapshots

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.1.0")

publishMavenStyle := false

scriptedSettings

scriptedLaunchOpts += ("-Dproject.version=" + version.value)

libraryDependencies += "io.spray" %% "spray-json" % "1.2.6"

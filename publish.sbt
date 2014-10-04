import com.typesafe.sbt.SbtGit._
import bintray.Keys._

versionWithGit

git.baseVersion := "1.0.0"

publishMavenStyle := false

bintrayPublishSettings

repository in bintray := "sbt-plugins"

// This is an example.  bintray-sbt requires licenses to be specified
// (using a canonical name).
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization in bintray := None

packageLabels in bintray := Seq("sbt", "angular", "template")



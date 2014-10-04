lazy val root = (project in file(".")).enablePlugins(SbtWeb)

pipelineStages := Seq(htmlJsWrap)

excludeFilter in htmlJsWrap := "other.html"
lazy val root = (project in file(".")).enablePlugins(SbtWeb)

pipelineStages := Seq(htmlJsWrap)

TaskKey[Unit]("check") <<= Def.task {
  val result = IO.read(baseDirectory.value / "target/web/htmlJsWrap/some.html.js").trim
  val expected = IO.read(baseDirectory.value / "some.html.js").trim
  if (result != expected) {
    sys.error(s"Expected:\n---\n$expected\n---\nGot:\n---\n$result\n---\n")
  }
  ()
}
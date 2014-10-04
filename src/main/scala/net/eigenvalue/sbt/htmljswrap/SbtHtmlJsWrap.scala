package net.eigenvalue.sbt.htmljswrap

import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.pipeline.Pipeline
import sbt.Keys._
import sbt._
import spray.json.JsString

object Import {

  val htmlJsWrap = TaskKey[Pipeline.Stage]("html-js-wrap", "Wraps html files with javascript code.")

  val htmlJsTemplate = SettingKey[(String,String) => String]("html-js-template", "The template to use for wrapping html.")

  val htmlJsAngularModule = SettingKey[String]("html-js-angular-module", "The module name to use in the generated templates.")

  val htmlJsAngularTemplate = SettingKey[(String,String) => String]("html-js-angular-template", "The default javascript template for angular.")
}

object SbtHtmlJsWrap extends AutoPlugin {

  override def requires = SbtWeb

  override def trigger = AllRequirements

  val autoImport = Import

  import com.typesafe.sbt.web.Import.WebKeys._
  import net.eigenvalue.sbt.htmljswrap.SbtHtmlJsWrap.autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    includeFilter in htmlJsWrap := "*.html",
    excludeFilter in htmlJsWrap := HiddenFileFilter,
    target in htmlJsWrap := webTarget.value / htmlJsWrap.key.label,
    htmlJsTemplate := htmlJsAngularTemplate.value,
    htmlJsAngularModule := "templates",
    htmlJsAngularTemplate := { (quotedFileName: String, quotedHtmlContent: String) =>
      s"""
         |angular.module(${JsString(htmlJsAngularModule.value)}).run(["$$templateCache", function($$templateCache) {
         |  $$templateCache.put($quotedFileName,$quotedHtmlContent);
         |}]);
      """.stripMargin
    },
    htmlJsWrap := htmlJsWrapFiles.value
  )

  def htmlJsWrapFiles: Def.Initialize[Task[Pipeline.Stage]] = Def.task {
    mappings =>
      val template = htmlJsTemplate.value
      val targetDir = (target in htmlJsWrap).value
      val include = (includeFilter in htmlJsWrap).value
      val exclude = (excludeFilter in htmlJsWrap).value
      val htmlJsWrapMappings = for {
        (file, path) <- mappings if !file.isDirectory && include.accept(file) && !exclude.accept(file)
      } yield {
        val jsPath = path + ".js"
        val jsFile = targetDir / jsPath
        val htmlContent = IO.read(file)
        val jsContent = template(JsString(path).toString(), JsString(htmlContent).toString())
        IO.write(jsFile, jsContent)
        (jsFile, jsPath)
      }
      mappings ++ htmlJsWrapMappings
  }
}

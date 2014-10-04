sbt-html-js-wrap
================

[sbt-web](https://github.com/sbt/sbt-web) plugin for converting HTML files to JavaScript files wrapping the HTML.

In combination with [sbt-uglify](https://github.com/sbt/sbt-uglify) to combine all project JavaScript- and
Template-Files to one big JavaScript-file.

[![Build Status](https://travis-ci.org/kolloch/sbt-html-js-wrap.png?branch=master)](https://travis-ci.org/kolloch/sbt-html-js-wrap)

Add plugin
----------

*This doesn't work yet. I am still working on publishing this.*

Add the plugin to `project/plugins.sbt`. For example:

```scala
addSbtPlugin("net.eigenvalue" % "sbt-html-js-wrap" % "1.0.0")
```

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project.in file(".")).enablePlugins(SbtWeb)

As with all sbt-web asset pipeline plugins you must declare their order of execution e.g.:

```scala
pipelineStages := Seq(htmlJsWrap)
```

Configuration
-------------

### Template

By default the code generated is suitable for angular:

```javascript
angular.module("templates").run(["$templateCache", function($templateCache) {
  $templateCache.put("some.html","<html><body>BODY</body></html>");
}]);
```

The module name can be customized by:

```scala
htmlJsAngularModule := "myModule"
```

The complete template can be changed like this:

```scala
htmlJsTemplate := { (quotedFileName: String, quotedHtmlContent: String) =>
      s"""
         |angular.module(${JsString(htmlJsAngularModule.value)}).run(["$$templateCache", function($$templateCache) {
         |  $$templateCache.put($quotedFileName,$quotedHtmlContent);
         |}]);
      """.stripMargin
    }
```

You need to import `spray.json.JsString` for the JSON quoting like this

```scala
import spray.json.JsString
```

If you generally useful templates, I like to include them as easily available alternative settings.
Please send me an email/message.

### Filters

Include and exclude filters can be provided. For example to only include *Template.html files:

```scala
includeFilter in htmlJsWrap := "*Template.html"
```

Or to exclude a vendor `vendor.html` file:

```scala
excludeFilter in htmlJsWrap := "vendor.html"
```

The default filter is to include all `.html` files:

```scala
includeFilter in htmlJsWrap := "*.html"
```

License
-------

This code is licensed under the [Apache 2.0 License][apache].

[sbt-web]: https://github.com/sbt/sbt-web
[apache]: http://www.apache.org/licenses/LICENSE-2.0.html

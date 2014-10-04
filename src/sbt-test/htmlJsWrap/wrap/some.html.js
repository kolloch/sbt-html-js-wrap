
angular.module("templates").run(["$templateCache", function($templateCache) {
  $templateCache.put("some.html","<html><body>BODY</body></html>");
}]);

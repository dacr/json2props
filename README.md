# json2props [![Build Status][travisImg]][travisLink] [![License][licenseImg]][licenseLink] [![Codacy][codacyImg]][codacyLink] [![codecov][codecovImg]][codecovLink]

JSON flattened into key value pairs.

JSon2Properties.flattenJSon function flatten a JSon tree into a map of pairs.

```scala
{"a" : 1 } becomes Map("a"->1)
{"a" : { "b" : 1 } } becomes Map("a.b" -> 1)
{"a" : { "b" : [100, 200] } } becomes Map("a.b.0" -> 100, "a.b.1" -> 200)
```

In your build.sbt, add this :

`libraryDependencies += "fr.janalyse" %% "json2props" % version`

Latest `version`: [![Maven][mavenImg]][mavenLink] [![Scaladex][scaladexImg]][scaladexLink]

[mavenImg]: https://img.shields.io/maven-central/v/fr.janalyse/json2props_2.12.svg
[mavenImg2]: https://maven-badges.herokuapp.com/maven-central/fr.janalyse/json2props_2.12/badge.svg
[mavenLink]: https://search.maven.org/#search%7Cga%7C1%7Cfr.janalyse.json2props

[scaladexImg]: https://index.scala-lang.org/dacr/json2props/json2props/latest.svg
[scaladexLink]: https://index.scala-lang.org/dacr/json2props

[licenseImg]: https://img.shields.io/github/license/dacr/json2props.svg
[licenseImg2]: https://img.shields.io/:license-apache2-blue.svg
[licenseLink]: LICENSE

[codacyImg]: https://img.shields.io/codacy/a23d442ea78f40b08e016e2f2fff5709.svg
[codacyImg2]: https://api.codacy.com/project/badge/grade/a23d442ea78f40b08e016e2f2fff5709
[codacyLink]: https://www.codacy.com/app/dacr/json2props/dashboard

[codecovImg]: https://img.shields.io/codecov/c/github/dacr/json2props/master.svg
[codecovImg2]: https://codecov.io/github/dacr/json2props/coverage.svg?branch=master
[codecovLink]: http://codecov.io/github/dacr/json2props?branch=master

[travisImg]: https://img.shields.io/travis/dacr/json2props.svg
[travisImg2]: https://travis-ci.org/dacr/json2props.png?branch=master
[travisLink]:https://travis-ci.org/dacr/json2props

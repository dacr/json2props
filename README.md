# json2props [![Build Status][travisImg]][travisLink] [![License][licenseImg]][licenseLink] [![Maven][mavenImg]][mavenLink] [![Scaladex][scaladexImg]][scaladexLink]

JSON flattened into key value pairs.

JSon2Properties.flattenJSon function flatten a JSon tree into a map of pairs.

```scala
{"a" : 1 } becomes Map("a"->1)
{"a" : { "b" : 1 } } becomes Map("a.b" -> 1)
{"a" : { "b" : [100, 200] } } becomes Map("a.b.0" -> 100, "a.b.1" -> 200)
```

In your build.sbt, add this :

`libraryDependencies += "fr.janalyse" %% "json2props" % version`


[mavenImg]: https://img.shields.io/maven-central/v/fr.janalyse/json2props_2.13.svg
[mavenLink]: https://search.maven.org/#search%7Cga%7C1%7Cfr.janalyse.json2props

[scaladexImg]: https://index.scala-lang.org/dacr/json2props/json2props/latest.svg
[scaladexLink]: https://index.scala-lang.org/dacr/json2props

[licenseImg]: https://img.shields.io/github/license/dacr/json2props.svg
[licenseLink]: LICENSE

[codacyImg]: https://img.shields.io/codacy/e01ab68b61424eb69211acf2699d0756.svg
[codacyLink]: https://www.codacy.com/app/dacr/json2props/dashboard

[codecovImg]: https://img.shields.io/codecov/c/github/dacr/json2props/master.svg
[codecovLink]: http://codecov.io/github/dacr/json2props?branch=master

[travisImg]: https://img.shields.io/travis/dacr/json2props.svg
[travisLink]:https://travis-ci.org/dacr/json2props

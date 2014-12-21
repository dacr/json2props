# JSON flattened into pairs #

JSon2Properties.flattenJSon function flatten a JSon tree into a map of pairs.

```scala
{"a" : 1 } becomes Map("a"->1)
{"a" : { "b" : 1 } } becomes Map("a.b" -> 1)
{"a" : { "b" : [100,200] } } becomes Map("a.b.0" -> 100, "a.b.1" -> 200)
```

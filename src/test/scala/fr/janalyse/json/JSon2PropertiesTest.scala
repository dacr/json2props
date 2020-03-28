/*
 * Copyright 2017-2020 David Crosson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.janalyse.json

import org.scalatest.funsuite._
import org.scalatest.matchers._

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._


class JSon2PropertiesTest extends AnyFunSuite with should.Matchers {


  ignore("basic 0") {
    val m = JSon2Properties.flattenJSon(parse(""))
    m should have size(0)
  }

  test("basic 1") {
    val m = JSon2Properties.flattenJSon(parse("{}"))
    m should have size(0)
  }

  test("basic 2") {
    val m = JSon2Properties.flattenJSon(parse("""{"a" : 0}"""))
    m should have size(1)
    m should contain("a"-> 0)
  }

  
  test("basic 3") {
    val json = parse(""" {"a" : null } """)
    info("JSON input : "+json.toString)
    val m = JSon2Properties.flattenJSon(json)
    info("JSON converted : "+m)
    m should have size(1)
    m should contain("a" -> null )
  }

  
  test("basic 4") {
    val json = parse("""{"name":"Toy","price":35.35}""")
    val m = JSon2Properties.flattenJSon(json)
    m should have size (2)
    m should contain("name" -> "Toy")
    m should contain("price" -> 35.35d)
  }

  test("basic 5") {
    val json = parse(""" { "x":32,  "n" : [1, 2, 3, 4] } """)
    val m = JSon2Properties.flattenJSon(json)
    m should have size (5)
    m should contain("x" -> 32)
    m should contain("n.1" -> 2)
  }

  test("basic 6") {
    val json = parse(""" { "a": {"b" : {"c": { "d" : 123 } } }  } """)
    val m = JSon2Properties.flattenJSon(json)
    m should have size (1)
    m should contain("a.b.c.d" -> 123)
  }

  test("basic 7") {
    val m = JSon2Properties.flattenJSon(parse("""{"a" : true}"""))
    m should have size(1)
    m should contain("a"-> true)
  }
  
  test("basic 8") {
    val m = JSon2Properties.flattenJSon(parse("""{"a" : true, "b": false}"""))
    m should have size(2)
    m should contain("a"-> true)
    m should contain("b"-> false)
  }
  
  test("basic 9") {
    val m = JSon2Properties.flattenJSon(parse("""[]"""))
    m should have size(0)
  }

  test("basic 10") {
    val m = JSon2Properties.flattenJSon(parse("""[100,200,300]"""))
    info(m.toString)
    m should have size(3)
    m should contain("0" -> 100)
  }

  test("basic 11") {
    val m = JSon2Properties.flattenJSon(parse("""[{"a":200}]"""))
    info(m.toString)
    m should have size(1)
    m should contain("0.a" -> 200)
  }

  
  
  test("test with DSL") {
    val bd =
      ("test" -> 123) ~
        ("sub" -> ("x" -> 1) ~ ("y" -> 2)) ~
        ("arr" -> List("10", "20"))
        
    //val json = parse(bd.toString())
    //info(json.toString())

    val m = JSon2Properties.flattenJSon(bd)
    m should have size (5)
    m should contain("test" -> 123)
    m should contain("sub.x" -> 1)
    m should contain("sub.y" -> 2)
    m should contain("arr.0" -> "10")
    m should contain("arr.1" -> "20")
  }

  test("test with mongo server status") {
    val data = 
    """
    { "host" : "zorglub",
      "version" : "2.4.9",
      "process" : "mongod",
      "pid" : 1417,
      "uptime" : 226,
      "uptimeMillis" : 226233,
      "uptimeEstimate" : 208,
      "localTime" : { "$date" : "Thu Jan 23 12:34:10 2014" },
      "asserts" : { "regular" : 0,
        "warning" : 0,
        "msg" : 0,
        "user" : 0,
        "rollovers" : 0 },
      "backgroundFlushing" : { "flushes" : 3,
        "total_ms" : 33,
        "average_ms" : 11,
        "last_ms" : 12,
        "last_finished" : { "$date" : "Thu Jan 23 12:33:24 2014" } },
      "connections" : { "current" : 1,
        "available" : 203,
        "totalCreated" : 23 },
      "cursors" : { "totalOpen" : 0,
        "clientCursors_size" : 0,
        "timedOut" : 0 },
      "dur" : { "commits" : 29,
        "journaledMB" : 0,
        "writeToDataFilesMB" : 0,
        "compression" : 0,
        "commitsInWriteLock" : 0,
        "earlyCommits" : 0,
        "timeMs" : { "dt" : 3025,
          "prepLogBuffer" : 0,
          "writeToJournal" : 0,
          "writeToDataFiles" : 0,
          "remapPrivateView" : 0 } },
      "extra_info" : { "note" : "fields vary by platform",
        "page_faults" : 4 },
      "globalLock" : { "totalTime" : 226233000,
        "lockTime" : 34592,
        "currentQueue" : { "total" : 0,
          "readers" : 0,
          "writers" : 0 },
        "activeClients" : { "total" : 0,
          "readers" : 0,
          "writers" : 0 } },
      "indexCounters" : { "accesses" : 0,
        "hits" : 0,
        "misses" : 0,
        "resets" : 0,
        "missRatio" : 0 },
      "locks" : { "." : { "timeLockedMicros" : { "R" : 9766,
            "W" : 34592 },
          "timeAcquiringMicros" : { "R" : 5021,
            "W" : 785 } },
        "admin" : { "timeLockedMicros" : {},
          "timeAcquiringMicros" : {} },
        "local" : { "timeLockedMicros" : { "r" : 813,
            "w" : 0 },
          "timeAcquiringMicros" : { "r" : 83,
            "w" : 0 } },
        "training" : { "timeLockedMicros" : { "r" : 170,
            "w" : 0 },
          "timeAcquiringMicros" : { "r" : 31,
            "w" : 0 } },
        "tweetstest" : { "timeLockedMicros" : { "r" : 160,
            "w" : 0 },
          "timeAcquiringMicros" : { "r" : 32,
            "w" : 0 } },
        "digg" : { "timeLockedMicros" : { "r" : 764,
            "w" : 0 },
          "timeAcquiringMicros" : { "r" : 53,
            "w" : 0 } },
        "test" : { "timeLockedMicros" : { "r" : 166,
            "w" : 0 },
          "timeAcquiringMicros" : { "r" : 33,
            "w" : 0 } },
        "twitter" : { "timeLockedMicros" : { "r" : 178,
            "w" : 0 },
          "timeAcquiringMicros" : { "r" : 27,
            "w" : 0 } },
        "orange" : { "timeLockedMicros" : { "r" : 634,
            "w" : 28 },
          "timeAcquiringMicros" : { "r" : 73,
            "w" : 20 } } },
      "network" : { "bytesIn" : 1774,
        "bytesOut" : 21220,
        "numRequests" : 28 },
      "opcounters" : { "insert" : 1,
        "query" : 27,
        "update" : 0,
        "delete" : 0,
        "getmore" : 0,
        "command" : 23 },
      "opcountersRepl" : { "insert" : 0,
        "query" : 0,
        "update" : 0,
        "delete" : 0,
        "getmore" : 0,
        "command" : 0 },
      "recordStats" : { "accessesNotInMemory" : 0,
        "pageFaultExceptionsThrown" : 0,
        "digg" : { "accessesNotInMemory" : 0,
          "pageFaultExceptionsThrown" : 0 },
        "local" : { "accessesNotInMemory" : 0,
          "pageFaultExceptionsThrown" : 0 },
        "orange" : { "accessesNotInMemory" : 0,
          "pageFaultExceptionsThrown" : 0 },
        "test" : { "accessesNotInMemory" : 0,
          "pageFaultExceptionsThrown" : 0 },
        "training" : { "accessesNotInMemory" : 0,
          "pageFaultExceptionsThrown" : 0 },
        "tweetstest" : { "accessesNotInMemory" : 0,
          "pageFaultExceptionsThrown" : 0 },
        "twitter" : { "accessesNotInMemory" : 0,
          "pageFaultExceptionsThrown" : 0 } },
      "writeBacksQueued" : false,
      "mem" : { "bits" : 64,
        "resident" : 40,
        "virtual" : 3853,
        "supported" : true,
        "mapped" : 688,
        "mappedWithJournal" : 1376 },
      "metrics" : { "document" : { "deleted" : 0,
          "inserted" : 1,
          "returned" : 21,
          "updated" : 0 },
        "getLastError" : { "wtime" : { "num" : 0,
            "totalMillis" : 0 },
          "wtimeouts" : 0 },
        "operation" : { "fastmod" : 0,
          "idhack" : 0,
          "scanAndOrder" : 0 },
        "queryExecutor" : { "scanned" : 75 },
        "record" : { "moves" : 0 },
        "repl" : { "apply" : { "batches" : { "num" : 0,
              "totalMillis" : 0 },
            "ops" : 0 },
          "buffer" : { "count" : 0,
            "maxSizeBytes" : 268435456,
            "sizeBytes" : 0 },
          "network" : { "bytes" : 0,
            "getmores" : { "num" : 0,
              "totalMillis" : 0 },
            "ops" : 0,
            "readersCreated" : 0 },
          "oplog" : { "insert" : { "num" : 0,
              "totalMillis" : 0 },
            "insertBytes" : 0 },
          "preload" : { "docs" : { "num" : 0,
              "totalMillis" : 0 },
            "indexes" : { "num" : 0,
              "totalMillis" : 0 } } },
        "ttl" : { "deletedDocuments" : 0,
          "passes" : 3 } },
      "ok" : 1 }
    """
    val m = JSon2Properties.flattenJSon(parse(data))
    m.size should be >(0)
    m should contain("ok"->1)
    for {(k,v)<-m} info(s"$k=$v")
  }
  
}


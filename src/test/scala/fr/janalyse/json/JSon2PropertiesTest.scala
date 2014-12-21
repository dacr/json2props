/*
 * Copyright 2014 David Crosson
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

import org.scalatest.FunSuite
import org.scalatest.ShouldMatchers
import org.scalatest.junit.JUnitRunner

import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._

class JSon2PropertiesTest extends FunSuite with ShouldMatchers {


  test("basic 0") {
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
    val json = parse("""{"a" : null }""")
    info(json.toString)
    val m = JSon2Properties.flattenJSon(json)
    m should have size(1)
    m should contain("a"-> None)
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

}


/*
 * Copyright 2015 David Crosson
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
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

class Xml2JsonTest extends FunSuite with ShouldMatchers {
  import Xml2Json._
  import JSon2Properties._
  
  def jinfo(in:Map[String,Any]):Unit = {
    for {(k,v)<-in} info(s"$k=$v")
  }
  
  test("basic 0") {
    val m1 = json2props(xml2json("<a>1</a>"))
    jinfo(m1)
    val m2 = json2props(xml2json("<a><b>1</b></a>"))
    jinfo(m2)
  }
  
  test("basic 1") {
    val xml=
      """
        <servlet>
            <servlet-name>jsp</servlet-name>
            <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
            <init-param>
                <param-name>fork</param-name>
                <param-value>false</param-value>
            </init-param>
            <init-param>
                <param-name>xpoweredBy</param-name>
                <param-value>false</param-value>
            </init-param>
            <load-on-startup>3</load-on-startup>
        </servlet>
        """
    val p = json2props(xml2json(xml))
    jinfo(p)
  }
  
}


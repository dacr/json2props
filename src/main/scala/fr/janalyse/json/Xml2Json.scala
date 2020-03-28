/*
 * Copyright 2014-2020 David Crosson
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

import fr.janalyse.json.{Xml=>JXML}
import org.json4s.JValue
import org.json4s.jackson.JsonMethods.{pretty,render}

import scala.xml.{XML=>SXML}

import scala.xml.NodeSeq

/**
 * @author David Crosson
 */
object Xml2Json {
  
  def xml2json(xml:NodeSeq):JValue={
    JXML.toJson(xml)
  }
  
  def xml2json(xml:String):JValue={
    xml2json(SXML.loadString(xml))
  }
  
  def json2str(json:JValue):String ={
    pretty(render(json))
  }
}
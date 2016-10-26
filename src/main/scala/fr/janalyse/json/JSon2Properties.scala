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

import org.json4s.JsonDSL._
import org.json4s._
import scala.util.{ Try, Success }
import annotation.tailrec

object JSon2Properties {
  val DEFKEY = "default"::Nil

  type JSonKey = List[String]
  
  private def convertWorker(value: Any, key: JSonKey): Map[JSonKey, Any] = {
    val curkey=if (key.isEmpty) DEFKEY else key
    value match {
      case null                     => Map(curkey -> null)
      case JNull                    => Map(curkey -> null)
      case JNothing                 => Map(curkey -> None)
      case JString(v)               => Map(curkey -> v)
      case JDouble(v)               => Map(curkey -> v)
      case JDecimal(v)              => Map(curkey -> v)
      case JInt(v)                  => Map(curkey -> v)
      case JBool(v)                 => Map(curkey -> v)
      case JField(subkey, newvalue) => convertWorker(newvalue, subkey::key)
      case JObject(content) =>
        content
          .groupBy{case (subkey,value) => subkey}
          .flatMap{
            case (subkey, subvalue::Nil) => convertWorker(subvalue, key)
            case (subkey, subvalues) => 
              subvalues
                .map{case (_,subvalue) => subvalue}
                .zipWithIndex
                .flatMap{case (subvalue,index) => convertWorker(subvalue,(index.toString::subkey::key))}
            }
      case JArray(v) =>
        val r = v.zipWithIndex.flatMap {
          case (sv, i)         => convertWorker(sv, i.toString::key)
        }
        r.toMap
      case v => Map(curkey -> v)
    }
  }

  private def convert(value: Any, key: JSonKey): Map[JSonKey, Any] = {
    convertWorker(value, key).map{case (k,v) => k.reverse->v}
  }

  def flattenJSon(bd: Any, base: Option[String] = None): Map[String, Any] = {
    val basekey = base.map(_.split("[.]").toList).getOrElse(Nil)
    convert(bd, basekey).map{case (k,v) => k.mkString(".")->v}
  }
  def json2props(in: Any): Map[String, Any] = flattenJSon(in)
  def json2propsKey(in: Any): Map[JSonKey, Any] = convert(in, Nil)
}

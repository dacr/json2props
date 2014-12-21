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

package dummy

import org.json4s.JsonDSL._
import org.json4s._
import scala.util.{ Try, Success }

object JSon2Properties {
  private def mkkey(key: String, subkey: Any): String =
    if (key.size > 0) key + "." + subkey else subkey.toString

  private def convert(key: String, bv: Any): Map[String, Any] = {
    val result = bv match {
      case v: Tuple2[String, _] => toProperties(v._2, v._1)
      case v: JString           => Map(key -> v.values)
      case v: JDouble           => Map(key -> v.values)
      case v: JDecimal          => Map(key -> v.values)
      case v: JInt              => Map(key -> v.values)
      case v: JBool             => Map(key -> v.values)
      case v: JObject           => v.values.flatMap { case (subk, sv) => convert(mkkey(key, subk), sv) }
      case v: JArray            => v.values.zipWithIndex.flatMap { case (sv, i) => convert(mkkey(key, i), sv) }
      case v: Iterable[_] => v.zipWithIndex.flatMap {
        case ((subk, sv), _) => convert(mkkey(key, subk), sv)  // for map for example
        case (sv, i) => convert(mkkey(key,i), sv)
      }
      case JNull    => Map(key -> "")
      case JNothing => Map(key -> "")
      case v        => Map(key -> v)
    }
    result.toMap
  }

  def toProperties(bd: Any, base: String = ""): Map[String, Any] = {
    val result = convert(base, bd)
    result.toMap
  }

}





/*
import reactivemongo.bson._
import scala.util.{ Try, Success }

object JSon2Properties {
  private def convert(key:String, bv: BSONValue): Map[String, String] = {
    val result = bv match {
      case v: BSONDocument     => toProperties(v, key)
      case v: BSONArray        => v.values.zipWithIndex.flatMap { case (sv, i) => convert(key + "." + i, sv) }
      case v: BSONBoolean      => Map(key -> v.value.toString)
      case v: BSONDouble       => Map(key -> v.value.toString)
      case v: BSONInteger      => Map(key -> v.value.toString)
      case v: BSONLong         => Map(key -> v.value.toString)
      case v: BSONString       => Map(key -> v.value)
      case v: BSONDateTime     => Map(key -> v.value.toString)
      case v: BSONTimestamp    => Map(key -> v.value.toString)
      case v: BSONSymbol       => Map(key -> v.value.toString)
      case v: BSONBinary       => Map() // TODO : NotYetImplemented, so ignored
      case v: BSONDBPointer    => Map() // TODO : NotYetImplemented, so ignored
      case v: BSONJavaScript   => Map() // TODO : NotYetImplemented, so ignored
      case v: BSONJavaScriptWS => Map() // TODO : NotYetImplemented, so ignored
      case v: BSONObjectID     => Map() // TODO : NotYetImplemented, so ignored
      case v: BSONRegex        => Map() // TODO : NotYetImplemented, so ignored
      case v => Map() // TODO : temporary to avoid any other unsupported types
      //        case v:BSONMaxKey =>       key -> ???
      //        case v:BSONMinKey =>       key -> ???
      //        case v:BSONNull =>         key -> ???
      //        case v:BSONUndefined =>    key -> ???
    }
    result.toMap
  }
  
  def toProperties(bd: BSONDocument, base: String = ""): Map[String, String] = {
    val result = bd.stream.collect { case Success(x) => x }.flatMap {
      case (curkey, value) =>
        val key = if (base.size==0) curkey else base+"."+curkey
        convert(key, value)
    }
    result.toMap
  }
  
}

*/
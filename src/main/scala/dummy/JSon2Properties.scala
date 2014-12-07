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

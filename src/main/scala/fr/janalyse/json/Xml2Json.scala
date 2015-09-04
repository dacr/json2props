package fr.janalyse.json

import org.json4s.{Xml=>JXML}
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
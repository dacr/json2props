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

  def jinfo(in: Map[String, Any]): Unit = {
    for { (k, v) <- in.toList.sortBy { case (k, v) => k } } info(s"$k=$v")
  }

  implicit class asStringHolder(in: Map[String, Any]) {
    def headAsString: String = {
      in.toList.headOption.map { case (k, v) => s"$k=$v" }.getOrElse("")
    }
    def asStringList: List[String] = {
      in.toList.map { case (k, v) => s"$k=$v" }.sorted
    }
    def asStringSet: Set[String] = {
      in.map { case (k, v) => s"$k=$v" }.toSet
    }
  }

  test("basic 1") {
    val m = json2props(xml2json("<a>1</a>"))
    m should have size (1)
    m.headAsString should equal("a=1")
  }
  test("basic 2") {
    val m = json2props(xml2json("<a><b>1</b></a>"))
    m.headAsString should equal("a.b=1")
  }
  test("basic 3") {
    val x3 = xml2json("<a><b>1</b><b>2</b></a>")
    val m3 = json2props(x3)
    m3.asStringList should equal(List("a.b.0=1", "a.b.1=2"))
  }
  test("basic 4") {
    val x4 = xml2json("""<a> <b n="B1"><c>C1</c></b> <b n="B2"><c>C2</c></b></a>""")
    val m4 = json2props(x4)
    m4.asStringSet should equal(
      Set(
        "a.b.0.n=B1",
        "a.b.0.c=C1",
        "a.b.1.n=B2",
        "a.b.1.c=C2"
      ))
  }
  test("basic 5") {
    val x5 = xml2json("""<a> <b n="B1"/> <b n="B2"/></a>""")
    val m5 = json2props(x5)
    m5.asStringSet should equal(
      Set(
        "a.b.0.n=B1",
        "a.b.1.n=B2"
      ))
  }
  ignore("basic 6") { // TO DEBUG
    val x6 = xml2json("""<a> <b n="B1">1</b><b n="B2">2</b></a>""")
    val m6 = json2props(x6)
    m6.asStringSet should equal(
      Set(
        "a.b.0.n=B1",
        "a.b.0=1",
        "a.b.1.n=B2",
        "a.b.1=2"
      ))
  }
  test("basic 7A") {
    val x7 = xml2json("""<a n="A1"><b n="B1"></b> <b n="B2"></b></a>""")
    val m7 = json2props(x7)
    m7.asStringSet should equal(
      Set(
        "a.n=A1",
        "a.b.0.n=B1",
        "a.b.1.n=B2"
      ))
  }
  test("basic 7B") {
    val x7 = xml2json("""<a n="A1"><b n="B1"></b> <x n="X0"/><b n="B2"></b></a>""")
    val m7 = json2props(x7)
    m7.asStringSet should equal(
      Set(
        "a.n=A1",
        "a.b.0.n=B1",
        "a.b.1.n=B2",
        "a.x.n=X0"
      ))
  }
  test("basic 8") {
    val x8 = xml2json("""<a n="A1"><b n="B1"><c n="C1"/></b><b n="B2"><c n="C2"/></b></a>""")
    val m8 = json2props(x8)
    m8.asStringSet should equal(
      Set(
        "a.n=A1",
        "a.b.0.n=B1",
        "a.b.1.n=B2",
        "a.b.0.c.n=C1",
        "a.b.1.c.n=C2"
      ))
  }

  test("real case 0") {
    val xml =
      """
<Server port="8005" shutdown="SHUTDOWN">
  <Service name="Catalina">  
    <Connector port="8080" redirectPort="8443"/>
  </Service>
</Server>
        """
    val x = xml2json(xml)
    val p = json2props(x)
    jinfo(p)
    p should have size (5)
    p.asStringSet should equal(Set(
      "Server.Service.Connector.port=8080",
      "Server.Service.Connector.redirectPort=8443",
      "Server.Service.name=Catalina",
      "Server.port=8005",
      "Server.shutdown=SHUTDOWN"
    ))
  }

  test("real case 1") {
    val xml =
      """
<Server port="8005" shutdown="SHUTDOWN">
  <Service name="Catalina">
    <Executor name="OrangeThreadPool"
              namePrefix="orange-exec-"
              maxThreads="200"
              minSpareThreads="20"
              prestartminSpareThreads="true"
              maxIdleTime="60000"
              />
    <Connector port="8080" 
               redirectPort="8443"
               address="0.0.0.0"
               acceptCount="500"
               connectionTimeout="5000"
               keepAliveTimeout="5000"
               maxKeepAliveRequests="100"
               maxConnections="10000"
               protocol="org.apache.coyote.http11.Http11NioProtocol"
               enableLookups="false"
               useExecutor="true"
               executor="OrangeThreadPool"
               acceptorThreadCount="1"
               pollerThreadCount="1"
               />
    <Executor name="OrangeAjpThreadPool"
              namePrefix="orange-ajp-exec-"
              maxThreads="200"
              minSpareThreads="20"
              prestartminSpareThreads="true"
              maxIdleTime="60000"
              />

    <Connector port="8009"
               redirectPort="8443"
               address="127.0.0.1"
               acceptCount="500"
               connectionTimeout="5000"
               keepAliveTimeout="60000"
               maxConnections="10000"
               protocol="org.apache.coyote.ajp.AjpNioProtocol"
               enableLookups="false"
               useExecutor="true"
               executor="OrangeAjpThreadPool"
               acceptorThreadCount="1"
               />
  </Service>
</Server>
        """
    val x = xml2json(xml)
    val p = json2props(x)
    jinfo(p)
    p should have size (41)
    p.asStringSet should equal(Set(
      "Server.Service.Connector.0.acceptCount=500",
      "Server.Service.Connector.0.acceptorThreadCount=1",
      "Server.Service.Connector.0.address=0.0.0.0",
      "Server.Service.Connector.0.connectionTimeout=5000",
      "Server.Service.Connector.0.enableLookups=false",
      "Server.Service.Connector.0.executor=OrangeThreadPool",
      "Server.Service.Connector.0.keepAliveTimeout=5000",
      "Server.Service.Connector.0.maxConnections=10000",
      "Server.Service.Connector.0.maxKeepAliveRequests=100",
      "Server.Service.Connector.0.pollerThreadCount=1",
      "Server.Service.Connector.0.port=8080",
      "Server.Service.Connector.0.protocol=org.apache.coyote.http11.Http11NioProtocol",
      "Server.Service.Connector.0.redirectPort=8443",
      "Server.Service.Connector.0.useExecutor=true",
      "Server.Service.Connector.1.acceptCount=500",
      "Server.Service.Connector.1.acceptorThreadCount=1",
      "Server.Service.Connector.1.address=127.0.0.1",
      "Server.Service.Connector.1.connectionTimeout=5000",
      "Server.Service.Connector.1.enableLookups=false",
      "Server.Service.Connector.1.executor=OrangeAjpThreadPool",
      "Server.Service.Connector.1.keepAliveTimeout=60000",
      "Server.Service.Connector.1.maxConnections=10000",
      "Server.Service.Connector.1.port=8009",
      "Server.Service.Connector.1.protocol=org.apache.coyote.ajp.AjpNioProtocol",
      "Server.Service.Connector.1.redirectPort=8443",
      "Server.Service.Connector.1.useExecutor=true",
      "Server.Service.Executor.0.maxIdleTime=60000",
      "Server.Service.Executor.0.maxThreads=200",
      "Server.Service.Executor.0.minSpareThreads=20",
      "Server.Service.Executor.0.name=OrangeThreadPool",
      "Server.Service.Executor.0.namePrefix=orange-exec-",
      "Server.Service.Executor.0.prestartminSpareThreads=true",
      "Server.Service.Executor.1.maxIdleTime=60000",
      "Server.Service.Executor.1.maxThreads=200",
      "Server.Service.Executor.1.minSpareThreads=20",
      "Server.Service.Executor.1.name=OrangeAjpThreadPool",
      "Server.Service.Executor.1.namePrefix=orange-ajp-exec-",
      "Server.Service.Executor.1.prestartminSpareThreads=true",
      "Server.Service.name=Catalina",
      "Server.port=8005",
      "Server.shutdown=SHUTDOWN"
    ))
  }

  test("real case 2") {
    val xml =
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
    val x = xml2json(xml)
    val p = json2props(x)
    jinfo(p)
    p.asStringList should equal(List(
      "servlet.init-param.0.param-name=fork",
      "servlet.init-param.0.param-value=false",
      "servlet.init-param.1.param-name=xpoweredBy",
      "servlet.init-param.1.param-value=false",
      "servlet.load-on-startup=3",
      "servlet.servlet-class=org.apache.jasper.servlet.JspServlet",
      "servlet.servlet-name=jsp"
    ))
  }

  test("real case 3") {
    val xml = //<?xml version="1.0" encoding="ISO-8859-1"?>
      """
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                      http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
  version="3.0">
  
    <servlet>
        <servlet-name>default</servlet-name>
        <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
        <init-param>
            <param-name>debug</param-name>
            <param-value>0</param-value>
        </init-param>
        <init-param>
            <param-name>listings</param-name>
            <param-value>false</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    
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
   <welcome-file-list>
        <welcome-file>index.html</welcome-file>
        <welcome-file>index.htm</welcome-file>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>

</web-app>

        """
    val x = xml2json(xml)
    val p = json2props(x)
    jinfo(p)
    p.asStringSet should equal(Set(
      "web-app.schemaLocation=http://java.sun.com/xml/ns/javaee                       http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd",
      "web-app.servlet.0.init-param.0.param-name=debug",
      "web-app.servlet.0.init-param.0.param-value=0",
      "web-app.servlet.0.init-param.1.param-name=listings",
      "web-app.servlet.0.init-param.1.param-value=false",
      "web-app.servlet.0.load-on-startup=1",
      "web-app.servlet.0.servlet-class=org.apache.catalina.servlets.DefaultServlet",
      "web-app.servlet.0.servlet-name=default",
      "web-app.servlet.1.init-param.0.param-name=fork",
      "web-app.servlet.1.init-param.0.param-value=false",
      "web-app.servlet.1.init-param.1.param-name=xpoweredBy",
      "web-app.servlet.1.init-param.1.param-value=false",
      "web-app.servlet.1.load-on-startup=3",
      "web-app.servlet.1.servlet-class=org.apache.jasper.servlet.JspServlet",
      "web-app.servlet.1.servlet-name=jsp",
      "web-app.version=3.0",
      "web-app.welcome-file-list.welcome-file.0=index.html",
      "web-app.welcome-file-list.welcome-file.1=index.htm",
      "web-app.welcome-file-list.welcome-file.2=index.jsp"
    ))
  }

}


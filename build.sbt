import org.scalastyle.sbt.{PluginKeys => StylePluginKeys, ScalastylePlugin}


val jdkVersion = settingKey[String]("Revision of the JDK used to build this project.")

lazy val scalaTime = project.in(file(".")).configs(Fmpp)

name <<= (name, jdkVersion)((n, v) => if (v == "1.7") s"$n Threeten" else n)

site.settings

ghpages.settings

fmppSettings

version := "0.1.0-SNAPSHOT"

organization := "codes.reactive"

description := "Basic Scala wrapper for easier use of JDK 1.8.0 (JSR-310) or Threeten BP time libraries."

startYear := Some(2014)

homepage := Some(url("https://oss.reactive.codes/scala-time"))

apiURL := Some(url(s"http://oss.reactive.codes/scala-time/${version.value}/"))

apacheLicensed

publishOSS

jdkVersion := System.getProperty("java.specification.version")

scalaVersion := "2.11.1"

crossScalaVersions := Seq("2.11.1", "2.10.4")

libraryDependencies ++= {
  def dependencies = Seq(scalaTest)
  jdkVersion.value match {
    case "1.8" => dependencies
    case "1.7" => dependencies :+ threeten
    case _ => sys.error("Java JDK version not supported. Use JDK 1.8 or 1.7.")
  }
}

codesCompileOpts

codesDocOpts

codesUnidocOpts

scalacOptions in (Compile, compile) += "-language:postfixOps"

unmanagedSourceDirectories in Compile += (sourceDirectory in Compile).value / s"scala_${scalaBinaryVersion.value}"

unmanagedSourceDirectories in Compile += (sourceDirectory in Compile).value / s"jdk_${jdkVersion.value}"

unmanagedSourceDirectories in Test += (sourceDirectory in Test).value / s"jdk_${jdkVersion.value}"

codesDevelopers := Some(Seq(Developer("Ali Salim Rashid", "arashi01")))

ScalastylePlugin.Settings

StylePluginKeys.config <<= baseDirectory(_ / "project/scalastyle-config.xml")

SiteKeys.siteMappings := Seq(baseDirectory.value / "project/site.html" -> "index.html")

SiteKeys.siteMappings <++= (mappings in (ScalaUnidoc, packageDoc), version) map {(m,v) =>
        for ((f, d) <- m) yield (f, s"$v/$d") }

git.remoteRepo := codesGithubRepo.value.developerConnection.drop(8)

def scalaTest = "org.scalatest" %% "scalatest" % "2.1.5" % "test"

def threeten = "org.threeten" % "threetenbp" % "1.0"

fmppArgs ++= Seq(s"-DunderlyingBase:${if (jdkVersion.value == "1.7") "org.threeten.bp" else "java.time"}")
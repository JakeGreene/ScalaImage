name := "manipulation"

version := "0.1"

scalaVersion := "2.10.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq( 
    "org.scalatest" % "scalatest_2.10" % "2.0.RC2" % "test",
    "org.scala-lang" % "scala-reflect" % "2.10.2",
    "org.mockito" % "mockito-all" % "1.9.5",
    "org.scalanlp" % "breeze_2.10" % "0.5.2"
)

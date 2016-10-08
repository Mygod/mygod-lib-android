import android.Keys._

android.Plugin.androidBuildAar

name := "mygod-lib-android"

organization := "be.mygod"

version := "4.0.0"

scalaVersion := "2.11.8"

platformTarget in Android := "android-24"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-target:jvm-1.7", "-Xexperimental")

typedResources in Android := false

proguardVersion in Android := "5.3"

libraryDependencies ++= Seq(
  "com.android.support" % "customtabs" % "24.2.1",
  "com.android.support" % "design" % "24.2.1",
  "com.android.support" % "preference-v14" % "24.2.1"
)

pomExtra in Global := {
  <url>https://github.com/Mygod/mygod-lib-android</url>
    <licenses>
      <license>
        <name>GPLv3</name>
        <url>http://www.gnu.org/licenses/gpl-3.0.en.html</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/Mygod/mygod-lib-android</connection>
      <developerConnection>scm:git:git@github.com:mygod-lib-android</developerConnection>
      <url>github.com/Mygod/mygod-lib-android.git</url>
    </scm>
    <developers>
      <developer>
        <id>Mygod</id>
        <name>Mygod</name>
        <url>https://mygod.be</url>
      </developer>
    </developers>
}

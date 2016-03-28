import android.Keys._

android.Plugin.androidBuildAar

name := "mygod-lib-android"

organization := "tk.mygod"

version := "1.3.11"

scalaVersion := "2.11.8"

platformTarget in Android := "android-23"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalacOptions ++= Seq("-target:jvm-1.6", "-Xexperimental")

libraryDependencies ++= Seq(
  "com.android.support" % "customtabs" % "23.2.1",
  "com.android.support" % "design" % "23.2.1",
  "com.android.support" % "preference-v14" % "23.2.1"
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
        <url>https://mygod.tk</url>
      </developer>
    </developers>
}

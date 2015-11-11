import android.Keys._

android.Plugin.androidBuildAar

name := "mygod-lib-android"

version := "1.3.0"

scalaVersion := "2.11.7"

platformTarget in Android := "android-23"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalacOptions ++= Seq("-target:jvm-1.6", "-Xexperimental")

// Dependencies should include these to build:
// Duplicate mygod-lib-android's dependencies due to bug in android-sdk-plugin.
libraryDependencies ++= Seq(
  "com.android.support" % "design" % "23.1.0",
  "com.android.support" % "preference-v14" % "23.1.0",
  "com.android.support" % "support-v13" % "23.1.0"
)

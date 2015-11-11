import android.Keys._

android.Plugin.androidBuild

name := "mygod-lib-android"

version := "1.3.0"

scalaVersion := "2.11.7"

platformTarget in Android := "android-23"

scalacOptions ++= Seq("-target:jvm-1.6", "-Xexperimental")

libraryDependencies ++= Seq(
  "com.android.support" % "appcompat-v7" % "23.1.0",
  "com.android.support" % "design" % "23.1.0",
  "com.android.support" % "preference-v14" % "23.1.0",
  "com.android.support" % "support-v13" % "23.1.0"
)

scalaVersion := "2.11.9"

enablePlugins(AndroidLib)

name := "mygod-lib-android"
organization := "be.mygod"
version := "4.0.5-SNAPSHOT"

platformTarget in Android := "android-25"

compileOrder := CompileOrder.JavaThenScala
javacOptions ++= "-source" :: "1.7" :: "-target" :: "1.7" :: Nil
scalacOptions ++= "-target:jvm-1.7" :: "-Xexperimental" :: Nil

proguardVersion := "5.3.3"
proguardCache := Seq()

pseudoLocalesEnabled := true
typedResources := false

val supportLibsVersion = "25.3.1"
libraryDependencies ++=
  "com.android.support" % "customtabs" % supportLibsVersion ::
  "com.android.support" % "design" % supportLibsVersion ::
  "com.android.support" % "preference-v14" % supportLibsVersion ::
  Nil

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

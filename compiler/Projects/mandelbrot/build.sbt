// 重要な注意：各設定の間に必ず空行を挿入して下さい。これをしないと sbt が起動しません。

name := "cs1.assignment5"      // プロジェクトの名称

version := "0.1.0"       // プロジェクトのバージョン番号

scalaVersion := "2.11.7" // コンパイルに使う scalac のバージョン

scalacOptions ++=        // scalac に与えるオプション
  Seq("-optimize",
    "-feature",
    "-unchecked",
    "-deprecation",
    "-Ylog-classpath")

javaOptions in run ++=   // 仮想機械に与えるオプション
  Seq( "-Xmx2G", "-verbose:gc")

libraryDependencies +=  // テストのために使う ScalaTest ライブラリ
  "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test"


 libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.60-R9"


unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar")

// addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)

// libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2"

// sbt の挙動の設定

fork := true

connectInput := true

// ソースコードの在処を非標準の場所に設定

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test"

// コンパイル結果を非標準の場所に設定

// target := Path.userHome / "tmp" / "cs1f" / "lx06"

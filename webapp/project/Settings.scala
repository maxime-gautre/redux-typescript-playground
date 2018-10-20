import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import sbt._
import sbt.Keys._
//import com.typesafe.sbt.digest.SbtDigest.autoImport._
//import com.typesafe.sbt.gzip.SbtGzip.autoImport._
import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._
import com.typesafe.sbt.web.SbtWeb.autoImport._
import play.sbt.PlayImport.{PlayKeys, ws}
import play.sbt.routes.RoutesKeys
import sbt.internal.io.Source
import sbtbuildinfo.BuildInfoPlugin.autoImport._
import sbtrelease.ReleasePlugin.autoImport._

object Settings {
  val timestamp = new java.text.SimpleDateFormat("yyyyMMdd-HHmm").format(new java.util.Date())

  lazy val common = Seq(
    scalacOptions := Seq(
      "-encoding",
      "UTF-8",
      "-target:jvm-1.8",
      "-unchecked",
      "-deprecation",
      "-feature",
      "-g:vars",
      "-Xlint:_",
      "-opt:l:inline",
      "-opt-inline-from",
      "-explaintypes",                     // Explain type errors in more detail.
      "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
      "-Xfuture",                          // Turn on future language features.
      "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
      "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
      "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
      "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
      "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
      "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
      "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
      "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
      "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
      "-Xlint:option-implicit",            // Option.apply used implicit view.
      "-Xlint:package-object-classes",     // Class or object defined in package object.
      "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
      "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
      "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
      "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
      "-Xlint:unsound-match",              // Pattern match may not be typesafe.
      "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
      "-Ypartial-unification",             // Enable partial unification in type constructor inference
      "-Ywarn-dead-code",                  // Warn when dead code is identified.
      "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
      "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
      "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
      "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
      "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
      "-Ywarn-numeric-widen",              // Warn when numerics are widened.
      "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
      "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
      "-Ywarn-unused:locals",              // Warn if a local definition is unused.
      "-Ywarn-unused:params",              // Warn if a value parameter is unused.
      "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
      "-Ywarn-unused:privates",            // Warn if a private member is unused.
      "-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
    ),
    // name dist with timestamp
    packageName in Universal := s"${name.value}-${version.value}-$timestamp",
  ) /*++ scalaFmtSettings*/

  lazy val commonPlay = common ++ Seq(
    buildInfoPackage := "sbt",
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      scalaVersion,
      BuildInfoKey.constant("buildTime" -> timestamp)
    ),
    RoutesKeys.routesImport := Seq(),
    // dont include local.conf in dist
    mappings in Universal := {
      val origMappings = (mappings in Universal).value
      origMappings.filterNot { case (_, file) => file.endsWith("local.conf") }
    },
    libraryDependencies ++= Seq(
      Dependencies.scalatest.play % Test
    ),
    releaseProcess := releaseSteps
  )

  lazy val releaseSteps = {
    import ReleaseTransformations._
    Seq[ReleaseStep](
      runClean,
      checkSnapshotDependencies,
      inquireVersions,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  }

  lazy val scalaFmtSettings = Seq(
    scalafmtVersion := "1.3.0",
    scalafmtOnCompile := true
  )

  def getSourceFile(source: Source): java.io.File = {
    val cl = classOf[Source]
    val baseField = cl.getDeclaredField("base")
    baseField.setAccessible(true)
    baseField.get(source).asInstanceOf[java.io.File]
  }
}

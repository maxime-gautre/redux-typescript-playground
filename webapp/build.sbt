scalaVersion in ThisBuild := "2.12.7"

name := """feature-flipping-mgmt"""

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, BuildInfoPlugin, SbtWeb)
  .settings(Settings.commonPlay: _*)
  .settings(
    libraryDependencies ++= Seq(
      jdbc,
      evolutions,
      "com.typesafe.play" %% "anorm" % "2.5.3",
      "org.postgresql" % "postgresql" % "42.2.2",
      "org.mockito" % "mockito-core" % "2.21.0" % Test,
    )
  )

package it.nicolasfarabegoli.gradle

import io.kotest.core.spec.style.WordSpec
import io.kotest.engine.spec.tempdir
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome

/**
 * Test class for the plugin.
 */
class ConventionalCommitsTest : WordSpec({
    "The plugin" `when` {
        "is applied without configuration" should {
            "write the default script" {
                val projectDirectory = tempdir()
                projectDirectory.resolve(".git/hooks").mkdirs()
                projectDirectory.configureSettingsGradle { "" }
                projectDirectory.configureBuildGradle {
                    """
                        plugins {
                            id("it.nicolasfarabegoli.conventional-commits")
                        }
                    """.trimIndent()
                }

                val tasks = GradleRunner.create()
                    .forwardOutput()
                    .withPluginClasspath()
                    .withProjectDir(projectDirectory)
                    .withArguments("tasks")
                    .build()
                tasks.task(":tasks")?.outcome shouldBe TaskOutcome.SUCCESS

                projectDirectory.resolve(".git/hooks/commit-msg").exists() shouldBe true
            }
        }
    }
//    val projectDir = File("build/gradleTest")
//    fun setupDefaultTest() {
//        projectDir.mkdirs()
//        File(projectDir.absolutePath, ".git/hooks").mkdirs()
//        projectDir.resolve("settings.gradle.kts").writeText("")
//        projectDir.resolve("build.gradle.kts").writeText(
//            """
//            plugins {
//                id("it.nicolasfarabegoli.conventional-commits")
//            }
//            """.trimIndent()
//        )
//    }
//    fun setupCustomTest() {
//        projectDir.mkdirs()
//        File(projectDir.absolutePath, ".git/hooks").mkdirs()
//        projectDir.resolve("settings.gradle.kts").writeText("")
//        projectDir.resolve("build.gradle.kts").writeText(
//            """
//            plugins {
//                id("it.nicolasfarabegoli.conventional-commits")
//            }
//
//            conventionalCommits {
//                from {
//                    "echo \"Hello World\""
//                }
//                setupScript()
//            }
//            """.trimIndent()
//        )
//    }
//
//    "The plugin" `when` {
//        "applied without configurations" should {
//            "generate the default script in `.git/hooks/commit-msg`" {
//                setupDefaultTest()
//                val tasks = GradleRunner.create()
//                    .forwardOutput()
//                    .withPluginClasspath()
//                    .withProjectDir(projectDir)
//                    .withArguments("tasks")
//                    .build()
//                tasks.task(":tasks")?.outcome shouldBe TaskOutcome.SUCCESS
//
//                val scriptFile = File(projectDir.absolutePath, "/.git/hooks/commit-msg")
//                scriptFile.exists() shouldBe true
//                scriptFile.readText() shouldBe Thread.currentThread()
//                    .contextClassLoader
//                    .getResource("it/nicolasfarabegoli/gradle/commit-msg.sh")
//                    .readText()
//            }
//        }
//        "applied with a configuration with `from` rule" should {
//            "generate the script with the content given by the user" {
//                setupCustomTest()
//                val tasks = GradleRunner.create()
//                    .forwardOutput()
//                    .withPluginClasspath()
//                    .withProjectDir(projectDir)
//                    .withArguments("tasks")
//                    .build()
//                tasks.task(":tasks")?.outcome shouldBe TaskOutcome.SUCCESS
//
//                val scriptFile = File(projectDir.absolutePath, "/.git/hooks/commit-msg")
//                scriptFile.exists() shouldBe true
//                scriptFile.readText() shouldBe "#!/usr/bin/env bash\n\necho \"Hello World\""
//            }
//        }
//    }
})

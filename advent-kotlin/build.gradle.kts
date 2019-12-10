import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
}
group = "com.tastapod"
version = "1.0-SNAPSHOT"
dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}
repositories {
    mavenCentral()
}
tasks {
    test {
        useJUnitPlatform()

        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent == null) { // root suite
                    logger.info("----")
                    logger.info("Test result: ${result.resultType}")
                    logger.info("Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeeded, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped")

                }
            }
        })
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
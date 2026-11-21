import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "failed", "skipped")
        exceptionFormat = TestExceptionFormat.FULL
    }
}

// Задача 1: запускает все тесты проекта
tasks.register<Test>("runAllTests") {
    group = "verification"
    description = "Запускает все тесты проекта"

    useJUnitPlatform()

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    testLogging {
        events("passed", "failed", "skipped")
        exceptionFormat = TestExceptionFormat.FULL
    }
}

// Задача 2: выполняется после завершения runAllTests
tasks.register("afterTests") {
    group = "verification"
    description = "Печатает сообщение после завершения прогона тестов"

    doLast {
        println("Test run is over")
    }
}

tasks.named("afterTests") {
    mustRunAfter(tasks.named("runAllTests"))
}

tasks.named("runAllTests") {
    finalizedBy(tasks.named("afterTests"))
}
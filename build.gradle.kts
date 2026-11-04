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
}

tasks.test {
    useJUnitPlatform()
}

// Задача 1: запускает все тесты в проекте
tasks.register<Test>("runAllTests") {
    group = "verification"
    description = "Запускает все тесты проекта"
    useJUnitPlatform()
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
}

// Задача 2: запускается после завершения первой и пишет в консоль сообщение
tasks.register("afterTests") {
    group = "verification"
    description = "Печатает сообщение после прогона тестов"
    doLast {
        println("Test run is over")
    }
}

tasks.named("afterTests") {
    dependsOn("runAllTests")
}

// Ручной чекер: TEST PASSED / TEST FAILED
tasks.register<JavaExec>("manualTests") {
    group = "verification"
    description = "Запускает ручную проверку методов"
    mainClass.set("org.example.MethodChecker")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<Test>("runAutotests") {
    group = "verification"
    description = "Запуск автотестов по тегу autotests"
    useJUnitPlatform {
        includeTags("autotests")
    }
    testLogging {
        events("passed", "failed", "skipped")
        exceptionFormat = TestExceptionFormat.FULL
    }
}




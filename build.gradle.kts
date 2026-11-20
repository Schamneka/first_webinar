import org.gradle.api.tasks.testing.logging.TestExceptionFormat


plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allure {
    version.set("2.29.0")          // версия allure-java (зависимостей и генератора)
    adapter {
        aspectjWeaver.set(true)    // вплетает аспекты @Step
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    implementation("org.assertj:assertj-core:3.27.7")
    implementation("io.rest-assured:rest-assured:5.5.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.codeborne:selenide:6.19.1")

    implementation("io.qameta.allure:allure-selenide:2.24.0")
    implementation("io.qameta.allure:allure-rest-assured:2.24.0")
    testImplementation("io.qameta.allure:allure-junit5:2.24.0")
}

tasks.test {
    useJUnitPlatform()
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

tasks.register<Test>("runSeleniumTests") {
    group = "verification"
    description = "Запускает только Selenium-тесты (тег selenium)"
    useJUnitPlatform {
        includeTags("selenium")
    }
    dependsOn(tasks.named("testClasses"))
}

tasks.register<Test>("runSelenideTests") {
    group = "verification"
    description = "Запускает только Selenide-тесты (тег selenide)"
    useJUnitPlatform {
        includeTags("selenide")
    }
    dependsOn(tasks.named("testClasses"))
}


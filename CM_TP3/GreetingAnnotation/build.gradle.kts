plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.kotlin.kapt") version "2.4.0" apply false
}

group = "org.cmtp1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(19)
}

tasks.test {
    useJUnitPlatform()
}
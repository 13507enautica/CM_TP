plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.kotlin.kapt")
}

group = "org.cmtp1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    // Include the annotations module
    implementation(project(":annotations"))
    // Use the annotation processor
    kapt(project(":processor"))
}

kotlin {
    jvmToolchain(19)
}

tasks.test {
    useJUnitPlatform()
}
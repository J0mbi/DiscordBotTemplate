import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "dev.jombi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    implementation("net.dv8tion:JDA:5.0.0-alpha.22")
    implementation("com.github.minndevelopment:jda-ktx:0.9.6-alpha.22")
    implementation("org.jetbrains:annotations:23.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
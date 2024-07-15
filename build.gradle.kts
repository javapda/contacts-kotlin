plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.javapda"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val junitVersion = "5.9.3"
dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
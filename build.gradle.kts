import org.gradle.api.JavaVersion

plugins {
    id("java")
}

group = "org.easybird"
version = "1.0.7"

java.sourceCompatibility = JavaVersion.VERSION_23
java.targetCompatibility = JavaVersion.VERSION_23

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.mockito:mockito-junit-jupiter:3.11.2")
}

tasks.test {
    useJUnitPlatform()
}
import org.gradle.api.JavaVersion

plugins {
    id("java")
    id("maven-publish")
}

group = "org.easybird"
version = "1.0.1"

java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.mockito:mockito-junit-jupiter:3.11.2")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            this.groupId = project.group.toString()
            this.artifactId = project.name
            this.version = project.version.toString()
        }
    }

    repositories {
        maven {
            name = "nexus"
            val isSnapshot = project.version.toString().endsWith("-SNAPSHOT")
            url = if (isSnapshot)
                uri("https://nexus.xo-6.studio/repository/maven-snapshots/")
            else
                uri("https://nexus.xo-6.studio/repository/maven-releases/")
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }
}
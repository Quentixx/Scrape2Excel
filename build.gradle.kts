plugins {
    id("java")
}

group = "fr.quentixx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.19.0")
    implementation("com.github.crab2died:Excel4J:3.0.0")
    implementation("org.jsoup:jsoup:1.13.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
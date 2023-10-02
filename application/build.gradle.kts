plugins {
    id("java")
}

group = "com.felipe.admin.catalogo.application"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.mockito:mockito-junit-jupiter:5.4.0")
}

tasks.test {
    useJUnitPlatform()
}
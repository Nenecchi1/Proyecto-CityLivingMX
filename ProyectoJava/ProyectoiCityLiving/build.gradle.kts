plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.beryx.jlink") version "2.25.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"
val mockitoVersion = "5.11.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(26)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("org.example.proyectoicityliving")
    mainClass.set("org.example.proyectoicityliving.Main")

    // 1. Argumentos JVM por defecto al empaquetar o lanzar la aplicación
    applicationDefaultJvmArgs = listOf(
        "--enable-native-access=javafx.graphics",
        "--add-opens=java.base/sun.misc=ALL-UNNAMED"
    )
}

javafx {
    version = "22.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("net.synedra:validatorfx:0.6.1") {
        exclude(group = "org.openjfx")
    }
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")

    // 2. Dependencias para Pruebas Unitarias (JUnit 5 + Mockito)
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    testImplementation("org.mockito:mockito-core:${mockitoVersion}")
    testImplementation("org.mockito:mockito-junit-jupiter:${mockitoVersion}")
}

// 3. Silenciar advertencias al ejecutar la tarea 'gradle run'
tasks.withType<JavaExec> {
    jvmArgs(
        "--enable-native-access=javafx.graphics",
        "--add-opens=java.base/sun.misc=ALL-UNNAMED"
    )
}

// 4. Silenciar advertencias durante la ejecución de pruebas unitarias
tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(
        "--enable-native-access=javafx.graphics",
        "--add-opens=java.base/sun.misc=ALL-UNNAMED"
    )
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "Main"
    }
}
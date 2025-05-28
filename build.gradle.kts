plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.0.0-beta13"
}

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {

    archiveClassifier.set("")

    doLast {
        println("> Task :copy to path")
        copy {
            from(archiveFile.get().asFile)
            into("C:\\Users\\Lexi\\Documents\\Reactor\\plugins\\")
        }
    }
}
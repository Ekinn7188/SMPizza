import java.net.URI

plugins {
    `java-library`
    java

    id("com.github.johnrengelman.shadow") version "7.1.2" // fat jar
    id("io.papermc.paperweight.userdev") version "1.5.11" // paper
    id("xyz.jpenilla.run-paper") version "1.0.6" // server
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven {
        name = "sonatype-oss-snapshots"
        url = URI("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
    implementation("jeeper.utils:PaperPluginUtils:1.4")
    implementation("net.dv8tion:JDA:5.0.0-alpha.21")
}


tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.20.1")
    }

    shadowJar {
        archiveFileName.set("${project.name}.jar")
    }
}

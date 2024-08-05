plugins {
    kotlin("jvm") version "1.8.21"
    id("net.minecraftforge.gradle") version "6.0.19"
    id("maven-publish")
}

version = "1.0.0"
group = "com.example"

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven("https://maven.kosmx.dev/") {
        name = "KosmX"
    }
    maven("https://api.modrinth.com/maven") {
        name = "Modrinth"
        content {
            includeGroup("com.replaymod")
        }
    }
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.neoforged.net")
    maven("https://jitpack.io")
    maven("https://cursemaven.com") {
        name = "CurseForge"
    }
}

dependencies {
    minecraft("net.minecraftforge:forge:1.20.1-47.2.19")
    implementation("io.github.kosmx.emotes:emotesMain:2.2.7-b.build.50")

    // CurseForge dependency for ReforgedPlayMod
    implementation(fg.deobf("curse.maven:reforgedplay-mod-1018692:5374146"))

    // ReplayMod dependencies from GitHub
    implementation("com.github.ReplayMod:ReplayStudio:d9f7c11")
    implementation("com.github.ReplayMod:lwjgl-utils:27dcd66")

    // SLF4J for logging
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    // Kotlin dependencies
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

base {
    archivesName.set("emotes_replay_compat")
}

kotlin {
    target {
        jvmToolchain(17)
    }
}

minecraft {
    mappings("official", "1.20.1")
    runs {
        create("client") {
            workingDirectory = "run"
            args("--username", "Player")
        }
        create("server") {
            workingDirectory = "run"
            args("--nogui")
        }
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filteringCharset = "UTF-8"
        filesMatching("META-INF/mods.toml") {
            expand(mapOf(
                "mod_id" to "examplemod",
                "version" to project.version,
                "mod_description" to "This mod provides compatibility between Emotes and ReplayMod."
            ))
        }
    }
    java {
        withSourcesJar()
    }
    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName}" }
        }
    }
}

// Configure the Maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        // Add repositories to publish to here.
    }
}
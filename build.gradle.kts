plugins {
    `java-library`
    `maven-publish`
}

group = "com.github.leroyguillaume"
version = "1.5.0"

publishing {
    publications {
        create<MavenPublication> ("keycloak-bcrypt") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "updox"
            url = uri( "https://nexus.updoxnet.com/repository/releases/")
            credentials(PasswordCredentials::class)
        }
    }
}

repositories {
    mavenCentral()
}



dependencies {
    val bcryptVersion = "0.9.0"
    val jbossLoggingVersion = "3.4.1.Final"
    val keycloakVersion = "14.0.0"

    // BCrypt
    implementation("at.favre.lib:bcrypt:$bcryptVersion")

    // JBoss
    compileOnly("org.jboss.logging:jboss-logging:$jbossLoggingVersion")

    // Keycloak
    compileOnly("org.keycloak:keycloak-common:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-core:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi-private:$keycloakVersion")
}

tasks {
    jar {
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
            exclude("META-INF/MANIFEST.MF")
            exclude("META-INF/*.SF")
            exclude("META-INF/*.DSA")
            exclude("META-INF/*.RSA")
        }
    }

    wrapper {
        gradleVersion = "6.8.1"
    }
}

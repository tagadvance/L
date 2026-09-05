plugins {
    `java-library`
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

dependencies {
    // api, not implementation: Logger and Marker are all over L's own signatures, so anyone
    // calling L needs slf4j on their compile classpath anyway.
    api("org.slf4j:slf4j-api:2.0.17")
}

group = "com.tagadvance"
version = "1.1.0"
description = "A super simple library that aims to eliminate logging boilerplate."

java {
    // 1.0.0 is on Maven Central built for 17. Raising this floor is a breaking change for every
    // consumer, so it moves only on a major version.
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
    withJavadocJar()
}

testing {
    suites {
        withType<JvmTestSuite>().configureEach {
            useJUnitJupiter("6.1.3")
        }
    }
}

// The published bytecode is always 17; this only changes the JVM the tests run on, so CI can prove
// the stack walk in L.getClassName still finds the caller on a newer runtime.
//     ./gradlew test -PtestJavaVersion=25
val testJavaVersion = (project.findProperty("testJavaVersion") as String?)?.toInt()
if (testJavaVersion != null) {
    val toolchains = extensions.getByType<JavaToolchainService>()
    tasks.withType<Test>().configureEach {
        javaLauncher = toolchains.launcherFor {
            languageVersion = JavaLanguageVersion.of(testJavaVersion)
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    // Explicit here as well as in the toolchain so the bytecode target is visible in this file.
    options.release = 17
    options.compilerArgs.add("-Xlint:all")
}

tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all,-missing", "-quiet")
}

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

tasks.named<Jar>("jar") {
    manifest {
        attributes("Automatic-Module-Name" to "com.tagadvance.l")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "l"
            from(components["java"])

            pom {
                name.set("L")
                description.set(provider { project.description })
                url.set("https://github.com/tagadvance/L")


                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://raw.githubusercontent.com/tagadvance/L/main/LICENSE")
                    }
                }

                organization {
                    name.set("tagadvance")
                    url.set("https://tagadvance.com")
                }

                developers {
                    developer {
                        id.set("tagadvance")
                        name.set("Tag Spilman")
                        email.set("tagadvance+L@gmail.com")
                        organization.set("tagadvance")
                        organizationUrl.set("https://tagadvance.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com:tagadvance/L.git")
                    developerConnection.set("scm:git:ssh://git@github.com:tagadvance/L.git")
                    url.set("https://github.com/tagadvance/L")
                }
            }
        }
    }

    repositories {
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
            name = "SonatypeSnapshot"
            credentials {
                username = System.getenv("SONATYPE_USER")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
        maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
            name = "SonatypeStaging"
            credentials {
                username = System.getenv("SONATYPE_USER")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

signing {
    val signingKey = System.getenv("GPG_SIGNING_KEY")
    val signingPassword = System.getenv("GPG_SIGNING_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar

plugins {
    `java-library`
    signing
    // Applies Gradle's own maven-publish, then targets the Sonatype Central Portal. `.base` rather
    // than the full `com.vanniktech.maven.publish`, because the full plugin decides what to do from
    // Gradle properties (SONATYPE_HOST, RELEASE_SIGNING_ENABLED, POM_*) and this build says it all
    // in one file instead.
    id("com.vanniktech.maven.publish.base") version "0.37.0"
}

group = "com.tagadvance"
version = "1.1.0"
description = "A super simple library that aims to eliminate logging boilerplate."

repositories {
    mavenCentral()
}

dependencies {
    // api, not implementation: Logger and Marker are all over L's own signatures, so anyone
    // calling L needs slf4j on their compile classpath anyway.
    api("org.slf4j:slf4j-api:2.0.17")
}

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

// What switches signing on, and the only thing that does. Keep it a condition: the plugin's
// signAllPublications() makes signing *required* for any version that is not a -SNAPSHOT, so
// calling it unconditionally would break publishToMavenLocal for anyone with no GPG key - the one
// publishing path that has to work without credentials.
val signingKey = providers.environmentVariable("GPG_SIGNING_KEY").orNull

// To release:
//
//   1. export a Central Portal token - not the old OSSRH login - under the names the plugin reads:
//        export ORG_GRADLE_PROJECT_mavenCentralUsername="$SONATYPE_USER"
//        export ORG_GRADLE_PROJECT_mavenCentralPassword="$SONATYPE_PASSWORD"
//      The plugin takes these as Gradle properties, and `ORG_GRADLE_PROJECT_` is how an environment
//      variable becomes one. SONATYPE_USER / SONATYPE_PASSWORD alone are read by nothing now.
//   2. export GPG_SIGNING_KEY, and GPG_SIGNING_PASSWORD if the key has one.
//
// Then `./gradlew publishToMavenCentral` and release the deployment by hand from the portal.
mavenPublishing {
    // s01.oss.sonatype.org is gone. 0.37.0 speaks to the Central Portal and nothing else: a
    // -SNAPSHOT goes to https://central.sonatype.com/repository/maven-snapshots/, and a release is
    // staged under build/publishing/mavenCentral then uploaded as one bundle at the end of the
    // build. No argument means no automatic release - the deployment waits in the portal.
    publishToMavenCentral()

    // None()/None() because `java { withSourcesJar(); withJavadocJar() }` above already produces
    // both jars, and - the part that matters - the sourcesElements and javadocElements *variants*
    // the published .module carries. Letting the plugin add its own would be a second artifact
    // under the same classifier.
    configure(JavaLibrary(javadocJar = JavadocJar.None(), sourcesJar = SourcesJar.None()))

    if (!signingKey.isNullOrBlank()) {
        signAllPublications()
    }

    coordinates(artifactId = "l")

    pom {
        name.set("L")
        description.set(provider { project.description })
        url.set("https://github.com/tagadvance/L")
        inceptionYear.set("2022")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://raw.githubusercontent.com/tagadvance/L/main/LICENSE")
            }
        }

        organization {
            name.set("tagadvance")
            url.set("https://github.com/tagadvance")
        }

        developers {
            developer {
                id.set("tagadvance")
                name.set("Tag Spilman")
                email.set("tagadvance+L@gmail.com")
                organization.set("tagadvance")
                organizationUrl.set("https://github.com/tagadvance")
            }
        }

        scm {
            connection.set("scm:git:git://github.com:tagadvance/L.git")
            developerConnection.set("scm:git:ssh://git@github.com:tagadvance/L.git")
            url.set("https://github.com/tagadvance/L")
        }

        issueManagement {
            system.set("GitHub Issues")
            url.set("https://github.com/tagadvance/L/issues")
        }
    }
}

// The plugin's own in-memory key comes from a `signingInMemoryKey` Gradle property. The key here has
// always been GPG_SIGNING_KEY, an environment variable, so it is handed to Gradle's signing
// extension directly and the plugin only has to know that publications are signed.
if (!signingKey.isNullOrBlank()) {
    signing {
        useInMemoryPgpKeys(signingKey, providers.environmentVariable("GPG_SIGNING_PASSWORD").orNull)
    }
}

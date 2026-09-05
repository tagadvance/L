plugins {
    // Lets the toolchain below provision a JDK rather than failing on a machine - or a CI runner -
    // that happens to have a different one installed.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "L"
include("l")

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    testImplementation(libs.junit)
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.library.coreUtil)
}
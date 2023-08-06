plugins {
    kotlin("jvm")
    kotlin("kapt")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":annotations"))
//    implementation("com.google.devtools.ksp:symbol-processing:1.8.20-1.0.11")
//    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.20-1.0.11")
//    implementation("com.squareup:kotlinpoet:1.14.2")
//    implementation("com.squareup:kotlinpoet-ksp:1.14.2")

    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.20")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
    kapt("com.google.auto.service:auto-service:1.0.1")
}
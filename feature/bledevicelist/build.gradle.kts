plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kover)
    alias(libs.plugins.mokkery)
}

kotlin {
    android {
        namespace = "com.anchor.ugzeroharm.bledevicelist"
    }
    jvm("desktop")

    val xcfName = "bledevicelist"

    iosArm64 { binaries.framework { baseName = xcfName } }
    iosSimulatorArm64 { binaries.framework { baseName = xcfName } }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.foundation)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.util)
                implementation(libs.compose.material.icons.extended)
                implementation(libs.compose.material3)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.ui.tooling.preview)

                implementation(libs.kotlinCollection)
                implementation(libs.kotlinxSerialization)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                api(libs.koin.core)
                implementation(libs.kable)
                implementation(libs.jetbrains.compose.navigation)

                implementation(project(":core:designsystem"))
                implementation(project(":core:common"))
                implementation(project(":core:permission"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.activityCompose)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}

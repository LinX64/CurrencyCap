import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.realm.plugin)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    subprojects {
        tasks.withType(KotlinCompile::class.java).configureEach {
            compilerOptions {
                if (project.findProperty("enableMultiModuleComposeReports") == "true") {
                    freeCompilerArgs.add("-P")
                    freeCompilerArgs.add("plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${rootProject.layout.buildDirectory}/compose_metrics/")
                    freeCompilerArgs.add("-P")
                    freeCompilerArgs.add("plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${rootProject.layout.buildDirectory}/compose_metrics/")
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.ui)
            api(libs.androidx.startup)

            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(compose.preview)

            compileOnly(libs.mongodb.realm)

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
            implementation(libs.firebase.fireStore)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)

            implementation(libs.navigation.compose)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.startup)
            implementation(libs.urlencoder.lib)
            implementation(libs.mongodb.realm)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.fireStore)
            implementation(libs.firebase.crashlytics)

            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.datetime)

            // UI
            implementation(libs.haze)
            implementation(libs.compottie)
            implementation(libs.placeholder.material3)
            implementation(libs.coil)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            api(libs.compose.webview.multiplatform)

            // Data
            implementation(libs.bundles.datastore)
            implementation(libs.bundles.ktor)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kachetor)
            api(libs.koin.core)
            implementation(libs.koin.compose)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.darwin)
            implementation(libs.koin.core)

            implementation(libs.bundles.datastore)
        }
    }
}

android {
    namespace = "com.client.currencycap"
    compileSdk = 34

    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
        jniLibs.srcDirs("src/main/jniLibs")
    }

    defaultConfig {
        applicationId = "com.client.currencycap"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.1.2"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}


import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("maven-publish")
    id("com.android.library") version "3.6.4"
}
repositories {
    mavenCentral()
    jcenter()
    google()
}
group = "com.example"
version = "0.0.1"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    ios {
        binaries {
            framework()
        }
    }

    /**
     * Other Apple Options– un-comment each to enable
     *
     * each target will need its own `binaries { framework() }` configuration
     */
    //watchos()
    //iosArm32()
    //tvos()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("androidx.sqlite:sqlite:2.1.0")
                implementation("io.requery:sqlite-android:3.30.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("androidx.test:core:1.2.0")
                implementation("org.robolectric:robolectric:4.3.1")
            }
        }
    }
}

tasks.register("debugFatFramework", FatFrameworkTask::class) {
    baseName = project.name

    // Framework is output here
    destinationDir = buildDir.resolve("fat-framework/debug")

    val targets = mutableListOf(
        kotlin.iosX64(),
        kotlin.iosArm64()
    )

    /**
     * Un-comment/modify to include additional targets
     *
     * targets.add(kotlin.iosArm32())
     */

    // Specify the frameworks to be merged.
    from(targets.map { it.binaries.getFramework(NativeBuildType.DEBUG) })
}

// Enough settings to gradle sync, but more can be added
android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

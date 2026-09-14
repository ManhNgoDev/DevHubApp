plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// Helper function to load environment variables from .env.example, .env, and System environment
fun loadEnvVariables(): Map<String, String> {
    val envMap = mutableMapOf<String, String>()
    val filesToRead = listOf(
        rootProject.file(".env.example"),
        rootProject.file(".env")
    )

    for (file in filesToRead) {
        if (file.exists() && file.isFile) {
            file.forEachLine { rawLine ->
                val line = rawLine.trim()
                if (line.isNotEmpty() && !line.startsWith("#") && line.contains("=")) {
                    val parts = line.split("=", limit = 2)
                    val key = parts[0].trim()
                    var value = parts[1].trim()
                    if ((value.startsWith("\"") && value.endsWith("\"")) ||
                        (value.startsWith("'") && value.endsWith("'"))
                    ) {
                        value = value.substring(1, value.length - 1)
                    }
                    envMap[key] = value
                }
            }
        }
    }

    // Allow system environment variables to override for CI/CD environments
    for (key in envMap.keys.toList()) {
        val sysVal = System.getenv(key)
        if (!sysVal.isNullOrBlank()) {
            envMap[key] = sysVal
        }
    }

    // Alias common typo GITHUB_CLIENT_SECRECT -> GITHUB_CLIENT_SECRET
    if (envMap.containsKey("GITHUB_CLIENT_SECRECT")) {
        val typoSecret = envMap["GITHUB_CLIENT_SECRECT"].orEmpty()
        if (typoSecret.isNotEmpty() && typoSecret != "your_github_client_secret_here") {
            envMap["GITHUB_CLIENT_SECRET"] = typoSecret
        }
    }

    return envMap
}

val envVars = loadEnvVariables()

android {
    namespace = "com.manhngo.devhubapp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.manhngo.devhubapp"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Inject all .env variables into BuildConfig and ManifestPlaceholders
        envVars.forEach { (key, value) ->
            val sanitizedKey = key.replace("[^A-Za-z0-9_]".toRegex(), "_")
            val escapedValue = value.replace("\\", "\\\\").replace("\"", "\\\"")
            buildConfigField("String", sanitizedKey, "\"$escapedValue\"")
            manifestPlaceholders[sanitizedKey] = value
        }
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
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
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    // Retrofit & OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Security Encrypted Storage
    implementation(libs.androidx.security.crypto)

    // Coil Image Loading
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
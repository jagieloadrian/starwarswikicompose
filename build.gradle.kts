plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.apollo.graphql) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.jacoco)
}

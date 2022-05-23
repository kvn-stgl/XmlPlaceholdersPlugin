import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.pluginPublish) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.versionCheck)
}

allprojects {
    group = PluginCoordinates.GROUP
    version = PluginCoordinates.VERSION

    repositories {
        google()
        mavenCentral()
    }

    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        ignoreFailures = false
        parallel = false
        buildUponDefaultConfig = true
        debug = false
        ignoredBuildTypes = listOf("release")
        autoCorrect = true
        config = rootProject.files("../config/detekt/detekt.yml")
    }

    dependencies {
        detektPlugins(project.rootProject.libs.detekt.formatting)
    }
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        html.outputLocation.set(file("build/reports/detekt.html"))
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

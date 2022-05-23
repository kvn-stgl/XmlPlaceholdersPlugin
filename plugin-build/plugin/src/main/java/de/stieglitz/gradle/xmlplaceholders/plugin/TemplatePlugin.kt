package de.stieglitz.gradle.xmlplaceholders.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

const val EXTENSION_NAME = "xmlPlaceholdersConfig"
const val TASK_NAME = "xmlPlaceholders"

abstract class TemplatePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, TemplateExtension::class.java, project)

        project.tasks.register(TASK_NAME, TemplateExampleTask::class.java) {
            it.tag.set(extension.tag)
            it.message.set(extension.message)
            it.outputFile.set(extension.outputFile)
        }
    }
}

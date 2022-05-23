package de.stieglitz.gradle.xmlplaceholders.plugin

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File

class TemplatePluginTest {

    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("de.stieglitz.gradle.xmlplaceholders.plugin")

        assert(project.tasks.getByName("xmlPlaceholders") is TemplateExampleTask)
    }

    @Test
    fun `extension templateExampleConfig is created correctly`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("de.stieglitz.gradle.xmlplaceholders.plugin")

        assertNotNull(project.extensions.getByName("xmlPlaceholdersConfig"))
    }

    @Test
    fun `parameters are passed correctly from extension to task`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("de.stieglitz.gradle.xmlplaceholders.plugin")
        val aFile = File(project.projectDir, ".tmp")
        (project.extensions.getByName("xmlPlaceholdersConfig") as TemplateExtension).apply {
            tag.set("a-sample-tag")
            message.set("just-a-message")
            outputFile.set(aFile)
        }

        val task = project.tasks.getByName("xmlPlaceholders") as TemplateExampleTask

        assertEquals("a-sample-tag", task.tag.get())
        assertEquals("just-a-message", task.message.get())
        assertEquals(aFile, task.outputFile.get().asFile)
    }
}

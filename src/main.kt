package sandbox

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*

import sandbox.ui.Window
import sandbox.models.Rectangle

fun main(args: Array<String>) {
    val window = Window(
        width  = 800,
        height = 600
    )

    val rectangle = Rectangle()
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

    while (!glfwWindowShouldClose(window.handle)) {
        glfwPollEvents()

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        rectangle.draw()

        glfwSwapBuffers(window.handle)
    }

    glfwTerminate()
}

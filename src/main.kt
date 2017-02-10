package sandbox

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*

fun main(args: Array<String>) {
    val window = Window()

    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    while (!glfwWindowShouldClose(window.handle)) {
        glfwPollEvents()

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        glfwSwapBuffers(window.handle)
    }

    glfwTerminate()
}

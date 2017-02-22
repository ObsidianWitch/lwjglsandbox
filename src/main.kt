package sandbox

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*

import sandbox.ui.Window
import sandbox.nodes.Rectangle
import sandbox.nodes.Camera

fun main(args: Array<String>) {
    val window = Window(
        width  = 800,
        height = 600
    )

    val rectangle = Rectangle()

    val camera = Camera(
        target = rectangle,
        aspect = window.width.toFloat() / window.height
    )

    glClearColor(0.2f, 0.2f, 0.2f, 1.0f)

    window.loop() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        camera.update()
        rectangle.update()
    }

    glfwTerminate()
}

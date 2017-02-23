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

    window.loop() {
        camera.update()
        rectangle.update()
    }

    glfwTerminate()
}

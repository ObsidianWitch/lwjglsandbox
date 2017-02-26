package sandbox

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*

import sandbox.ui.Window
import sandbox.nodes.Cube
import sandbox.nodes.Camera

fun main(args: Array<String>) {
    val window = Window(
        width  = 800,
        height = 600
    )

    val cube = Cube()
    cube.model.rotateX(Math.toRadians(45.0).toFloat())

    val camera = Camera(
        target = cube,
        aspect = window.width.toFloat() / window.height
    )

    window.loop() {
        camera.update()

        cube.tmpModel.apply {
            identity()
            rotateY(glfwGetTime().toFloat())
        }
        cube.update()
    }

    glfwTerminate()
}

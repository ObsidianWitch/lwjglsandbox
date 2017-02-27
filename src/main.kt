package sandbox

import org.lwjgl.glfw.GLFW.*

import sandbox.ui.Window
import sandbox.scenes.Scene

fun main(args: Array<String>) {
    val window = Window(
        width  = 800,
        height = 600
    )

    val scene = Scene(window)

    window.loop() { scene.update() }
}

package sandbox.scenes

import org.lwjgl.glfw.GLFW.*

import sandbox.ui.Window
import sandbox.nodes.Camera
import sandbox.nodes.Ground
import sandbox.nodes.Cube

class Scene {
    private val window: Window

    private val camera: Camera

    private val ground: Ground
    private val cube: Cube

    constructor(window: Window) {
        this.window = window

        this.ground = Ground()
        this.cube = Cube().apply {
            model.translate(0.0f, 5.0f, 0.0f)
                 .rotateX(Math.toRadians(45.0).toFloat())
        }

        this.camera = Camera(
            target = cube,
            aspect = window.width.toFloat() / window.height
        )
    }

    fun update() {
        camera.update()

        ground.update()

        cube.tmpLocal.apply {
            identity()
            rotateY(glfwGetTime().toFloat())
        }
        cube.update()
    }
}

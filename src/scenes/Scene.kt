package sandbox.scenes

import org.lwjgl.glfw.GLFW.*

import sandbox.ui.Window
import sandbox.nodes.Camera
import sandbox.nodes.Ground
import sandbox.nodes.Player

class Scene {
    private val window: Window

    private val camera: Camera

    private val ground: Ground
    private val player: Player

    constructor(window: Window) {
        this.window = window

        this.ground = Ground()
        this.player = Player().apply {
            model.rotateY(Math.toRadians(180.0).toFloat())
        }

        this.camera = Camera(
            target = player,
            aspect = window.width.toFloat() / window.height
        )

        window.callbacks.scrollCallbacks.add(camera.scrollCallback)
    }

    fun update() {
        camera.update()

        ground.update()

        player.update()
    }
}

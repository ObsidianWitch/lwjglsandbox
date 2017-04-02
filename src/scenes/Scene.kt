package sandbox.scenes

import org.lwjgl.glfw.GLFW.*
import org.joml.Vector3f

import sandbox.ui.Window
import sandbox.nodes.Camera
import sandbox.nodes.CameraCallbacks
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
            target       = player,
            targetOffset = Vector3f(0.0f, 1.5f, 0.0f),
            position     = Vector3f(0.0f, 1.5f, -2.0f),
            aspect       = window.width.toFloat() / window.height
        )

        val cameraCallbacks = CameraCallbacks(camera)

        window.callbacks.apply {
            scrollCallbacks.add(cameraCallbacks.scrollCallback)
            mouseButtonCallbacks.add(cameraCallbacks.mouseButtonCallback)
            cursorPositionCallbacks.add(cameraCallbacks.cursorPositionCallback)
        }
    }

    fun update() {
        camera.update()

        ground.update()

        player.update()
    }
}

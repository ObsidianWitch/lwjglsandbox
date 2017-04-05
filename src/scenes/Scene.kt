package sandbox.scenes

import org.lwjgl.glfw.GLFW.*
import org.joml.Vector3f

import sandbox.ui.Window
import sandbox.nodes.Camera
import sandbox.nodes.CameraCallbacks
import sandbox.nodes.Ground
import sandbox.nodes.Player
import sandbox.nodes.PlayerCallbacks

class Scene {
    private val window: Window

    private val camera: Camera

    private val ground: Ground
    private val player: Player

    constructor(window: Window) {
        this.window = window

        this.ground = Ground()

        this.player = Player(
            speed = 0.08f
        ).apply {
            model.rotateY(Math.toRadians(180.0).toFloat())
        }
        val playerCallbacks = PlayerCallbacks(player)

        this.camera = Camera(
            target       = player,
            targetOffset = Vector3f(0.0f, 1.5f, 0.0f),
            position     = Vector3f(0.0f, 1.5f, -2.0f),
            aspect       = window.width.toFloat() / window.height,
            zoomRange    = 1.0f..5.0f,
            pitchRange   = -15.0f..30.0f
        )
        val cameraCallbacks = CameraCallbacks(camera)

        window.callbacks.apply {
            keyCallbacks.add(playerCallbacks.keyCallback)
            mouseButtonCallbacks.add(playerCallbacks.mouseButtonCallback)

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

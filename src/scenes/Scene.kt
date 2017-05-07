package sandbox.scenes

import org.lwjgl.glfw.GLFW.*
import org.joml.*

import sandbox.ui.Window
import sandbox.nodes.*

class Scene {
    private val window: Window

    private val camera: Camera

    private val ground: Ground
    private val player: Player

    private val ambLight: AmbientLight
    private val dirLight: DirectionalLight
    private val ptLight: PointLight

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

        this.ambLight = AmbientLight(
            name  = "aL",
            color = Vector4f(0.1f, 0.0f, 0.0f, 1.0f)
        )

        this.dirLight = DirectionalLight(
            name      = "dL",
            color     = Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
            direction = Vector3f(-1.0f, -1.0f, -1.0f)
        )

        this.ptLight  = PointLight(
            name      = "pL",
            color     = Vector4f(0.9f, 0.6f, 0.2f, 1.0f),
            position  = Vector3f(10.0f, 10.0f, 10.0f),
            constant  = 0.1f,
            linear    = 0.1f,
            quadratic = 0.01f,
            frequency = 2.0f
        )
    }

    fun update() {
        camera.update()

        ambLight.update()
        dirLight.update()
        ptLight.update()

        ground.update()

        player.update()
    }
}

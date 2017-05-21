package sandbox.nodes

import org.joml.Vector2d
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.BufferUtils

import sandbox.ui.MouseButtonCallback
import sandbox.ui.CursorPositionCallback
import sandbox.ui.ScrollCallback

class CameraCallbacks {
    companion object {
        private val MOUSE_SENSITIVITY = 0.25
    }

    private val camera: Camera
    private var isMoving: Boolean
    private var mousePosition: Vector2d

    constructor(camera: Camera) {
        this.camera = camera
        this.isMoving = false
        this.mousePosition = Vector2d()
    }

    val mouseButtonCallback: MouseButtonCallback get() = {
        window, _, action, _ ->
        if (action == GLFW_PRESS) {
            isMoving = true

            val xpos = BufferUtils.createDoubleBuffer(1);
            val ypos = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, xpos, ypos)
            mousePosition = Vector2d(xpos.get(), ypos.get())

            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED)
        } else {
            isMoving = false

            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL)
        }
    }

    val cursorPositionCallback: CursorPositionCallback get() = a@ {
        _, xpos, ypos ->
        if (!isMoving) { return@a }

        val oldMousePosition = mousePosition
        mousePosition = Vector2d(xpos, ypos)

        val delta = Vector2d(
            (oldMousePosition.x - mousePosition.x) * MOUSE_SENSITIVITY,
            (mousePosition.y - oldMousePosition.y) * MOUSE_SENSITIVITY
        )

        camera.yaw(delta.x)
        camera.pitch(delta.y)
    }

    val scrollCallback: ScrollCallback get() = {
        _, _, yoffset -> camera.zoom(-yoffset.toFloat())
    }
}

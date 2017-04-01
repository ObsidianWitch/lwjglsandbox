package sandbox.nodes

import org.lwjgl.glfw.GLFW.*

import sandbox.ui.MouseButtonCallback
import sandbox.ui.cursorPositionCallback
import sandbox.ui.ScrollCallback

class CameraCallbacks {
    val camera: Camera

    constructor(camera: Camera) {
        this.camera = camera
    }

    val scrollCallback: ScrollCallback get() = {
        _, _, yoffset -> camera.zoom(yoffset.toFloat())
    }
}

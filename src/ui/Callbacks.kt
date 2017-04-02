package sandbox.ui

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import org.lwjgl.opengl.GL11.*

typealias KeyCallback = (
    window: Long, key: Int, scancode: Int, action: Int, mods: Int
) -> Unit

typealias MouseButtonCallback = (
    window: Long, button: Int, action: Int, mods: Int
) -> Unit

typealias CursorPositionCallback = (
    window: Long, xpos: Double, ypos: Double
) -> Unit

typealias ScrollCallback = (
    window: Long, xoffset: Double, yoffset: Double
) -> Unit

class Callbacks {
    val keyCallbacks: MutableList<KeyCallback>
    val mouseButtonCallbacks: MutableList<MouseButtonCallback>
    val cursorPositionCallbacks: MutableList<CursorPositionCallback>
    val scrollCallbacks: MutableList<ScrollCallback>

    private val handle: Long

    constructor(handle: Long) {
        this.handle = handle
        this.keyCallbacks = mutableListOf()
        this.mouseButtonCallbacks = mutableListOf()
        this.cursorPositionCallbacks = mutableListOf()
        this.scrollCallbacks = mutableListOf()

        glfwSetKeyCallback(handle, keyHandler)
        glfwSetMouseButtonCallback(handle, mouseButtonHandler)
        glfwSetCursorPosCallback(handle, cursorPositionHandler)
        glfwSetScrollCallback(handle, scrollHandler)
    }

    private val keyHandler get() = object : GLFWKeyCallback() {
        override fun invoke(
            window: Long, key: Int, scancode: Int, action: Int, mods: Int
        ) {
            keyCallbacks.forEach { it(window, key, scancode, action, mods) }
        }
    }

    private val mouseButtonHandler get() = object : GLFWMouseButtonCallback() {
        override fun invoke(
            window: Long, button: Int, action: Int, mods: Int
        ) {
            mouseButtonCallbacks.forEach { it(window, button, action, mods) }
        }
    }

    private val cursorPositionHandler get() = object : GLFWCursorPosCallback() {
        override fun invoke(
            window: Long, xpos: Double, ypos: Double
        ) {
            cursorPositionCallbacks.forEach { it(window, xpos, ypos) }
        }
    }

    private val scrollHandler get() = object : GLFWScrollCallback() {
        override fun invoke(window: Long, xoffset: Double, yoffset: Double) {
            scrollCallbacks.forEach { it(window, xoffset, yoffset) }
        }
    }
}

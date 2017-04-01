package sandbox.ui

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import org.lwjgl.opengl.GL11.*

typealias KeyCallback = (
    window: Long, key: Int, scancode: Int, action: Int, mods: Int
) -> Unit

typealias ScrollCallback = (
    window: Long, xoffset: Double, yoffset: Double
) -> Unit

class Callbacks {
    val keyCallbacks: MutableList<KeyCallback>
    val scrollCallbacks: MutableList<ScrollCallback>

    private val handle: Long

    constructor(handle: Long) {
        this.handle = handle
        this.keyCallbacks = mutableListOf()
        this.scrollCallbacks = mutableListOf()

        glfwSetKeyCallback(handle, keyHandler)
        glfwSetScrollCallback(handle, scrollHandler)
    }

    private val keyHandler get() = object : GLFWKeyCallback() {
        override fun invoke(
            window: Long, key: Int, scancode: Int, action: Int, mods: Int
        ) {
            keyCallbacks.forEach { it(window, key, scancode, action, mods) }
        }
    }

    private val scrollHandler get() = object : GLFWScrollCallback() {
        override fun invoke(window: Long, xoffset: Double, yoffset: Double) {
            scrollCallbacks.forEach { it(window, xoffset, yoffset) }
        }
    }
}

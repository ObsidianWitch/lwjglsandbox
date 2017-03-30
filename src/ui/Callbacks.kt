package sandbox.ui

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import org.lwjgl.opengl.GL11.*

typealias KeyCallback = (
    window: Long, key: Int, scancode: Int, action: Int, mods: Int
) -> Unit

class Callbacks {
    val keyCallbacks: MutableList<KeyCallback>

    private val handle: Long

    constructor(handle: Long) {
        this.handle = handle
        this.keyCallbacks = mutableListOf()

        glfwSetKeyCallback(handle, keyHandler)
    }

    private val keyHandler get() = object : GLFWKeyCallback() {
        override fun invoke(
            window: Long, key: Int, scancode: Int, action: Int, mods: Int
        ) {
            keyCallbacks.forEach { it(window, key, scancode, action, mods) }
        }
    }
}

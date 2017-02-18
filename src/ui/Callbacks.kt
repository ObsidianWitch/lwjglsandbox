package sandbox.ui

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import org.lwjgl.opengl.GL11.*

class Callbacks {
    val handle: Long

    constructor(handle: Long) {
        this.handle = handle
    }

    fun addKeyCallback(
        callback: (
            window: Long, key: Int, scancode: Int, action: Int, mods: Int
        ) -> Unit
    ) {
        val keyCallback = object : GLFWKeyCallback() {
            override fun invoke(
                window: Long, key: Int, scancode: Int, action: Int, mods: Int
            ) {
                callback(window, key, scancode, action, mods)
            }
        }
        glfwSetKeyCallback(handle, keyCallback)
    }
}

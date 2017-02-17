package sandbox.ui

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*

class Window {
    val handle: Long

    constructor() {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        handle = glfwCreateWindow(
            800,             // width
            600,             // height
            "Sandbox",       // title
            MemoryUtil.NULL, // monitor
            MemoryUtil.NULL  // share
        )
        if (handle == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create the GLFW window.")
        }

        addKeyCallback { window, key, scancode, action, mods ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
                glfwSetWindowShouldClose(window, true)
            }
        }

        glfwMakeContextCurrent(handle)
        GL.createCapabilities()
        glfwSwapInterval(1) //vsync
    }

    fun addKeyCallback(
        callback: (
            window: Long, key: Int, scancode: Int, action: Int, mods: Int
        ) -> Unit
    ) {
        object : GLFWKeyCallback() {
            override fun invoke(
                window: Long, key: Int, scancode: Int, action: Int, mods: Int
            ) {
                callback(window, key, scancode, action, mods)
            }
        }.set(handle)
    }
}

package sandbox.ui

import kotlin.properties.Delegates

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import org.lwjgl.opengl.GL11.*

class Window {
    public val callbacks: Callbacks

    private val handle: Long
    private val debug: Boolean

    constructor(width: Int, height: Int) {
        debug = (System.getProperty("debug") != null)
        handle = initGLFW(width, height)
        initOpenGL(width, height)
        callbacks = initCallbacks()
    }

    private fun initGLFW(width: Int, height: Int) : Long {
        if (debug) { GLFWErrorCallback.createPrint(System.err).set() }

        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        val handle = glfwCreateWindow(
            width,           // width
            height,          // height
            "Sandbox",       // title
            MemoryUtil.NULL, // monitor
            MemoryUtil.NULL  // share
        )
        if (handle == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create the GLFW window.")
        }

        glfwMakeContextCurrent(handle)
        glfwSwapInterval(1) //vsync

        return handle
    }

    private fun initOpenGL(width: Int, height: Int) {
        GL.createCapabilities()
        glViewport(0, 0, width, height)

        if (debug) { GLUtil.setupDebugMessageCallback() }
    }

    private fun initCallbacks() : Callbacks {
        val callbacks = Callbacks(handle)
        callbacks.addKeyCallback { window, key, scancode, action, mods ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
                glfwSetWindowShouldClose(window, true)
            }
        }

        return callbacks
    }

    fun shouldClose() : Boolean {
        return glfwWindowShouldClose(handle)
    }

    fun swapBuffers() {
        glfwSwapBuffers(handle)
    }
}

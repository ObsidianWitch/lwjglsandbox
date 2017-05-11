package sandbox.ui

import kotlin.properties.Delegates

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import org.lwjgl.opengl.GL11.*

class Window {
    val callbacks: Callbacks
    val width: Int
    val height: Int

    private val handle: Long
    private val debug: Boolean

    constructor(width: Int, height: Int) {
        debug = (System.getProperty("debug") != null)
        handle = initGLFW(width, height)
        initOpenGL(width, height)
        callbacks = initCallbacks()
        this.width = width
        this.height = height
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
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glViewport(0, 0, width, height)

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        if (debug) { GLUtil.setupDebugMessageCallback() }
    }

    private fun initCallbacks() : Callbacks {
        val callbacks = Callbacks(handle)
        callbacks.keyCallbacks.add(keyCallback)
        return callbacks
    }

    private val keyCallback: KeyCallback get() = {
        window, key, _, action, _ ->
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
            glfwSetWindowShouldClose(window, true)
        }
    }

    fun loop(instructions: () -> Unit) {
        while (!glfwWindowShouldClose(handle)) {
            glfwPollEvents()

            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            instructions()

            glfwSwapBuffers(handle)
        }

        glfwTerminate()
    }
}

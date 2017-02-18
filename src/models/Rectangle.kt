package sandbox.models

import java.nio.IntBuffer

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

import sandbox.shaders.Shader

class Rectangle {
    private val shader: Shader
    private val vertexArray : Int
    private val indices : IntArray

    constructor() {
        val vertices = floatArrayOf(
             0.5f,  0.5f, 0.0f,  // Top Right
             0.5f, -0.5f, 0.0f,  // Bottom Right
            -0.5f, -0.5f, 0.0f,  // Bottom Left
            -0.5f,  0.5f, 0.0f   // Top Left
        )
        indices = intArrayOf(
            0, 1, 3,   // First Triangle
            1, 2, 3    // Second Triangle
        )

        // Generate and compile shader
        shader = Shader().add(GL_VERTEX_SHADER, "src/shaders/main.vs")
                         .add(GL_FRAGMENT_SHADER, "src/shaders/main.fs")
                         .link()

        // Generate and bind vertex array
        vertexArray = glGenVertexArrays()
        glBindVertexArray(vertexArray)

        // Generate and bind vertex buffer
        val vertexBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        // Generate and bind element buffer
        val elementBuffer = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBuffer)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        // Vertex attribute pointers
        // vertices positions at index 0
        glVertexAttribPointer(
            0,        // index
            3,        // size
            GL_FLOAT, // type
            false,    // normalized
            0,        // stride
            0         // pointer/offset
        )
        glEnableVertexAttribArray(0)

        // Unbind vertex array & buffers
        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun draw() {
        shader.use()

        glBindVertexArray(vertexArray)
        glDrawElements(GL_TRIANGLES, IntBuffer.wrap(indices))
        glBindVertexArray(0)
    }
}

package sandbox.models

import java.nio.IntBuffer

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

import sandbox.shaders.Shader

class Mesh {
    private val vertices: FloatArray
    private val indices: IntArray
    private val vertexArray : Int
    private val shader: Shader // TODO Material class

    constructor(vertices: FloatArray, indices: IntArray, shader: Shader) {
        this.vertices = vertices
        this.indices = indices
        this.shader = shader

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

    fun render() {
        shader.bind()

        glBindVertexArray(vertexArray)
        glDrawElements(GL_TRIANGLES, IntBuffer.wrap(indices))
        glBindVertexArray(0)

        shader.unbind()
    }
}

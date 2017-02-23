package sandbox.models

import java.nio.IntBuffer

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

import sandbox.materials.Material

class Mesh {
    private val indices: IntArray
    private val vertexArray : Int
    val material: Material

    constructor(vertices: FloatArray, indices: IntArray, material: Material) {
        this.indices = indices
        this.material = material

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

        setAttributePointers()

        // Unbind vertex array & buffers
        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    // Specifies the location and data format of vertex attribute arrays,
    // and enables them.
    private fun setAttributePointers() {
        val vertexSize = 5 * 4

        // positions
        // index, size, type, normalized, stride, offset
        glVertexAttribPointer(0, 3, GL_FLOAT, false, vertexSize, 0)
        glEnableVertexAttribArray(0)

        // texture coordinates
        glVertexAttribPointer(1, 2, GL_FLOAT, false, vertexSize, 3 * 4)
        glEnableVertexAttribArray(1)
    }

    fun draw() {
        glBindVertexArray(vertexArray)
        glDrawElements(GL_TRIANGLES, IntBuffer.wrap(indices))
        glBindVertexArray(0)
    }
}

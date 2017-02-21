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

        // Vertex attribute pointers
        // vertices positions at index 0
        glVertexAttribPointer(
            0,        // index
            3,        // size
            GL_FLOAT, // type
            false,    // normalized
            4 * 5,    // stride, sizeof(GL_FLOAT) * 5 elements for one vertex
            0         // pointer/offset
        )
        glEnableVertexAttribArray(0)

        // vertices positions at index 1
        glVertexAttribPointer(
            1,        // index
            2,        // size
            GL_FLOAT, // type
            false,    // normalized
            4 * 5,    // stride, sizeof(GL_FLOAT) * 5 elements for one vertex
            4 * 3     // pointer/offset, sizeof(GL_FLOAT) * 3 elements before
        )
        glEnableVertexAttribArray(1)

        // Unbind vertex array & buffers
        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun draw() {
        glBindVertexArray(vertexArray)
        glDrawElements(GL_TRIANGLES, IntBuffer.wrap(indices))
        glBindVertexArray(0)
    }
}

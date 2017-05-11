package sandbox.models

import kotlin.properties.Delegates

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

import sandbox.materials.Material

class Mesh {
    companion object {
        private val loadedMeshes: MutableList<Mesh> = mutableListOf()
    }

    private val path: String
    private var vertexArray: Int by Delegates.notNull()
    private var indicesSize: Int

    val material: Material

    constructor(path: String, material: Material) {
        this.path = path
        this.material = material

        // Only load the mesh if it has not already been loaded.
        val existingMesh = loadedMeshes
                         .filter { it.path == this.path }
                         .firstOrNull()

        if (existingMesh != null) {
            this.vertexArray = existingMesh.vertexArray
            this.indicesSize = existingMesh.indicesSize
        } else {
            val mesh = MeshLoader(path)
            this.indicesSize = mesh.indices.size

            createBuffers(mesh.vertices, mesh.indices)
            loadedMeshes.add(this)
        }
    }

    private fun createBuffers(vertices: FloatArray, indices: IntArray) {
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
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    // Specifies the location and data format of vertex attribute arrays,
    // and enables them.
    private fun setAttributePointers() {
        val vertexSize = 8 * 4

        // positions
        // index, size, type, normalized, stride, offset
        glVertexAttribPointer(0, 3, GL_FLOAT, false, vertexSize, 0)
        glEnableVertexAttribArray(0)

        // normals
        glVertexAttribPointer(1, 3, GL_FLOAT, false, vertexSize, 3 * 4)
        glEnableVertexAttribArray(1)

        // texture coordinates
        glVertexAttribPointer(2, 2, GL_FLOAT, false, vertexSize, 6 * 4)
        glEnableVertexAttribArray(2)
    }

    fun draw() = material.use {
        glBindVertexArray(vertexArray)
        glDrawElements(GL_TRIANGLES, indicesSize, GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)
    }
}

package sandbox.models

import kotlin.properties.Delegates
import java.nio.IntBuffer
import java.nio.FloatBuffer

import org.lwjgl.assimp.*
import org.lwjgl.assimp.Assimp.*;
import org.lwjgl.BufferUtils

// Handles loading a Mesh using the Assimp library.
// If an Assimp:aiScene contains multiple meshes, only load one.
// To replicate the behaviour of one model containing multiple meshes, Node and
// VisibleNode should be used.
// Assimp:aiMaterials are not exploited. Material definitions can be handled
// in classes inherithing from Material.
class MeshLoader {
    private val path: String

    var vertices: FloatArray by Delegates.notNull()
    var indices: IntArray by Delegates.notNull()

    constructor(path: String) {
        this.path = path
        load()
    }

    private fun load() {
        val vertices = mutableListOf<Float>()
        val indices  = mutableListOf<Int>()

        val scene = aiImportFile(
            path,
            aiProcess_JoinIdenticalVertices
         or aiProcess_FlipUVs
        )
        val mesh = AIMesh.create(scene.mMeshes()[0])

        // vertices
        for (i in 0..mesh.mNumVertices() - 1) {
            val position = mesh.mVertices()[i]
            vertices.add(position.x())
            vertices.add(position.y())
            vertices.add(position.z())

            val normal = mesh.mNormals()[i]
            vertices.add(normal.x())
            vertices.add(normal.y())
            vertices.add(normal.z())

            val uv = mesh.mTextureCoords(0)[i]
            vertices.add(uv.x())
            vertices.add(uv.y())
        }

        // indices
        for (i in 0..mesh.mNumFaces() - 1) {
            val face = mesh.mFaces()[i].mIndices()
            indices.add(face[0])
            indices.add(face[1])
            indices.add(face[2])
        }

        this.vertices = vertices.toFloatArray()
        this.indices = indices.toIntArray()
    }
}

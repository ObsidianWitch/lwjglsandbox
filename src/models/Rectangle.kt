package sandbox.models

import org.joml.*

import sandbox.materials.Unshaded

class Rectangle {
    private val mesh: Mesh

    constructor() {
        mesh = Mesh(
            vertices = floatArrayOf(
                 0.5f,  0.5f, 0.0f,  // Top Right
                 0.5f, -0.5f, 0.0f,  // Bottom Right
                -0.5f, -0.5f, 0.0f,  // Bottom Left
                -0.5f,  0.5f, 0.0f   // Top Left
            ),
            indices = intArrayOf(
                0, 1, 3,   // First Triangle
                1, 2, 3    // Second Triangle
            ),
            material = Unshaded().apply {
                diffuseColor = Vector4f(0.0f, 0.0f, 1.0f, 1.0f)
            }
        )
    }

    fun render() {
        mesh.render()
    }
}

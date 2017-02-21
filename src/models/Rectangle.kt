package sandbox.models

import org.joml.*

import sandbox.materials.Unshaded
import sandbox.models.Texture

class Rectangle {
    private val mesh: Mesh
    private val model: Matrix4f

    constructor() {
        mesh = Mesh(
            vertices = floatArrayOf(
                 // Positions          // Texture Coordinates
                 0.5f,  0.5f, 0.0f,    1.0f, 1.0f, // Top Right
                 0.5f, -0.5f, 0.0f,    1.0f, 0.0f, // Bottom Right
                -0.5f, -0.5f, 0.0f,    0.0f, 0.0f, // Bottom Left
                -0.5f,  0.5f, 0.0f,    0.0f, 1.0f  // Top Left
            ),
            indices = intArrayOf(
                0, 1, 3,   // First Triangle
                1, 2, 3    // Second Triangle
            ),
            material = Unshaded().apply {
                diffuseColor   = Vector4f(1.0f, 1.0f, 0.0f, 1.0f)
                diffuseTexture = Texture("resources/wall.jpg")
            }
        )

        model = Matrix4f().apply {
            rotateZ(Math.toRadians(90.0).toFloat())
            scale(1.0f, 0.5f, 1.0f)
        }
    }

    fun render() = mesh.render {
        material.shader.setUniform("model", model)
    }
}

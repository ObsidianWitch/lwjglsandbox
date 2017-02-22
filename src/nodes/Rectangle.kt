package sandbox.nodes

import org.joml.*

import sandbox.materials.Unshaded
import sandbox.models.Mesh
import sandbox.models.Texture

import org.lwjgl.glfw.GLFW.*

class Rectangle : VisibleNode {
    constructor() : super() {
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
    }

    override fun update(f: () -> Unit) = super.update {
        localMatrix.apply {
            identity()
            rotateZ(glfwGetTime().toFloat())
        }
    }
}

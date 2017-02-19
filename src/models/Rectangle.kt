package sandbox.models

import org.lwjgl.opengl.GL20.*
import org.joml.*

import sandbox.shaders.Shader

class Rectangle {
    private val mesh: Mesh

    constructor() {
        val vertices = floatArrayOf(
             0.5f,  0.5f, 0.0f,  // Top Right
             0.5f, -0.5f, 0.0f,  // Bottom Right
            -0.5f, -0.5f, 0.0f,  // Bottom Left
            -0.5f,  0.5f, 0.0f   // Top Left
        )

        val indices = intArrayOf(
            0, 1, 3,   // First Triangle
            1, 2, 3    // Second Triangle
        )

        val shader = Shader()
           .add(GL_VERTEX_SHADER, "src/shaders/main.vs")
           .add(GL_FRAGMENT_SHADER, "src/shaders/main.fs")
           .link()
           .bind()
           .setUniform("diffuseColor", Vector4f(0.0f, 0.0f, 1.0f, 1.0f))
           .unbind()

        mesh = Mesh(vertices, indices, shader)
    }

    fun render() {
        mesh.render()
    }
}

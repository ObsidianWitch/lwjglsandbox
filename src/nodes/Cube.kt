package sandbox.nodes

import org.joml.*

import sandbox.materials.Unshaded
import sandbox.models.Mesh
import sandbox.models.Texture

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*

class Cube : VisibleNode {
    constructor() : super() {
        mesh = Mesh(
            path = "resources/cube.obj",
            material = Unshaded().apply {
                diffuseColor   = Vector4f(1.0f, 1.0f, 0.0f, 1.0f)
                diffuseTexture = Texture(
                    path = "resources/wall.jpg",
                    unit = GL_TEXTURE0
                )
            }
        )
    }
}

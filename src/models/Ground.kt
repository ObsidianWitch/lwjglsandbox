package sandbox.nodes

import org.joml.*

import sandbox.materials.Unshaded
import sandbox.models.Mesh
import sandbox.models.Texture

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*

class Ground : VisibleNode {
    constructor() : super() {
        mesh = Mesh(
            path = "resources/ground/ground.obj",
            material = Unshaded().apply {
                diffuseTexture = Texture(
                    path = "resources/ground/ground.jpg",
                    unit = GL_TEXTURE0
                )
            }
        )
    }
}

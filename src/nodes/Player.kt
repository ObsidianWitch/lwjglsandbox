package sandbox.nodes

import org.joml.*

import sandbox.materials.Unshaded
import sandbox.models.Mesh
import sandbox.models.Texture

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*

class Player : VisibleNode {
    constructor() : super() {
        mesh = Mesh(
            path = "resources/aina/aina.obj",
            material = Unshaded().apply {
                diffuseTexture = Texture(
                    path = "resources/aina/textures.png",
                    unit = GL_TEXTURE0
                )
            }
        )
    }
}

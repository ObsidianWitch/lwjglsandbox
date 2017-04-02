package sandbox.nodes

import org.joml.*

import sandbox.materials.Unshaded
import sandbox.models.Mesh
import sandbox.models.Texture

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*
import org.joml.Vector3f

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

    // Simulates breathing using scaling.
    override fun update(f: () -> Unit) {
        super.update(f)

        val scaleVec = Vector3f(1.0f)
        scaleVec.y += 0.005f * Math.exp(Math.sin(glfwGetTime())).toFloat()

        tmpLocal.identity().scale(Vector3f(scaleVec))
    }
}

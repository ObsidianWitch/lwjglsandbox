package sandbox.materials

import org.joml.Vector4f
import org.joml.Matrix4f
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL20.*

import sandbox.models.Texture

class Unshaded : Material {
    override protected val shader = Shader().apply {
       add(GL_VERTEX_SHADER, "src/materials/general/Main.vs")
       add(
           GL_FRAGMENT_SHADER,
           "src/materials/general/Texture.fs",
           "#define UNSHADED"
       )
       add(GL_FRAGMENT_SHADER, "src/materials/unshaded/Unshaded.fs")
       link()
    }

    var diffuseColor: Vector4f
        set(value) {
            field = value
            setUniform("material.diffuseColor", field)
        }

    var diffuseTexture: Texture?
        set(value) {
            if(value == null) { return }

            field = value
            setUniform("material.hasDiffuseTexture", 1)
            setUniform("material.diffuseTexture", field!!.unit - GL_TEXTURE0)
        }

    constructor() : super() {
        diffuseColor = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
        diffuseTexture = null
    }

    override fun use(f: () -> Unit) {
        val g : () -> Unit = {
            diffuseTexture?.bind(); f(); diffuseTexture?.unbind()
        }
        super.use(g)
    }
}

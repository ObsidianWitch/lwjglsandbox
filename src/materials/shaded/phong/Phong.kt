package sandbox.materials

import org.joml.Vector4f
import org.joml.Matrix4f
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL20.*

import sandbox.models.Texture

class Phong : Material {
    override protected val shader = Shader().apply {
        add(GL_VERTEX_SHADER, "src/materials/general/Main.vs")
        add(
            GL_FRAGMENT_SHADER,
            "src/materials/general/Texture.fs",
            "#define SHADED"
        )
        add(GL_FRAGMENT_SHADER, "src/materials/shaded/Lights.fs")
        add(GL_FRAGMENT_SHADER, "src/materials/shaded/phong/Phong.fs")
        add(GL_FRAGMENT_SHADER, "src/materials/shaded/Shaded.fs")
        link()
    }

    var ambientColor: Vector4f
        set(value) {
            field = value
            setUniform("material.ambientColor", field)
        }

    var diffuseColor: Vector4f
        set(value) {
            field = value
            setUniform("material.diffuseColor", field)
        }

    var specularColor: Vector4f
        set(value) {
            field = value
            setUniform("material.specularColor", field)
        }

    var specularHighlight: Float
        set(value) {
            field = value
            setUniform("material.specularHighlight", field)
        }

    var diffuseTexture: Texture?
        set(value) {
            if (value == null) { return }

            field = value
            setUniform("material.hasDiffuseTexture", 1)
            setUniform("material.diffuseTexture", field!!.unit - GL_TEXTURE0)
        }

    constructor() : super() {
        ambientColor = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
        diffuseColor = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
        specularColor = Vector4f(0.0f, 0.0f, 0.0f, 1.0f)
        specularHighlight = 0.0f
        diffuseTexture = null
    }

    override fun use(f: () -> Unit) = super.use {
        diffuseTexture?.bind(); f(); diffuseTexture?.unbind()
    }
}

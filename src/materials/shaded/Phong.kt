package sandbox.materials

import org.joml.Vector4f
import org.joml.Matrix4f
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL20.*

import sandbox.models.Texture

class Phong : Material {
    override val shader: Shader = Shader().apply {
       add(GL_VERTEX_SHADER, "src/materials/general/Main.vs")
       add(GL_FRAGMENT_SHADER, "src/materials/shaded/Lights.fs")
       add(GL_FRAGMENT_SHADER, "src/materials/shaded/Phong.fs")
       add(GL_FRAGMENT_SHADER, "src/materials/shaded/Shaded.fs")
       link()
    }

    var ambientColor: Vector4f
        set(value) = shader.use {
            field = value
            setUniform("material.ambientColor", field)
        }

    var diffuseColor: Vector4f
        set(value) = shader.use {
            field = value
            setUniform("material.diffuseColor", field)
        }

    var specularColor: Vector4f
        set(value) = shader.use {
            field = value
            setUniform("material.specularColor", field)
        }

    var specularHighlight: Float
        set(value) = shader.use {
            field = value
            setUniform("material.specularHighlight", field)
        }

    var diffuseTexture: Texture? = null
        set(value) = shader.use {
            field = value
            setUniform("material.hasDiffuseTexture", 1)
            setUniform("material.diffuseTexture", field!!.unit - GL_TEXTURE0)
        }

    constructor() : super() {
        ambientColor = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
        diffuseColor = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
        specularColor = Vector4f(0.0f, 0.0f, 0.0f, 1.0f)
        specularHighlight = 0.0f

    }

    override fun bind() {
        super.bind()
        diffuseTexture?.bind()
    }
    override fun unbind() {
        diffuseTexture?.unbind()
        shader.unbind()
    }
}

package sandbox.materials

import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*

import sandbox.models.Texture

class Unshaded : Material() {
    override protected val shader: Shader = Shader().apply {
       add(GL_VERTEX_SHADER, "src/materials/unshaded/Unshaded.vs")
       add(GL_FRAGMENT_SHADER, "src/materials/unshaded/Unshaded.fs")
       link()
    }

    var diffuseColor: Vector4f? = null
        set(value) = shader.use {
            field = value
            setUniform("material.hasDiffuseColor", 1)
            setUniform("material.diffuseColor", value!!)
        }

    var diffuseTexture: Texture? = null
        set(value) = shader.use {
            field = value
            setUniform("material.hasDiffuseTexture", 1)
            setUniform("material.diffuseTexture", 0)
        }

    override fun bind() {
        super.bind()
        diffuseTexture?.bind()
    }
    override fun unbind() {
        shader.unbind()
        diffuseTexture?.unbind()
    }
}

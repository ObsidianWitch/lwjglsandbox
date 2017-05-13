package sandbox.materials

import org.joml.Vector4f
import org.lwjgl.opengl.GL13.*

import sandbox.models.Texture

class UnshadedUniforms {
    private val handler: UniformsHandler

    constructor(shaders: List<Shader>) {
        handler = UniformsHandler(shaders)

        diffuseColor = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
        diffuseTexture = null
    }

    var diffuseColor: Vector4f
        set(value) {
            field = value
            handler.setUniform("material.diffuseColor", field)
        }

    var diffuseTexture: Texture?
        set(value) {
            if (value == null) { return }

            field = value
            handler.setUniform("material.hasDiffuseTexture", 1)
            handler.setUniform("material.diffuseTexture", field!!.unit - GL_TEXTURE0)
        }
}

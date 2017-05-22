package sandbox.materials

import kotlin.properties.Delegates

import org.joml.Vector4f
import org.lwjgl.opengl.GL13.*

import sandbox.models.TextureCubeMap

class SkyboxUniforms {
    private val handler: UniformsHandler

    constructor(shaders: List<Shader>) {
        handler = UniformsHandler(shaders)

        backTexture = null
        frontTexture = null
    }

    var backTexture: TextureCubeMap?
        set(value) {
            if (value == null) { return }

            field = value
            handler.setUniform("material.backTexture", field!!.unit - GL_TEXTURE0)
        }

    var frontTexture: TextureCubeMap?
        set(value) {
            if (value == null) { return }

            field = value
            handler.setUniform("material.frontTexture", field!!.unit - GL_TEXTURE0)
        }
}

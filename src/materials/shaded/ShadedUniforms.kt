package sandbox.materials

import org.joml.Vector4f

class ShadedUniforms {
    private val handler: UniformsHandler

    constructor(shaders: List<Shader>) {
        handler = UniformsHandler(shaders)

        ambientColor = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
        specularColor = Vector4f(0.0f, 0.0f, 0.0f, 1.0f)
        specularHighlight = 0.0f
    }

    var ambientColor: Vector4f
        set(value) {
            field = value
            handler.setUniform("material.ambientColor", field)
        }

    var specularColor: Vector4f
        set(value) {
            field = value
            handler.setUniform("material.specularColor", field)
        }

    var specularHighlight: Float
        set(value) {
            field = value
            handler.setUniform("material.specularHighlight", field)
        }
}

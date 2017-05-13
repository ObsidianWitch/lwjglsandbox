package sandbox.materials

import org.joml.Matrix3f
import org.joml.Matrix4f

class MeshUniforms {
    private val handler: UniformsHandler

    constructor(shaders: List<Shader>) {
        handler = UniformsHandler(shaders)

        model = Matrix4f()
        normalMatrix = Matrix3f()
    }

    var model : Matrix4f
        set(value) {
            field = value
            handler.setUniform("model", field)
        }

    var normalMatrix : Matrix3f
        set(value) {
            field = value
            handler.setUniform("normalMatrix", field)
        }
}

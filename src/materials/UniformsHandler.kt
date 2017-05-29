package sandbox.materials

import kotlin.reflect.KProperty

import org.joml.Vector4f

class UniformsHandler {
    private val shaders: List<Shader>

    constructor(shaders: List<Shader>) { this.shaders = shaders }

    fun <T> setUniform(name: String, value: T) {
        shaders.forEach {
            it.bind()
            it.setUniform(name, value)
        }
    }
}

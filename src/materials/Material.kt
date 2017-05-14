package sandbox.materials

import kotlin.properties.Delegates

import org.joml.Matrix3f
import org.joml.Matrix4f

abstract class Material {
    protected val shaders: MutableList<Shader>

    val nodeUniforms : NodeUniforms

    constructor() {
        shaders = mutableListOf()
        nodeUniforms = NodeUniforms(shaders)
    }

    open fun use(f: () -> Unit) = shaders.forEach {
        it.bind(); f(); it.unbind()
    }
}

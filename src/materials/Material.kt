package sandbox.materials

import kotlin.properties.Delegates

import org.joml.Matrix3f
import org.joml.Matrix4f

abstract class Material {
    protected val shaders: MutableList<Shader>

    val meshUniforms : MeshUniforms

    constructor() {
        shaders = mutableListOf()
        meshUniforms = MeshUniforms(shaders)
    }

    open fun use(f: () -> Unit) = shaders.forEach {
        it.bind(); f(); it.unbind()
    }
}

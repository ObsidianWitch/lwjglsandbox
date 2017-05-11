package sandbox.materials

import org.joml.Matrix3f
import org.joml.Matrix4f

abstract class Material {
    protected abstract val shader: Shader

    var model = Matrix4f()
        set(value) {
            field = value
            setUniform("model", field)
        }

    var normalMatrix = Matrix3f()
        set(value) {
            field = value
            setUniform("normalMatrix", field)
        }

    open fun use(f: () -> Unit) { shader.bind(); f(); shader.unbind() }


    protected open fun <T> setUniform(name: String, value: T) {
        shader.use { setUniform(name, value) }
    }
}

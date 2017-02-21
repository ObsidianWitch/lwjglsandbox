package sandbox.materials

import org.lwjgl.glfw.GLFW.*

abstract class Material {
    protected abstract val shader: Shader

    inline fun use(f: () -> Unit) { bind(); f(); unbind() }
    open fun bind() {
        shader.bind()

        Shader.globalUniforms.setUniform(0, glfwGetTime().toFloat())
    }
    open fun unbind() { shader.unbind() }

}

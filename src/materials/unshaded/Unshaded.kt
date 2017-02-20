package sandbox.materials

import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*

class Unshaded : Material() {
    override protected val shader: Shader = Shader().apply {
       add(GL_VERTEX_SHADER, "src/materials/unshaded/Unshaded.vs")
       add(GL_FRAGMENT_SHADER, "src/materials/unshaded/Unshaded.fs")
       link()
    }

    var diffuseColor: Vector4f = Vector4f(0.0f)
        set(value) = shader.use {
            field = value
            setUniform("diffuseColor", value)
            setUniform("hasDiffuseColor", 1)
        }
}

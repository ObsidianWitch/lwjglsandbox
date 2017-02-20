package sandbox.materials

import kotlin.properties.Delegates

import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*

import sandbox.shaders.Shader

class Unshaded : Material() {
    override val shader: Shader = Shader().apply {
       add(GL_VERTEX_SHADER, "src/shaders/main.vs")
       add(GL_FRAGMENT_SHADER, "src/shaders/main.fs")
       link()
    }

    var diffuseColor: Vector4f = Vector4f(0.0f, 0.0f, 0.0f, 0.0f)
        set(value) { shader.block {
            field = value
            setUniform("diffuseColor", value)
        }}
}

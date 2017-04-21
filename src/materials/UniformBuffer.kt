package sandbox.materials

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.opengl.GL31.*


// UniformBuffer handles the creation of a GL_UNIFORM_BUFFER to store uniforms
// shared between multiple shaders.
// The sdt140 layout is used to explicitely states the memory layout for each
// variable type.
// e.g. In the example below, we have 1*float padded to 16B (4 floats) and
// 2*mat4 (64B each = 16 floats each); so we need to allocate 36 floats (144B).
// variable    base alignment    offset    accumulated memory
// float a;    4B                0         16B
// mat4  b;    64B               16        80B
// mat4  c;    64B               80        144B
class UniformBuffer {
    val id: Int
    val bindingPoint: Int

    // `bindingPoint`: index of the uniform buffer
    // `size`: number of floats the uniform buffer can store
    constructor(bindingPoint: Int, size: Int) {
        this.id = glGenBuffers()
        this.bindingPoint = bindingPoint

        use {
            glBufferData(GL_UNIFORM_BUFFER, FloatArray(size), GL_DYNAMIC_DRAW)
            glBindBufferBase(GL_UNIFORM_BUFFER, bindingPoint, id)
        }
    }

    private fun bind() { glBindBuffer(GL_UNIFORM_BUFFER, id) }
    private fun unbind() { glBindBuffer(GL_UNIFORM_BUFFER, 0) }
    private inline fun use(f: () -> Unit) { bind(); f(); unbind() }

    // Tells the `shader` program that the uniform block identified by `name` is
    // associated with the current uniform buffer at `this.bindingPoint`.
    fun associate(shader: Shader, name: String) {
        val globalIndex = glGetUniformBlockIndex(shader.program, name)
        glUniformBlockBinding(shader.program, globalIndex, bindingPoint)
    }

    fun setUniform(offset: Long, value: Matrix4f) = use {
        val fb = BufferUtils.createFloatBuffer(16)
        glBufferSubData(GL_UNIFORM_BUFFER, offset, value.get(fb))
    }

    fun setUniform(offset: Long, value: FloatArray) = use {
        glBufferSubData(GL_UNIFORM_BUFFER, offset, value)
    }
    fun setUniform(offset: Long, value: Float) = use {
        glBufferSubData(GL_UNIFORM_BUFFER, offset, floatArrayOf(value))
    }
}

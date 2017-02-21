package sandbox.materials

import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.opengl.GL31.*

class GlobalUniforms {
    val id: Int

    constructor() {
        id = glGenBuffers()

        use {
            glBufferData(
                GL_UNIFORM_BUFFER,
                FloatArray(40), // allocates 40 floats (160 bytes)
                GL_DYNAMIC_DRAW // the data is likely to change a lot
            )

            glBindBufferBase(GL_UNIFORM_BUFFER, 0, id); //binding point: 0
        }
    }

    private fun bind() { glBindBuffer(GL_UNIFORM_BUFFER, id) }
    private fun unbind() { glBindBuffer(GL_UNIFORM_BUFFER, 0) }
    private inline fun use(f: () -> Unit) { bind(); f(); unbind() }

    fun setUniform(offset: Long, value: FloatArray) = use {
        glBufferSubData(GL_UNIFORM_BUFFER, offset, value)
    }
    fun setUniform(offset: Long, value: Float) = use {
        glBufferSubData(GL_UNIFORM_BUFFER, offset, floatArrayOf(value))
    }
}

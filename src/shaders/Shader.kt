package sandbox.shaders

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.FloatBuffer
import java.util.stream.Collectors

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import org.joml.*

class Shader {
    private var program: Int = 0
    private val shaders: MutableList<Int> = mutableListOf()
    private val uniforms: MutableMap<String, Int> = mutableMapOf()

    fun add(type: Int, path: String) : Shader {
        val src = Files.lines(Paths.get(path))
                       .collect(Collectors.joining("\n"))

        val shader = glCreateShader(type)
        glShaderSource(shader, src)
        glCompileShader(shader)

        val compiled = glGetShaderi(shader, GL_COMPILE_STATUS)
        assert(compiled != 0) { """
            Shader (${path}) compilation failed
            ${glGetShaderInfoLog(shader)}
        """.trimIndent() }

        shaders.add(shader)

        return this
    }

    fun link() : Shader {
        program = glCreateProgram()

        shaders.forEach { glAttachShader(program, it) }

        glLinkProgram(program)
        val linked = glGetProgrami(program, GL_LINK_STATUS)
        assert(linked != 0) { """
            Program linkage failed
            ${glGetProgramInfoLog(program)}
        """.trimIndent() }

        shaders.forEach { glDeleteShader(it) }
        shaders.clear()

        return this
    }

    fun bind() : Shader {
        assert(program != 0)
        glUseProgram(program)

        return this
    }

    fun unbind() : Shader {
        glUseProgram(0)

        return this
    }

    private fun uniformLocation(name: String) : Int {
        if (uniforms.contains(name)) { return uniforms[name]!! }

        val id = glGetUniformLocation(program, name)
        uniforms.put(name, id)
        return id
    }

    fun setUniform(name: String, value: Int) : Shader {
        glUniform1i(uniformLocation(name), value)

        return this
    }

    fun setUniform(name: String, value: Float) : Shader {
        glUniform1f(uniformLocation(name), value)

        return this
    }

    fun setUniform(name: String, value: Vector3f) : Shader {
        val fb = BufferUtils.createFloatBuffer(3)
        glUniform3fv(uniformLocation(name), value.get(fb))

        return this
    }

    fun setUniform(name: String, value: Vector4f) : Shader {
        val fb = BufferUtils.createFloatBuffer(4)
        glUniform4fv(uniformLocation(name), value.get(fb))

        return this
    }

    fun setUniform(name: String, value: Matrix3f) : Shader {
        val fb = BufferUtils.createFloatBuffer(9)
        glUniformMatrix3fv(uniformLocation(name), false, value.get(fb))

        return this
    }

    fun setUniform(name: String, value: Matrix4f) : Shader {
        val fb = BufferUtils.createFloatBuffer(16)
        glUniformMatrix4fv(uniformLocation(name), false, value.get(fb))

        return this
    }
}


package sandbox.materials

import java.io.File
import java.nio.FloatBuffer
import java.util.stream.Collectors

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL31.*
import org.joml.*

class Shader {
    companion object {
        private val programs: MutableList<Shader> = mutableListOf()

        fun setSharedUniform(name: String, value: Int) {
            programs.forEach { it.use { it.setUniform(name, value) } }
        }

        fun setSharedUniform(name: String, value: Float) {
            programs.forEach { it.use { it.setUniform(name, value) } }
        }

        fun setSharedUniform(name: String, value: Vector3f) {
            programs.forEach { it.use { it.setUniform(name, value) } }
        }

        fun setSharedUniform(name: String, value: Vector4f) {
            programs.forEach { it.use { it.setUniform(name, value) } }
        }

        fun setSharedUniform(name: String, value: Matrix3f) {
            programs.forEach { it.use { it.setUniform(name, value) } }
        }

        fun setSharedUniform(name: String, value: Matrix4f) {
            programs.forEach { it.use { it.setUniform(name, value) } }
        }
    }

    val program: Int = glCreateProgram()
    private val shaders: MutableList<Int> = mutableListOf()
    private val uniforms: MutableMap<String, Int> = mutableMapOf()

    // Add the following shader sources specified by `path` and compiles them.
    // Additional sources can be manually added through `optSrc` (e.g. to add
    // preprocessor directives).
    fun add(type: Int, path: String, optSrc: String = "") {
        val src = File(path).readLines().toMutableList().apply {
            add(1, optSrc)
        }.joinToString(separator = "\n")

        val shader = glCreateShader(type)
        glShaderSource(shader, src)
        glCompileShader(shader)

        val compiled = glGetShaderi(shader, GL_COMPILE_STATUS)
        assert(compiled != 0) { """
            Shader (${path}) compilation failed
            ${glGetShaderInfoLog(shader)}
        """.trimIndent() }

        shaders.add(shader)
    }

    fun link() {
        shaders.forEach { glAttachShader(program, it) }

        glLinkProgram(program)
        val linked = glGetProgrami(program, GL_LINK_STATUS)
        assert(linked != 0) { """
            Program linkage failed
            ${glGetProgramInfoLog(program)}
        """.trimIndent() }

        shaders.forEach { glDeleteShader(it) }
        shaders.clear()

        programs.add(this)
    }

    fun bind() {
        glUseProgram(program)
    }
    fun unbind() { glUseProgram(0) }
    inline fun use(f: Shader.() -> Unit) { bind(); f(); unbind() }

    // Retrieves the uniform (identified by `name`) location.
    // The first call asks OpenGL for the location, subsequent calls retrieve
    // the location from the `uniforms` map.
    private fun uniformLocation(name: String) : Int {
        if (uniforms.contains(name)) { return uniforms[name]!! }

        val id = glGetUniformLocation(program, name)
        uniforms.put(name, id)
        return id
    }

    fun setUniform(name: String, value: Int) {
        glUniform1i(uniformLocation(name), value)
    }

    fun setUniform(name: String, value: Float) {
        glUniform1f(uniformLocation(name), value)
    }

    fun setUniform(name: String, value: Vector3f) {
        val fb = BufferUtils.createFloatBuffer(3)
        glUniform3fv(uniformLocation(name), value.get(fb))
    }

    fun setUniform(name: String, value: Vector4f) {
        val fb = BufferUtils.createFloatBuffer(4)
        glUniform4fv(uniformLocation(name), value.get(fb))
    }

    fun setUniform(name: String, value: Matrix3f) {
        val fb = BufferUtils.createFloatBuffer(9)
        glUniformMatrix3fv(uniformLocation(name), false, value.get(fb))
    }

    fun setUniform(name: String, value: Matrix4f) {
        val fb = BufferUtils.createFloatBuffer(16)
        glUniformMatrix4fv(uniformLocation(name), false, value.get(fb))
    }
}


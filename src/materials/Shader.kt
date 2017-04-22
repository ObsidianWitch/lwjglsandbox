package sandbox.materials

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.FloatBuffer
import java.util.stream.Collectors

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL31.*
import org.joml.*

class Shader {
    companion object {
        // `globalUniforms` stores the following uniforms.
        // variable        size    offset    accumulated memory    owner
        // time            4B      0         16B                   Shader
        // projection*view 64B     16        80B                   Camera
        // -> accumulated memory = 80B = 20F
        val globalUniforms: UniformBuffer = UniformBuffer(
            name         = "global",
            bindingPoint = 0,
            size         = 20
        )

        // `lightsUniforms` stores the following uniforms.
        // variable        size    offset    accumulated memory    owner
        // ambientLight    16B     0         16B                   AmbientLight
        val lightsUniforms: UniformBuffer = UniformBuffer(
            name         = "lights",
            bindingPoint = 1,
            size         = 4
        )
    }

    val program: Int = glCreateProgram()
    private val shaders: MutableList<Int> = mutableListOf()
    private val uniforms: MutableMap<String, Int> = mutableMapOf()

    fun add(type: Int, path: String) {
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

        // Associate the current shader program with the `global` and `lights`
        // uniform buffers.
        globalUniforms.associate(this)
        lightsUniforms.associate(this)
    }

    fun bind() {
        globalUniforms.setUniform(0, glfwGetTime().toFloat())
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


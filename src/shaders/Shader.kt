package sandbox.shaders

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

import org.lwjgl.opengl.GL20.*

class Shader {
    private var program: Int = 0
    private val shaders: MutableList<Int> = mutableListOf()

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

    fun bind() {
        assert(program != 0)
        glUseProgram(program)
    }

    fun unbind() {
        glUseProgram(0)
    }
}


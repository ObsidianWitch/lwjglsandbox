package sandbox.models

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

import sandbox.shaders.Shader

class Triangle {
    private val shader: Shader
    private val vertexArray : Int

    constructor() {
        val vertices = floatArrayOf(
            -0.5f, -0.5f, 0.0f,
             0.5f, -0.5f, 0.0f,
             0.0f,  0.5f, 0.0f
        )

        // Generate and compile shader
        shader = Shader().add(GL_VERTEX_SHADER, "src/shaders/main.vs")
                         .add(GL_FRAGMENT_SHADER, "src/shaders/main.fs")
                         .link()

        // Generate and bind vertex array
        vertexArray = glGenVertexArrays()
        glBindVertexArray(vertexArray);

        // Generate and bind vertex buffer
        val vertexBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        // Vertex attribute pointers
        // vertices positions at index 0
        glVertexAttribPointer(
            0,        // index
            3,        // size
            GL_FLOAT, // type
            false,    // normalized
            0,        // stride
            0         // pointer/offset
        )
        glEnableVertexAttribArray(0);

        // Unbind vertex array & buffers
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    fun draw() {
        shader.use()

        glBindVertexArray(vertexArray);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindVertexArray(0);
    }
}

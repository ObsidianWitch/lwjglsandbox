package sandbox.nodes

import org.lwjgl.glfw.GLFW.*
import org.joml.Vector3f
import org.joml.Vector4f

import sandbox.materials.Shader

// A point light emits light equaly in all directions. Light intensity decays
// based on the distance from the light to the object.
class PointLight : AmbientLight {
    var constant: Float
    var linear: Float
    var quadratic: Float
    var frequency: Float

    constructor(
        name: String, color: Vector4f, position: Vector3f,
        constant: Float, linear: Float, quadratic: Float,
        frequency: Float = 0.0f
    ) : super(name, color) {
        this.model.translate(position)
        this.constant = constant
        this.linear = linear
        this.quadratic = quadratic
        this.frequency = frequency
    }

    override fun update() {
        super.update()

        val quadOverTime = if (frequency == 0.0f) 1.0f else Math.exp(Math.sin(
            frequency * glfwGetTime()
        )).toFloat()

        Shader.setSharedUniform("${name}.position", position)
        Shader.setSharedUniform("${name}.constant", constant)
        Shader.setSharedUniform("${name}.linear", linear)
        Shader.setSharedUniform("${name}.quadratic", quadratic * quadOverTime)
    }
}

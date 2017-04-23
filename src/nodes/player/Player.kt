package sandbox.nodes

import org.joml.*

import sandbox.materials.Phong
import sandbox.models.Mesh
import sandbox.models.Texture

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*
import org.joml.Vector3f

data class Movements (
    var forward: Boolean  = false,
    var backward: Boolean = false,
    var left: Boolean     = false,
    var right: Boolean    = false,
    var strafing: Boolean = false
)

class Player : VisibleNode {
    val movements: Movements

    private val speed: Float

    constructor(speed: Float) : super() {
        this.speed = speed
        this.movements = Movements()

        mesh = Mesh(
            path = "resources/aina/aina.obj",
            material = Phong().apply {
                diffuseTexture = Texture(
                    path = "resources/aina/textures.png",
                    unit = GL_TEXTURE0
                )
            }
        )
    }

    // Simulates breathing using scaling.
    override fun update() {
        super.update()

        val scaleVec = Vector3f(1.0f)
        scaleVec.y += 0.005f * Math.exp(Math.sin(glfwGetTime())).toFloat()

        tmpLocal.identity().scale(scaleVec)

        move()
    }

    // Moves/Rotates the player in the directions specified by `movements`.
    // The movement vector's magnitude is clamped in order to move at the same
    // speed in all directions and avoid problems such as straferunning.
    fun move() {
        val moveVec = Vector3f(0.0f)

        if (movements.forward) { moveVec.z += speed }
        else if (movements.backward) { moveVec.z -= speed }

        val moveSide = { value: Float ->
            if (movements.strafing) { moveVec.x += value }
            else {
                model.rotate(
                    value,           // angle
                    0.0f, 1.0f, 0.0f // axis
                 )
             }
        }
        if (movements.left) { moveSide(speed) }
        else if (movements.right) { moveSide(-speed) }

        // clamp moveVec's magnitude
        val ratio = moveVec.length() / speed
        if (ratio != 0.0f) { moveVec.div(ratio) }

        model.translate(moveVec)
    }
}

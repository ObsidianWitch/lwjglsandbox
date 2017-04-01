package sandbox.nodes

import org.joml.Vector3f
import org.joml.Matrix4f

import sandbox.materials.Shader

// Creates a perspective Camera which holds the view & projection matrices.
class Camera : Node {
    companion object {
        private val MIN_ZOOM = 1.0f
        private val MAX_ZOOM = 10.0f
    }

    val target: Node
    val targetOffset: Vector3f
    val aspect: Float
    val fov: Float
    val zNear: Float
    val zFar: Float

    val targetPosition: Vector3f
        get() = Vector3f(target.position).add(targetOffset)

    val direction: Vector3f
        get() = Vector3f(position).sub(targetPosition).normalize()

    val right: Vector3f
        get() = Vector3f(direction).cross(0.0f, 1.0f, 0.0f).normalize()

    val up: Vector3f
        get() = Vector3f(right).cross(direction).normalize()

    val projection: Matrix4f
        get() = Matrix4f().perspective(fov, aspect, zNear, zFar)

    val view: Matrix4f
        get() = Matrix4f().lookAt(position, targetPosition, up)

    val projectionView: Matrix4f
        get() = Matrix4f(projection).mul(view)

    constructor(
        target: Node,
        aspect: Float,
        targetOffset: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
        position: Vector3f = Vector3f(0.0f, target.position.y(), -5.0f),
        fov: Float = Math.toRadians(45.0).toFloat(),
        zNear: Float = 0.01f,
        zFar: Float = 100.0f
    ) : super() {
        model.translate(position)
        this.target = target
        this.aspect = aspect
        this.targetOffset = targetOffset
        this.fov = fov
        this.zNear = zNear
        this.zFar = zFar
    }

    fun zoom(value: Float) {
        val zoomVec = direction.mul(-value)
        val newPosition = Vector3f(position).add(zoomVec)
        val distanceToTarget = newPosition.distance(targetPosition)

        if (distanceToTarget > MIN_ZOOM && distanceToTarget < MAX_ZOOM) {
            model.translate(zoomVec)
        }
    }

    override fun update(f: () -> Unit) {
        Shader.globalUniforms.setUniform(
            offset = 16,
            value  = projectionView
        )
    }
}

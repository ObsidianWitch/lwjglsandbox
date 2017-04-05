package sandbox.nodes

import org.joml.Vector2d
import org.joml.Vector3f
import org.joml.Matrix4f

import sandbox.materials.Shader

// Creates a perspective chase Camera which holds the view & projection matrices.
class Camera : Node {
    companion object {
        private val MIN_ZOOM  = 1.0f
        private val MAX_ZOOM  = 5.0f
        private val MIN_PITCH = -15.0f
        private val MAX_PITCH = 30.0f
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
        position: Vector3f = Vector3f(0.0f, 0.0f, 0.0f),
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
        val zoomVec = direction.mul(value)
        val distanceToTarget = position.add(zoomVec).distance(targetPosition)

        if (distanceToTarget > MIN_ZOOM && distanceToTarget < MAX_ZOOM) {
            model.translate(zoomVec)
        }
    }

    // Rotates the camera (represented by its `position`) by `angle` around
    // an `axis` with `targetPosition` as the rotation center.
    private fun rotate(angle: Float, axis: Vector3f) {
        val newPosition = position
        Matrix4f().translate(targetPosition)
                  .rotate(angle, axis.x, axis.y, axis.z)
                  .translate(targetPosition.negate())
                  .transformPosition(newPosition)

        model.translate(newPosition.sub(position))
    }

    // Rotates the camera around its `up` vector with `targetPosition` as the
    // rotation center.
    fun yaw(deltax: Double) {
        rotate(
            angle = Math.toRadians(deltax).toFloat(),
            axis  = up
        )
    }

    // Rotates the camera around its `right` vector with `targetPosition` as the
    // rotation center.
    // pitch = atan(direction.y / direction.length) where direction is a
    // normalized vector
    // pitch = atan(direction.y) = asin(direction.y)
    fun pitch(deltay: Double) {
        val pitch = Math.toDegrees(
            Math.atan(direction.y.toDouble())
        ) + deltay

        if (pitch > MIN_PITCH && pitch < MAX_PITCH) {
            rotate(
                angle = Math.toRadians(deltay).toFloat(),
                axis  = right
            )
        }
    }

    override fun update(f: () -> Unit) {
        Shader.globalUniforms.setUniform(
            offset = 16,
            value  = projectionView
        )
    }
}

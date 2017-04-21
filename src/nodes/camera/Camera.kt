package sandbox.nodes

import org.joml.Vector2d
import org.joml.Vector3f
import org.joml.Matrix4f

import sandbox.materials.Shader

// Creates a perspective chase Camera which holds the view & projection matrices.
class Camera : Node {
    val target: Node
    val targetOffset: Vector3f
    val aspect: Float
    val fov: Float
    val zClip: ClosedRange<Float>
    val zoomRange: ClosedRange<Float>
    val pitchRange: ClosedRange<Float>

    var oldTargetPosition: Vector3f
    val targetPosition: Vector3f
        get() = Vector3f(target.position).add(targetOffset)

    val direction: Vector3f
        get() = Vector3f(position).sub(targetPosition).normalize()

    val right: Vector3f
        get() = Vector3f(direction).cross(0.0f, 1.0f, 0.0f).normalize()

    val up: Vector3f
        get() = Vector3f(right).cross(direction).normalize()

    val projection: Matrix4f
        get() = Matrix4f().perspective(
            fov, aspect, zClip.start, zClip.endInclusive
        )

    val view: Matrix4f
        get() = Matrix4f().lookAt(position, targetPosition, up)

    val projectionView: Matrix4f
        get() = Matrix4f(projection).mul(view)

    constructor(
        target: Node,
        aspect: Float,
        targetOffset: Vector3f = Vector3f(0.0f),
        position: Vector3f = Vector3f(0.0f),
        fov: Float = Math.toRadians(45.0).toFloat(),
        zClip: ClosedRange<Float> = 0.001f..100.0f,
        zoomRange: ClosedRange<Float>,
        pitchRange: ClosedRange<Float>
    ) : super() {
        model.translate(position)
        this.target = target
        this.aspect = aspect
        this.targetOffset = targetOffset
        this.fov = fov
        this.zClip = zClip
        this.zoomRange = zoomRange
        this.pitchRange = pitchRange
        this.oldTargetPosition = targetPosition
    }

    fun zoom(value: Float) {
        val zoomVec = direction.mul(value)
        val distanceToTarget = position.add(zoomVec).distance(targetPosition)

        if (distanceToTarget in zoomRange) {
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

        if (pitch in pitchRange) {
            rotate(
                angle = Math.toRadians(deltay).toFloat(),
                axis  = right
            )
        }
    }

    // Stores the projection * view matrix in a global uniform shared by all
    // shaders.
    // The `oldTargetPösition` purpose is to keep track of the target's moves.
    // This allows the distance between the camera and the target to remain the
    // same if the target moves. This is what makes this camera a chasing one.
    override fun update(f: () -> Unit) {
        super.update(f)

        model.translate(targetPosition.sub(oldTargetPosition))

        Shader.globalUniforms.setUniform(
            offset = 16,
            value  = projectionView
        )

        oldTargetPosition = targetPosition
    }
}

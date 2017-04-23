package sandbox.nodes

import org.joml.Vector3f
import org.joml.Matrix3f
import org.joml.Matrix4f

// A Node represent an element in the scene. It has a position in the world,
// and thus can be transformed (e.g. translation, rotation, scaling).
open class Node {

    // Handles temporary local transformations (e.g. rotate the Node over time).
    val tmpLocal: Matrix4f = Matrix4f()

    val model: Matrix4f = Matrix4f()

    val finalModel get() = Matrix4f().mul(model).mul(tmpLocal)

    val position get() = finalModel.getTranslation(Vector3f())

    val normalMatrix get() = Matrix3f(
        finalModel.invert().transpose()
    )

    open fun update() {}
}

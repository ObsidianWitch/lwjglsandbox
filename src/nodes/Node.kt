package sandbox.nodes

import org.joml.Vector3f
import org.joml.Matrix4f

// A Node represent an element in the scene. It has a position in the world,
// and thus can be transformed (e.g. translation, rotation, scaling).
abstract class Node {
    // This matrix is used to apply temporary transformations to the model
    // (e.g. modify the model over time).
    val tmpModel: Matrix4f = Matrix4f()

    val model: Matrix4f = Matrix4f()

    val finalModel: Matrix4f
        get() = Matrix4f().mul(tmpModel).mul(model)

    val position: Vector3f
        get() = finalModel.getTranslation(Vector3f())

    // Updates the node.
    abstract fun update(f: () -> Unit = {})
}

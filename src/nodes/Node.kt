package sandbox.nodes

import org.joml.Matrix4f

import sandbox.models.Mesh

// A Node represent an element in the scene. It has a position in the world,
// and thus can be transformed (e.g. translation, rotation, scaling). It can
// optionnaly contain a Mesh, in which case it becomes visible in the scene.
open class Node {
    protected var mesh: Mesh? = null

    // This matrix is used to apply temporary transformations to the model
    // (e.g. modify the model over time).
    protected val localMatrix: Matrix4f = Matrix4f()
    protected val modelMatrix: Matrix4f = Matrix4f()
    protected val finalModelMatrix: Matrix4f
        get() = Matrix4f().mul(localMatrix).mul(modelMatrix)


    // Updates the node. If a mesh is associated with the node, renders it by
    // binding the associated material (shader + uniforms).
    // Further instructions can be specified through the `f` parameter. This is
    // useful for example to set more uniforms which are not directly related
    // to the material (e.g. transformations).
    open fun update(f: () -> Unit = {}) {
        if (mesh == null) { f(); return }

        mesh?.render {
            f()
            material.shader.setUniform("model", finalModelMatrix)
        }
    }
}

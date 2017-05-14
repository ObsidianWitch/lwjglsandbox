package sandbox.nodes

import kotlin.properties.Delegates

import sandbox.models.Mesh

// A VisibleNode is a Node containing a mesh.
open class VisibleNode : Node() {
    var mesh: Mesh by Delegates.notNull()

    // Updates the node. If a mesh is associated with the node, renders it by
    // binding the associated material (shader + uniforms).
    // Further instructions can be specified through the `f` parameter. This is
    // useful for example to set more uniforms which are not directly related
    // to the material (e.g. transformations).
    override fun update() {
        super.update()

        mesh.material.nodeUniforms.model = finalModel
        mesh.material.nodeUniforms.normalMatrix = normalMatrix
        mesh.draw()
    }
}

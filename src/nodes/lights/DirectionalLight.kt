package sandbox.nodes

import org.joml.Vector3f
import org.joml.Vector4f

import sandbox.materials.Shader

// A directional light is a far away source of light (modeled as infinitely far
// away). All its light rays have the same direction (modeled as parallel).
class DirectionalLight : AmbientLight {
    var direction: Vector3f

    constructor(
        name: String, color: Vector4f, direction: Vector3f
    ) : super(name, color) {
        this.direction = direction
    }

    override fun update() {
        super.update()

        Shader.setSharedUniform("${name}.direction", direction)
    }
}

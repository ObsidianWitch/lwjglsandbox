package sandbox.nodes

import org.joml.Vector3f
import org.joml.Vector4f

import sandbox.materials.Shader

// A directional light is a far away source of light (modeled as infinitely far
// away). All its light rays have the same direction (modeled as parallel).
class DirectionalLight : Light {
    var direction: Vector3f

    constructor(
        offset: Long, color: Vector4f, direction: Vector3f
    ) : super(offset, color) {
        this.direction = direction
    }

    override fun update(f: () -> Unit) {
        super.update(f)

        Shader.lightsUniforms.setUniform(
            offset = offset + 16,
            value  = direction
        )
    }
}

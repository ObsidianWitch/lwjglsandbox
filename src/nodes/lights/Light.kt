package sandbox.nodes

import org.joml.Vector4f

import sandbox.materials.Shader

abstract class Light : Node {

    var color: Vector4f

    // Offset of this light in `Shader.lightsUniforms`
    protected val offset: Long

    constructor(offset: Long, color: Vector4f) : super() {
        this.offset = offset
        this.color = color
    }

    override fun update() {
        super.update()

        Shader.lightsUniforms.setUniform(
            offset = offset,
            value  = color
        )
    }
}

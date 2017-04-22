package sandbox.nodes

import org.joml.Vector4f

class AmbientLight : Light {
    constructor(offset: Long, color: Vector4f) : super(offset, color) {}
}

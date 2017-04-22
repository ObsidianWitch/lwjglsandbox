package sandbox.nodes

import org.joml.Vector4f

// Objects in a scene are almost never completely dark. There is usually some
// light coming from a distant source or due to reflections. Ambient lights are
// used to simulate this behaviour.
class AmbientLight : Light {
    constructor(offset: Long, color: Vector4f) : super(offset, color) {}
}

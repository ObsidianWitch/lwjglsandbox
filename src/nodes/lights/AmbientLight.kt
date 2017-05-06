package sandbox.nodes

import org.joml.Vector4f

import sandbox.materials.Shader

// Objects in a scene are almost never completely dark. There is usually some
// light coming from a distant source or due to reflections. Ambient lights are
// used to simulate this behaviour. AmbientLight is also used as the base class
// for other lights.
open class AmbientLight : Node {
    val name: String
    var color: Vector4f

    constructor(name: String, color: Vector4f) : super() {
        this.name = name
        this.color = color
    }

    override fun update() {
        super.update()

        Shader.setSharedUniform("${name}.color", color)
    }
}

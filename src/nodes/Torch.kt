package sandbox.nodes

import org.joml.*

import sandbox.materials.Phong
import sandbox.models.Mesh
import sandbox.models.Texture

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*

class Torch : Node {
    constructor() : super() {
        val staff = VisibleNode().apply {
            mesh = Mesh(
                path = "resources/staff/staff.obj",
                material = Phong().apply {
                    diffuseColor = Vector4f(0.35f, 0.2f, 0.1f, 1.0f)
                }
            )
        }
        children.add(staff)

        val pointLight = PointLight(
            name      = "pL",
            color     = Vector4f(0.9f, 0.6f, 0.2f, 1.0f),
            position  = Vector3f(0.0f, 2.0f, 0.0f),
            constant  = 0.1f,
            linear    = 0.1f,
            quadratic = 0.05f,
            frequency = 2.0f
        )
        children.add(pointLight)
    }
}

package sandbox.nodes

import org.joml.*

import sandbox.materials.Phong
import sandbox.models.Mesh
import sandbox.models.Texture2D

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*

class Lantern : Node {
    constructor() : super() {
        val lamppost = VisibleNode().apply {
            mesh = Mesh(
                path = "resources/lantern/lantern.obj",
                material = Phong().apply {
                    unshadedUniforms.diffuseTexture = Texture2D(
                        path = "resources/lantern/texture.png",
                        unit = GL_TEXTURE0
                    )
                }
            )
        }
        children.add(lamppost)

        val pointLight = PointLight(
            name      = "pL",
            color     = Vector4f(0.9f, 0.6f, 0.2f, 1.0f),
            position  = Vector3f(-0.6f, 2.0f, 0.0f),
            constant  = 0.2f,
            linear    = 0.1f,
            quadratic = 0.05f,
            frequency = 2.0f
        )
        children.add(pointLight)
    }
}

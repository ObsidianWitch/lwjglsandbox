package sandbox.nodes

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL13.*

import sandbox.materials.Skybox
import sandbox.models.Mesh
import sandbox.models.TextureCubeMap

class TwilightSky : VisibleNode {

    constructor() : super() {
        mesh = Mesh(
            path = "resources/skybox/skybox.obj",
            material = Skybox().apply {
                skyboxUniforms.backTexture = TextureCubeMap(
                    paths = listOf(
                        "resources/skybox/right.jpg",
                        "resources/skybox/left.jpg",
                        "resources/skybox/top.jpg",
                        "resources/skybox/bottom.jpg",
                        "resources/skybox/back.jpg",
                        "resources/skybox/front.jpg"
                    ),
                    unit = GL_TEXTURE0
                )

                skyboxUniforms.frontTexture = TextureCubeMap(
                    paths = listOf(
                        "resources/skybox/fog_right.png",
                        "resources/skybox/fog_left.png",
                        "resources/skybox/fog_left.png",
                        "resources/skybox/transparent.png",
                        "resources/skybox/fog_back.png",
                        "resources/skybox/fog_front.png"
                    ),
                    unit = GL_TEXTURE1
                )
            }
        )
    }

    // Rotates the front skybox over time.
    override fun update() {
        super.update()

        tmpLocal.identity().rotateY((0.02 * glfwGetTime()).toFloat())
    }
}

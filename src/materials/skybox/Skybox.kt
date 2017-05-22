package sandbox.materials

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.*

import sandbox.models.Texture

class Skybox : Material {
    val skyboxUniforms: SkyboxUniforms

    constructor() : super() {
        shaders.add(Shader().apply {
            add(GL_VERTEX_SHADER, "src/materials/skybox/Skybox.vs")
            add(GL_FRAGMENT_SHADER, "src/materials/skybox/Skybox.fs")
            link()
        })

        skyboxUniforms = SkyboxUniforms(shaders)
    }

    override fun use(f: () -> Unit) = super.use {
        glDisable(GL_CULL_FACE);
        glDepthFunc(GL_LEQUAL)

        skyboxUniforms.backTexture?.bind()
        skyboxUniforms.frontTexture?.bind()
        f()
        skyboxUniforms.backTexture?.unbind()
        skyboxUniforms.frontTexture?.unbind()

        glDepthFunc(GL_LESS)
        glEnable(GL_CULL_FACE);
    }
}

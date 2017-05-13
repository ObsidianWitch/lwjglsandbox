package sandbox.materials

import org.lwjgl.opengl.GL20.*

import sandbox.models.Texture

class Toon : Material {
    val shadedUniforms: ShadedUniforms
    val unshadedUniforms: UnshadedUniforms

    constructor() : super() {
        shaders.add(Shader().apply {
            add(GL_VERTEX_SHADER, "src/materials/general/Main.vs")
            add(
                GL_FRAGMENT_SHADER,
                "src/materials/general/Texture.fs",
                "#define SHADED"
            )
            add(GL_FRAGMENT_SHADER, "src/materials/shaded/Lights.fs")
            add(GL_FRAGMENT_SHADER, "src/materials/shaded/toon/Toon.fs")
            add(GL_FRAGMENT_SHADER, "src/materials/shaded/Shaded.fs")
            link()
        })

        shadedUniforms = ShadedUniforms(shaders)
        unshadedUniforms = UnshadedUniforms(shaders)
    }

    override fun use(f: () -> Unit) = super.use {
        unshadedUniforms.diffuseTexture?.bind()
        f()
        unshadedUniforms.diffuseTexture?.unbind()
    }
}

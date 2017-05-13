package sandbox.materials

import org.lwjgl.opengl.GL20.*

import sandbox.models.Texture

class Unshaded : Material {
    val unshadedUniforms: UnshadedUniforms

    constructor() : super() {
        shaders.add(Shader().apply {
            add(GL_VERTEX_SHADER, "src/materials/general/Main.vs")
            add(
            GL_FRAGMENT_SHADER,
                "src/materials/general/Texture.fs",
                "#define UNSHADED"
            )
            add(GL_FRAGMENT_SHADER, "src/materials/unshaded/Unshaded.fs")
            link()
        })

        unshadedUniforms = UnshadedUniforms(shaders)
    }

    override fun use(f: () -> Unit) = super.use {
        unshadedUniforms.diffuseTexture?.bind()
        f()
        unshadedUniforms.diffuseTexture?.unbind()
    }
}

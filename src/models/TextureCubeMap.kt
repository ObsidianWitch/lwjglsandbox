package sandbox.models

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL30.*

class TextureCubeMap : Texture {

    // Loads the images at `paths` as a cubemap texture.
    // Force the image to have 4 channels (RGBA).
    // Texture wrapping: clamp to edge.
    // Minifying filter: linear.
    // Magnifying filter: linear.
    constructor(paths: List<String>, unit: Int) : super(
            uid = paths.joinToString(),
            unit = unit
    ) {
        glBindTexture(GL_TEXTURE_CUBE_MAP, id)

        paths.forEachIndexed { i, path ->
            loadImage(path, GL_TEXTURE_CUBE_MAP_POSITIVE_X + i)
        }

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR)

        glBindTexture(GL_TEXTURE_CUBE_MAP, 0)
    }

    override fun bind() {
        super.bind()
        glBindTexture(GL_TEXTURE_CUBE_MAP, id)
    }

    override fun unbind() {
        super.unbind()
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0)
    }

}

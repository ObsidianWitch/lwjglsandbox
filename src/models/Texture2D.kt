package sandbox.models

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL30.*

class Texture2D : Texture {

    // Loads the image at `path` as a 2D texture.
    // Force the image to have 4 channels (RGBA).
    // Texture wrapping: repeat.
    // Minifying filter: linear mipmap, linear texel.
    // Magnifying filter: linear filtering (interpolates values from
    // neighbouring texels).
    constructor(path: String, unit: Int) : super(
        uid = path,
        unit = unit
    ) {
        glBindTexture(GL_TEXTURE_2D, id)

        loadImage(path, GL_TEXTURE_2D)

        glGenerateMipmap(GL_TEXTURE_2D)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        glBindTexture(GL_TEXTURE_2D, 0)
    }

    override fun bind() {
        super.bind()
        glBindTexture(GL_TEXTURE_2D, id)
    }

    override fun unbind() {
        super.unbind()
        glBindTexture(GL_TEXTURE_2D, 0)
    }

}

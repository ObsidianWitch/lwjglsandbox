package sandbox.models

import java.nio.IntBuffer

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.*

class Texture {
    companion object {
        private val loadedTextures: MutableList<Texture> = mutableListOf()
    }

    private val id: Int
    private val path: String

    constructor(path: String) {
        this.path = path

        // Only load the texture if it has not already been loaded.
        id = loadedTextures.filter { it.path == this.path }
                           .firstOrNull()
                          ?.id
           ?: load()
    }

    // Loads the image at `path` as a 2D texture.
    // Force the image to have 4 channels (RGBA).
    // Texture wrapping: repeat.
    // Minifying filter: nearest mipmap, nearest texel.
    // Magnifying filter: linear filtering (interpolates values from
    // neighbouring texels).
    fun load() : Int {
        val width = BufferUtils.createIntBuffer(1)
        val height = BufferUtils.createIntBuffer(1)
        val channels = BufferUtils.createIntBuffer(1)

        stbi_set_flip_vertically_on_load(true)
        val image = stbi_load(path, width, height, channels, 4)
        if (image == null) {
            throw RuntimeException("Texture $path could not be loaded.")
        }

        val id = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, id)
        glTexImage2D(
            GL_TEXTURE_2D,    // target
            0,                // manual mipmap level (0 base image level)
            GL_RGBA,          // texture internal format (channels)
            width.get(0),     // width
            height.get(0),    // height
            0,                // border (legacy)
            GL_RGBA,          // texel data format
            GL_UNSIGNED_BYTE, // texel data type
            image             // image data
        )
        glGenerateMipmap(GL_TEXTURE_2D)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        glBindTexture(GL_TEXTURE_2D, 0)
        stbi_image_free(image)

        return id
    }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    fun unbind() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }
}

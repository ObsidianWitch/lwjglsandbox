package sandbox.models

import java.nio.IntBuffer

import org.lwjgl.BufferUtils
import org.lwjgl.stb.STBImage.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL30.*

abstract class Texture {
    companion object {
        val loadedTextures: MutableList<Texture> = mutableListOf()
    }

    val unit: Int

    protected val id: Int

    // Unique string to identify the texture in `loadedTextures`.
    protected val uid: String

    constructor(uid: String, unit: Int) {
        assert(unit >= GL_TEXTURE0 && unit <= GL_TEXTURE31)
        this.unit = unit
        this.uid = uid

        // Only load the texture if it does not already exist.
        val loadedTex = loadedTextures.filter { it.uid == this.uid }
                           .firstOrNull()

        if (loadedTex != null) {
            id = loadedTex.id
        } else {
            id = glGenTextures()
            loadedTextures.add(this)
        }
    }

    protected fun loadImage(path: String, target: Int) {
        val width = BufferUtils.createIntBuffer(1)
        val height = BufferUtils.createIntBuffer(1)
        val channels = BufferUtils.createIntBuffer(1)

        val image = stbi_load(path, width, height, channels, 4)
                 ?: throw RuntimeException("Texture $path could not be loaded.")

        glTexImage2D(
            target,           // target
            0,                // manual mipmap level (0 base image level)
            GL_RGBA,          // texture internal format (channels)
            width.get(0),     // width
            height.get(0),    // height
            0,                // border (legacy)
            GL_RGBA,          // texel data format
            GL_UNSIGNED_BYTE, // texel data type
            image             // image data
        )
        stbi_image_free(image)
    }

    open fun bind() { glActiveTexture(unit) }
    open fun unbind() { glActiveTexture(unit) }
}


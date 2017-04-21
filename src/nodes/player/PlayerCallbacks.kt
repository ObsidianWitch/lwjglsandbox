package sandbox.nodes

import org.lwjgl.glfw.GLFW.*

import sandbox.ui.KeyCallback
import sandbox.ui.MouseButtonCallback

class PlayerCallbacks {
    private val player: Player

    constructor(player: Player) {
        this.player = player
    }

    val keyCallback: KeyCallback get() = {
        _, key, _, action, _ ->

        val pressed = (action != GLFW_RELEASE)

        when (key) {
            GLFW_KEY_W -> player.movements.forward  = pressed
            GLFW_KEY_S -> player.movements.backward = pressed
            GLFW_KEY_A -> player.movements.left     = pressed
            GLFW_KEY_D -> player.movements.right    = pressed
        }
    }

    val mouseButtonCallback: MouseButtonCallback get() = {
        _, button, action, _ ->
        player.movements.strafing = (button == GLFW_MOUSE_BUTTON_RIGHT)
                                 && (action == GLFW_PRESS)
    }
}

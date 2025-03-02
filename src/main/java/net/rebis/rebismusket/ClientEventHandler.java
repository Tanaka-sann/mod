package net.rebis.rebismusket;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {
    public static boolean isMousePressed = false;
    public static boolean isReloading = false;

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Post event) {
        if (event.getButton() == org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            isMousePressed = event.getAction() == org.lwjgl.glfw.GLFW.GLFW_PRESS;
        }
    }
}

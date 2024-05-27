package com.example.mymod;

import com.example.mymod.gui.StatsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ClientModInitializer implements ClientModInitializer {
    private static KeyBinding openStatsKey;

    @Override
    public void onInitializeClient() {
        openStatsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mymod.open_stats",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "category.mymod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openStatsKey.wasPressed()) {
                client.setScreen(new StatsScreen(Text.of("Player Stats")));
            }
        });
    }
}

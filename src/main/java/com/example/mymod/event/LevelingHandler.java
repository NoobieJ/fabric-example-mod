package com.example.mymod.event;

import com.example.mymod.PlayerStats;
import com.example.mymod.PlayerStatsComponent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;

public class LevelingHandler {
    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(world -> {
            for (PlayerEntity player : world.getPlayers()) {
                if (player.totalExperience > 0) {
                    PlayerStats stats = PlayerStatsComponent.get(player);
                    stats.addExperience((int) (player.totalExperience * (1 + stats.getIntelligence() * 0.1)));
                    player.addExperience(-player.totalExperience);
                    stats.applyEffects(player);
                }
            }
        });
    }
}

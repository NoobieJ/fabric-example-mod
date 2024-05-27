package com.example.mymod;

import com.example.mymod.network.NetworkHandler;
import com.example.mymod.event.LevelingHandler;
import com.example.mymod.component.PlayerStatsComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MyMod implements ModInitializer {
    public static final String MOD_ID = "mymod";
    public static final Identifier UPDATE_STATS = new Identifier(MOD_ID, "update_stats");

    @Override
    public void onInitialize() {
        // Register networking
        NetworkHandler.register();

        // Register leveling handler
        LevelingHandler.register();

        // Register player stats component
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
                PlayerStats oldStats = PlayerStatsComponent.get(oldPlayer);
                PlayerStats newStats = PlayerStatsComponent.get(newPlayer);
                newStats.fromNbt(oldStats.toNbt());
                newStats.applyEffects(newPlayer);
            });
        });

        // Handle gaining experience on block break as an example
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!world.isClient) {
                PlayerStats stats = PlayerStatsComponent.get(player);
                stats.addExperience(10);
                NetworkHandler.syncStats((ServerPlayerEntity) player);
                stats.applyEffects(player);
            }
        });
    }
}

package com.example.mymod.network;

import com.example.mymod.PlayerStats;
import com.example.mymod.PlayerStatsComponent;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier UPDATE_STATS = new Identifier("mymod", "update_stats");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_STATS, (server, player, handler, buf, responseSender) -> {
            NbtCompound nbt = buf.readNbt();
            if (nbt != null) {
                server.execute(() -> {
                    PlayerStats stats = PlayerStatsComponent.get(player);
                    stats.fromNbt(nbt);
                    stats.applyEffects(player);
                    syncStats(player);
                });
            }
        });
    }

    public static void syncStats(ServerPlayerEntity player) {
        PlayerStats stats = PlayerStatsComponent.get(player);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeNbt(stats.toNbt());
        ServerPlayNetworking.send(player, UPDATE_STATS, buf);
    }
}

package main.java.com.example.mymod.component;

import com.example.mymod.PlayerStats;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.component.ComponentProvider;
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerStatsComponent implements Component, EntitySyncedComponent {
    private final PlayerStats stats;

    public PlayerStatsComponent(PlayerEntity player) {
        this.stats = new PlayerStats();
    }

    public PlayerStats getStats() {
        return this.stats;
    }

    @Override
    public void fromTag(NbtCompound tag) {
        this.stats.fromNbt(tag);
    }

    @Override
    public NbtCompound toTag(NbtCompound tag) {
        return this.stats.toNbt(tag);
    }

    @Override
    public void sync() {
        EntitySyncedComponent.super.sync();
    }
}

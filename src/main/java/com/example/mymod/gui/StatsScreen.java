package com.example.mymod.gui;

import com.example.mymod.PlayerStats;
import com.example.mymod.PlayerStatsComponent;
import com.example.mymod.network.NetworkHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class StatsScreen extends Screen {
    private final PlayerStats stats;

    protected StatsScreen(Text title) {
        super(title);
        this.stats = PlayerStatsComponent.get(MinecraftClient.getInstance().player);
    }

    @Override
    protected void init() {
        int yBase = this.height / 4 + 24;
        int yStep = 24;

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, yBase, 200, 20, Text.of("Increase Strength"), button -> {
            stats.increaseStrength();
            syncStats();
        }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, yBase + yStep, 200, 20, Text.of("Increase Agility"), button -> {
            stats.increaseAgility();
            syncStats();
        }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, yBase + 2 * yStep, 200, 20, Text.of("Increase Intelligence"), button -> {
            stats.increaseIntelligence();
            syncStats();
        }));
    }

    private void syncStats() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeNbt(stats.toNbt());
        ClientPlayNetworking.send(NetworkHandler.UPDATE_STATS, buf);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, Text.of("Level: " + stats.getLevel()), this.width / 2, 40, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, Text.of("Experience: " + stats.getExperience() + " / " + stats.getExperienceForNextLevel()), this.width / 2, 60, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, Text.of("Points: " + stats.getPoints()), this.width / 2, 80, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, Text.of("Strength: " + stats.getStrength()), this.width / 2, 100, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, Text.of("Agility: " + stats.getAgility()), this.width / 2, 120, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, Text.of("Intelligence: " + stats.getIntelligence()), this.width / 2, 140, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}

package com.example.forgeauth;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class AuthEvents {

    private static final Map<UUID, Vec3> playerPositions = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerJoin(PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        UUID playerId = player.getUUID();

        if (!PlayerAuthHandler.isLoggedIn(player.getName().getString())) {
            player.displayClientMessage(Component.literal("Please log in using /auth login <password>"), false);
            playerPositions.put(playerId, player.position());
        }

            if (!PlayerAuthHandler.isLoggedIn(player.getName().getString())) {
              // If the player is not logged in, show a message and freeze their position
              player.displayClientMessage(Component.literal("Please log in using /auth login <password>"), false);
              playerPositions.put(playerId, player.position());
            }
    }

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID playerId = player.getUUID();
            String playerName = player.getName().getString();

            if (!PlayerAuthHandler.isLoggedIn(playerName)) {
                // Freeze the player
                Vec3 originalPos = playerPositions.get(playerId);
                if (originalPos != null) {
                    player.teleportTo(originalPos.x, originalPos.y, originalPos.z);
                    player.setDeltaMovement(Vec3.ZERO); // Prevent movement
                }
            } else {
                // Remove from frozen list if logged in
                playerPositions.remove(playerId);
            }
        }
    }
}

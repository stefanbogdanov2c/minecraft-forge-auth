package com.example.forgeauth;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Timer;
import java.util.TimerTask;

@Mod.EventBusSubscriber
public class AuthEvents {
    private final Timer timer = new Timer();

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity(); // Changed to `getEntity()`
        if (!PlayerAuthHandler.isRegistered(player.getName().getString())) {
            player.displayClientMessage(Component.literal("Please register using /auth register <password>"), false);
        } else {
            player.displayClientMessage(Component.literal("Please log in using /auth login <password>"), false);
        }

        // Kick player if they don't log in within a minute
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!PlayerAuthHandler.isLoggedIn(player.getName().getString())) {
                    player.connection.disconnect(Component.literal("You failed to log in.")); // Corrected
                }
            }
        }, 60000); // 1 minute
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) { // Corrected to `PlayerInteractEvent`
        ServerPlayer player = (ServerPlayer) event.getEntity(); // Changed to `getEntity()`
        if (!PlayerAuthHandler.isLoggedIn(player.getName().getString())) {
            event.setCanceled(true);
        }
    }
}

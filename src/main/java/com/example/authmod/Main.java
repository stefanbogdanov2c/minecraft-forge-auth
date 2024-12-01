package com.example.forgeauth;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("forgeauth")
public class Main {
    public static final String MOD_ID = "forgeauth";

    public Main() {
        // Register the event handlers
        MinecraftForge.EVENT_BUS.register(new AuthEvents());
        MinecraftForge.EVENT_BUS.addListener(this::onServerStart);
    }

    private void onServerStart(ServerStartingEvent event) {
        // Load data from auth.json
        Map<String, String> loadedData = AuthStorage.load();
        loadedData.forEach(PlayerAuthHandler::register);

        // Register the commands
        event.getServer().getCommands().getDispatcher()
            .register(AuthCommand.registerCommand());
        event.getServer().getCommands().getDispatcher()
            .register(AuthCommand.loginCommand());
        event.getServer().getCommands().getDispatcher()
            .register(AuthCommand.changePasswordCommand());
    }

}

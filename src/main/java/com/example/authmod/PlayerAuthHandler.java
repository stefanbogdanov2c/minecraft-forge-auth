package com.example.forgeauth;

import java.util.HashMap;
import java.util.Map;

public class PlayerAuthHandler {
    private static final Map<String, String> registeredPlayers = new HashMap<>();
    private static final Map<String, Boolean> loggedInPlayers = new HashMap<>();

    public static boolean isRegistered(String username) {
        return registeredPlayers.containsKey(username);
    }

    public static boolean register(String username, String password) {
        if (isRegistered(username)) {
            return false; // Player is already registered
        }
        registeredPlayers.put(username, password);
        return true;
    }

    public static boolean login(String username, String password) {
        if (!isRegistered(username) || !registeredPlayers.get(username).equals(password)) {
            return false; // Invalid login
        }
        loggedInPlayers.put(username, true);
        return true;
    }

    public static boolean isLoggedIn(String username) {
        return loggedInPlayers.getOrDefault(username, false);
    }

    public static void logout(String username) {
        loggedInPlayers.remove(username);
    }
}

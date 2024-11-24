package com.example.forgeauth;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class AuthCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> registerCommands() {
        return Commands.literal("auth")
            .then(Commands.literal("register")
                .then(Commands.argument("password", StringArgumentType.string())
                    .executes(context -> {
                        String username = context.getSource().getTextName();
                        String password = StringArgumentType.getString(context, "password");
                        if (PlayerAuthHandler.register(username, password)) {
                            context.getSource().sendSuccess(Component.literal("Registered successfully!"), false);
                        } else {
                            context.getSource().sendFailure(Component.literal("You are already registered!"));
                        }
                        return 1;
                    })
                )
            )
            .then(Commands.literal("login")
                .then(Commands.argument("password", StringArgumentType.string())
                    .executes(context -> {
                        String username = context.getSource().getTextName();
                        String password = StringArgumentType.getString(context, "password");
                        if (PlayerAuthHandler.login(username, password)) {
                            context.getSource().sendSuccess(Component.literal("Logged in successfully!"), false);
                        } else {
                            context.getSource().sendFailure(Component.literal("Invalid username or password!"));
                        }
                        return 1;
                    })
                )
            );
    }
}

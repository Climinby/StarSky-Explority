package com.climinby.starsky_e.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Commands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            ResearchCommand.registerResearchCommand(dispatcher, registryAccess);
        }));
    }
}

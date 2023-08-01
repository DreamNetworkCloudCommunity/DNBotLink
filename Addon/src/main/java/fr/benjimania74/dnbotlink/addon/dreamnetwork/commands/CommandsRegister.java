package fr.benjimania74.dnbotlink.addon.dreamnetwork.commands;

import be.alexandre01.dreamnetwork.api.commands.CommandsManager;

public class CommandsRegister {
    public static void register(CommandsManager commandsManager){
        commandsManager.addCommands(new Config("config"));
    }
}

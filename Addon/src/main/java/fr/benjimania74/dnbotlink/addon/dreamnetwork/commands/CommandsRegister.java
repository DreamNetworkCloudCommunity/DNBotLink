package fr.benjimania74.dnbotlink.addon.dreamnetwork.commands;

import be.alexandre01.dreamnetwork.api.commands.ICommandsManager;

public class CommandsRegister {
    public static void register(ICommandsManager commandsManager){
        commandsManager.addCommands(new Config("config"));
    }
}

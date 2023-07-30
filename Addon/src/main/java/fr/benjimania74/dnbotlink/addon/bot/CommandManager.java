package fr.benjimania74.dnbotlink.addon.bot;

import fr.benjimania74.dnbotlink.addon.bot.commands.*;
import fr.benjimania74.dnbotlink.addon.bot.commands.console.ConsoleLinkCommand;
import fr.benjimania74.dnbotlink.addon.bot.commands.console.ConsoleUnlinkCommand;
import fr.benjimania74.dnbotlink.addon.bot.commands.console.DisableConsoleCommand;
import fr.benjimania74.dnbotlink.addon.bot.commands.console.EnableConsoleCommand;
import fr.benjimania74.dnbotlink.addon.bot.commands.service.StartCommand;
import fr.benjimania74.dnbotlink.addon.bot.commands.service.StopCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    @Getter private static CommandManager instance;

    @Getter private final List<Command> commands = new ArrayList<>();

    public CommandManager(){
        commands.add(new StartCommand("start", "Start a Service", true));
        commands.add(new StopCommand("stop", "Stop a Service", true));
        commands.add(new EnableConsoleCommand("enableconsole", "Enable a Server's console on Discord", true));
        commands.add(new DisableConsoleCommand("disableconsole", "Disable a Server's console on Discord", true));
        commands.add(new ConsoleLinkCommand("consolelink", "Link a Server's Console with this Discord Channel", true));
        commands.add(new ConsoleUnlinkCommand("consoleunlink", "Unlink a Server's Console with this Discord Channel", true));
        instance = this;
    }

    public Command getCommand(String name){
        for(Command cmd : commands){
            if(cmd.getName().equalsIgnoreCase(name)){
                return cmd;
            }
        }
        return null;
    }
}

package fr.benjimania74.dnbotlink.addon.bot;

import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.ConsoleLinkCommand;
import fr.benjimania74.dnbotlink.addon.bot.commands.StartCommand;
import fr.benjimania74.dnbotlink.addon.bot.commands.StopCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    @Getter private static CommandManager instance;

    @Getter private final List<Command> commands = new ArrayList<>();

    public CommandManager(){
        commands.add(new StartCommand("start", "Start a Service", true));
        commands.add(new StopCommand("stop", "Stop a Service", true));
        commands.add(new ConsoleLinkCommand("consolelink", "Link a Server's Console with a Discord Channel", true));
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

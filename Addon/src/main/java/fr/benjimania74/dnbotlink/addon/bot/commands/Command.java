package fr.benjimania74.dnbotlink.addon.bot.commands;

import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.LinkedHashMap;

public abstract class Command {
    @Getter private final String name;
    @Getter private final String description;
    @Getter private final boolean permCommand;
    @Getter private final LinkedHashMap<String, ArgumentCompleter> argumentsCompleter;

    public Command(String name, String description, boolean permCommand, LinkedHashMap<String, ArgumentCompleter> argumentsCompleter){
        this.name = name;
        this.description = description;
        this.permCommand = permCommand;
        this.argumentsCompleter = argumentsCompleter;
    }

    public abstract void execute(SlashCommandInteractionEvent event);
}

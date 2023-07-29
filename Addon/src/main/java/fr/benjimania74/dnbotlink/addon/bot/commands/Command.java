package fr.benjimania74.dnbotlink.addon.bot.commands;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class Command {
    @Getter private final String name;
    @Getter private final String description;
    @Getter private final boolean permCommand;

    public Command(String name, String description, boolean permCommand){
        this.name = name;
        this.description = description;
        this.permCommand = permCommand;
    }

    public abstract void execute(SlashCommandInteractionEvent event);
}

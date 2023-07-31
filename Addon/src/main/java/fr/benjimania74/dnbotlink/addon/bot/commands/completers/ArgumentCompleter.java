package fr.benjimania74.dnbotlink.addon.bot.commands.completers;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Collection;

public abstract class ArgumentCompleter {
    @Getter private final String description;
    @Getter private final OptionType type;

    public ArgumentCompleter(String description, OptionType type){
        this.description = description;
        this.type = type;
    }
    public abstract Collection<Command.Choice> getCompleter(CommandAutoCompleteInteractionEvent event);
}

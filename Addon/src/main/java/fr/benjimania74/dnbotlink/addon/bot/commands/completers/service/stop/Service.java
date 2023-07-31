package fr.benjimania74.dnbotlink.addon.bot.commands.completers.service.stop;

import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Collection;
import java.util.stream.Collectors;

public class Service extends ArgumentCompleter {
    public Service(String description, OptionType type) {
        super(description, type);
    }

    @Override
    public Collection<Command.Choice> getCompleter(CommandAutoCompleteInteractionEvent event) {
        return AddonMain.getInstance().getServiceScreenReaders().keySet().stream()
                .filter(service -> service.getFullName().startsWith(event.getFocusedOption().getValue()))
                .map(service -> new Command.Choice(service.getFullName(), service.getFullName()))
                .collect(Collectors.toList());
    }
}

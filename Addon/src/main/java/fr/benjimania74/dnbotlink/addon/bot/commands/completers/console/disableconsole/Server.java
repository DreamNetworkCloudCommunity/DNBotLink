package fr.benjimania74.dnbotlink.addon.bot.commands.completers.console.disableconsole;

import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Collection;
import java.util.stream.Collectors;

public class Server extends ArgumentCompleter {
    public Server(String description, OptionType type) {
        super(description, type);
    }

    @Override
    public Collection<Command.Choice> getCompleter(CommandAutoCompleteInteractionEvent event) {
        return AddonMain.getInstance().getConfigManager().getLinkConfig().getConsoleLinks().stream()
                .filter(name -> name.startsWith(event.getFocusedOption().getValue()))
                .map(name -> new Command.Choice(name, name))
                .collect(Collectors.toList());
    }
}

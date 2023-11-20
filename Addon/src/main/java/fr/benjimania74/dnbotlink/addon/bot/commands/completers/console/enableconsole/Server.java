package fr.benjimania74.dnbotlink.addon.bot.commands.completers.console.enableconsole;

import be.alexandre01.dreamnetwork.api.service.IExecutor;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Server extends ArgumentCompleter {
    public Server(String description, OptionType type) {
        super(description, type);
    }

    @Override
    public Collection<Command.Choice> getCompleter(CommandAutoCompleteInteractionEvent event) {
        List<String> serversName = new ArrayList<>();
        for(IExecutor executor : AddonMain.getInstance().getCoreAPI().getContainer().getJVMExecutors()){
            if(!executor.isProxy()){serversName.add(executor.getFullName());}
        }
        return serversName.stream()
                .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                .map(word -> new Command.Choice(word, word))
                .collect(Collectors.toList());
    }
}

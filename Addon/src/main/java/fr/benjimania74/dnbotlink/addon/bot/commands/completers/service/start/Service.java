package fr.benjimania74.dnbotlink.addon.bot.commands.completers.service.start;

import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Service extends ArgumentCompleter {
    public Service(String description, OptionType type) {
        super(description, type);
    }

    @Override
    public Collection<Command.Choice> getCompleter(CommandAutoCompleteInteractionEvent event) {
        List<String> jvmNames = new ArrayList<>();
        for(IJVMExecutor jvm : AddonMain.getInstance().getCoreAPI().getContainer().getJVMExecutors()){
            if(jvm.getType().equals(IJVMExecutor.Mods.DYNAMIC) || (jvm.getType().equals(IJVMExecutor.Mods.STATIC) && jvm.getServices().isEmpty())){
                jvmNames.add(jvm.getFullName());
            }
        }

        return jvmNames.stream()
                .filter(name -> name.startsWith(event.getFocusedOption().getValue()))
                .map(name -> new Command.Choice(name, name))
                .collect(Collectors.toList())
                ;
    }
}

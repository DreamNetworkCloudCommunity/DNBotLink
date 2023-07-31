package fr.benjimania74.dnbotlink.addon.bot.listeners;

import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SlashCommandAutoCompleteListener extends ListenerAdapter {
    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        AddonMain main = AddonMain.getInstance();
        String command = event.getName();
        String option = event.getFocusedOption().getName();

        switch (command){
            case "start":
                if(option.equals("service")){
                    List<String> jvmNames = new ArrayList<>();
                    for(IJVMExecutor jvm : main.getCoreAPI().getContainer().getJVMExecutors()){
                        if(jvm.getType().equals(IJVMExecutor.Mods.DYNAMIC) || (jvm.getType().equals(IJVMExecutor.Mods.STATIC) && jvm.getServices().isEmpty())){
                            jvmNames.add(jvm.getFullName());
                        }
                    }

                    event.replyChoices(
                            jvmNames.stream()
                                    .filter(name -> name.startsWith(event.getFocusedOption().getValue()))
                                    .map(name -> new Command.Choice(name, name))
                                    .collect(Collectors.toList())
                    ).queue();
                }
                break;
            case "stop":
                if(option.equals("service")){
                    event.replyChoices(
                            main.getServiceScreenReaders().keySet().stream()
                                    .filter(service -> service.getFullName().startsWith(event.getFocusedOption().getValue()))
                                    .map(service -> new Command.Choice(service.getFullName(), service.getFullName()))
                                    .collect(Collectors.toList())
                    ).queue();
                }
                break;
            case "enableconsole":
                List<String> serversName = new ArrayList<>();
                for(IJVMExecutor jvmExecutor : main.getCoreAPI().getContainer().getJVMExecutors()){
                    if(!jvmExecutor.isProxy()){serversName.add(jvmExecutor.getFullName());}
                }

                event.replyChoices(
                        serversName.stream()
                                .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                                .map(word -> new Command.Choice(word, word))
                                .collect(Collectors.toList())
                ).queue();
                break;
            case "disableconsole":
                event.replyChoices(
                        main.getConfigManager().getLinkConfig().getConsoleLinks().stream()
                                .filter(name -> name.startsWith(event.getFocusedOption().getValue()))
                                .map(name -> new Command.Choice(name, name))
                                .collect(Collectors.toList())
                ).queue();
                break;
            case "consolelink":
                event.replyChoices(
                        main.getServiceScreenReaders().keySet().stream()
                                .filter(service -> service.getFullName().startsWith(event.getFocusedOption().getValue()))
                                .map(service -> new Command.Choice(service.getFullName(), service.getFullName()))
                                .collect(Collectors.toList())
                ).queue();
                break;
        }
    }
}

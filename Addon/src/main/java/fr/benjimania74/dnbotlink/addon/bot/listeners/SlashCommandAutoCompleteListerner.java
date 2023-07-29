package fr.benjimania74.dnbotlink.addon.bot.listeners;

import be.alexandre01.dreamnetwork.api.connection.core.communication.IClient;
import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SlashCommandAutoCompleteListerner extends ListenerAdapter {
    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        AddonMain main = AddonMain.getInstance();
        String command = event.getName();
        String option = event.getFocusedOption().getName();

        switch (command){
            case "start":
                if(option.equals("service")){
                    List<IJVMExecutor> jvmExecutors = main.getCoreAPI().getContainer().getJVMExecutors();

                    List<Command.Choice> options = jvmExecutors.stream()
                            .filter(jvm -> jvm.getFullName().startsWith(event.getFocusedOption().getValue()))
                            .map(jvm -> new Command.Choice(jvm.getFullName(), jvm.getFullName()))
                            .collect(Collectors.toList());
                    event.replyChoices(options).queue();
                }
                break;
            case "stop":
                if(option.equals("service")){
                    HashMap<String, IClient> clients = main.getCoreAPI().getClientManager().getClients();

                    List<Command.Choice> options = clients.values().stream()
                            .filter(client -> client.getJvmService().getFullName().startsWith(event.getFocusedOption().getValue()))
                            .map(client -> new Command.Choice(client.getJvmService().getFullName(), client.getJvmService().getFullName()))
                            .collect(Collectors.toList());

                    event.replyChoices(options).queue();
                }
                break;
            case "consolelink":
                List<String> toComplete = new ArrayList<>();
                for(IJVMExecutor jvmExecutor : main.getCoreAPI().getContainer().getJVMExecutors()){
                    if(!jvmExecutor.isProxy()){toComplete.add(jvmExecutor.getFullName());}
                }

                List<Command.Choice> options = toComplete.stream()
                        .filter(word -> word.startsWith(event.getFocusedOption().getValue()))
                        .map(word -> new Command.Choice(word, word))
                        .collect(Collectors.toList());
                event.replyChoices(options).queue();
                break;
        }
    }
}

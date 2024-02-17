package fr.benjimania74.dnbotlink.addon.bot.commands.service;

import be.alexandre01.dreamnetwork.api.service.IExecutor;
import fr.benjimania74.dnbotlink.addon.bot.utils.ScreenReader;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Objects;

public class StartCommand extends Command {
    private MessageEmbed embed;

    public StartCommand(String name, String description, boolean permCommand, LinkedHashMap<String, ArgumentCompleter> argumentsCompleter) {
        super(name, description, permCommand, argumentsCompleter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String service = Objects.requireNonNull(event.getOption("service")).getAsString();

        AddonMain main = AddonMain.getInstance();

        State state = State.NOT_FOUND;
        IExecutor executor;

        if(service.contains("/")){
            String serviceName = service.substring(service.lastIndexOf("/") + 1);
            String bundleName = service.substring(0, service.lastIndexOf("/"));
            executor = main.getCoreAPI().getContainer().getExecutor(serviceName, bundleName);
        }else{
            IExecutor[] executors = main.getCoreAPI().getContainer().getExecutorsFromName(service);
            if(executors.length == 0){
                executor = null;
            } else if(executors.length > 1){
                executor = null;
                state = State.MULTIPLE_POSSIBILITIES;}
            else{executor = executors[0];}
        }

        if(executor != null) {
            if (!executor.isConfig()) {
                state = State.NOT_CONFIG;
            } else if (!executor.getServices().isEmpty() && executor.getType().equals(IExecutor.Mods.STATIC)) {
                state = State.ALREADY_STARTED;
            } else {
                state = State.STARTED;
            }
        }

        switch (state){
            case STARTED:
                embed = new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Service Started")
                        .setDescription("The Service **" + service + "** has been Started")
                        .build();
                break;
            case ALREADY_STARTED:
                embed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Already Started")
                        .setDescription("**" + service + "** is already started and is not dynamic")
                        .build();
                break;
            case NOT_FOUND:
                embed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Service Not Found")
                        .setDescription("The Service **" + service + "** has not been found")
                        .build();
                break;
            case NOT_CONFIG:
                embed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Service Not Config")
                        .setDescription("The Service **" + service + "** has not been configured")
                        .build();
                break;
            case MULTIPLE_POSSIBILITIES:
                embed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Service must be more specific")
                        .setDescription("**" + service + "** has multiple service's possibilities")
                        .build();
        }

        State finalState = state;
        event.replyEmbeds(embed).queue(interactionHook -> {
            if(finalState != State.STARTED){return;}
            interactionHook.editOriginal("").queue(message -> {
                executor.startService().whenStart(s -> {
                    if(executor.isProxy() || !main.getConfigManager().getLinkConfig().getConsoleLinks().contains(executor.getFullName())){return;}
                    message.createThreadChannel(s.getFullName() + "'s Console").queue(threadChannel -> {
                        if(!main.getServiceScreenReaders().containsKey(s)){
                            ScreenReader sr = new ScreenReader(s);
                            sr.addChannel(threadChannel);
                            main.getServiceScreenReaders().put(s, sr);
                        }else {
                            main.getServiceScreenReaders().get(s).addChannel(threadChannel);
                        }
                    });
                });
            });
        });
    }

    public enum State {
        NOT_CONFIG,
        NOT_FOUND,
        ALREADY_STARTED,
        STARTED,
        MULTIPLE_POSSIBILITIES
    }
}

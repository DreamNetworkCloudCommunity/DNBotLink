package fr.benjimania74.dnbotlink.addon.bot.commands.service;

import be.alexandre01.dreamnetwork.api.service.ExecutorCallbacks;
import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.LinkedHashMap;

public class StartCommand extends Command {
    private MessageEmbed embed;

    public StartCommand(String name, String description, boolean permCommand, LinkedHashMap<String, ArgumentCompleter> argumentsCompleter) {
        super(name, description, permCommand, argumentsCompleter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String service = event.getOption("service").getAsString();

        AddonMain main = AddonMain.getInstance();
        IJVMExecutor jvm = main.getCoreAPI().getContainer().tryToGetJVMExecutor(service);

        States states;
        if(jvm == null){
            states = States.NOT_FOUND;
        }else{
            if(!jvm.isConfig()){states = States.NOT_CONFIG;}
            else if(!jvm.getServices().isEmpty() && jvm.getType().equals(IJVMExecutor.Mods.STATIC)){states = States.ALREADY_STARTED;}
            else {states = States.STARTED;}
        }

        switch (states){
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
        }

        event.replyEmbeds(embed).queue(interactionHook -> {
            if(states != States.STARTED){return;}
            interactionHook.editOriginal("").queue(message -> {
                jvm.startServer().whenStart(new ExecutorCallbacks.ICallbackStart() {
                    @Override
                    public void whenStart(IService s) {
                        if(jvm.isProxy() || !main.getConfigManager().getLinkConfig().getConsoleLinks().contains(service)){return;}
                        message.createThreadChannel(s.getFullName() + "'s Console").queue(threadChannel -> {
                            main.getServiceScreenReaders().get(s).getChannels().add(threadChannel);
                        });
                    }
                });
            });
        });
    }

    public enum States {
        NOT_CONFIG,
        NOT_FOUND,
        ALREADY_STARTED,
        STARTED
    }
}

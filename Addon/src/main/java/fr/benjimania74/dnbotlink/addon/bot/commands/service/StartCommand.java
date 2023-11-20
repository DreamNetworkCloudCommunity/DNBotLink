package fr.benjimania74.dnbotlink.addon.bot.commands.service;

import be.alexandre01.dreamnetwork.api.service.ExecutorCallbacks;
import be.alexandre01.dreamnetwork.api.service.IExecutor;
import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
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
        IExecutor executor = main.getCoreAPI().getContainer().tryToGetJVMExecutor(service).orElse(null);

        States states;
        if(executor == null){states = States.NOT_FOUND;}
        else{
            if(!executor.isConfig()){
                states = States.NOT_CONFIG;
            } else if(!executor.getServices().isEmpty() && executor.getType().equals(IExecutor.Mods.STATIC)) {
                states = States.ALREADY_STARTED;
            } else {
                states = States.STARTED;
            }
        }
        /*Optional<IJVMExecutor> optionalIJVMExecutor = main.getCoreAPI().getContainer().tryToGetJVMExecutor(service);


        AtomicReference<IJVMExecutor> arJVM = new AtomicReference<>(null);
        optionalIJVMExecutor.ifPresent(jvm -> {
            System.out.println("présent !");
        });*/

        /*if(arJVM.get() == null){
            states = States.NOT_FOUND;
        }else{
            System.out.println("a");
            IJVMExecutor jvm = arJVM.get();
            System.out.println("b");
            if(!jvm.isConfig()){states = States.NOT_CONFIG;}
            else if(!jvm.getServices().isEmpty() && jvm.getType().equals(IJVMExecutor.Mods.STATIC)){states = States.ALREADY_STARTED;}
            else {states = States.STARTED;}
            System.out.println("d");
        }*/

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

        States finalStates = states;
        event.replyEmbeds(embed).queue(interactionHook -> {
            if(finalStates != States.STARTED){return;}
            interactionHook.editOriginal("").queue(message -> {
                executor.startServers(1).whenStart(new ExecutorCallbacks.ICallbackStart() {
                    @Override
                    public void whenStart(IService s) {
                        if(executor.isProxy() || !main.getConfigManager().getLinkConfig().getConsoleLinks().contains(service)){return;}
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

package fr.benjimania74.dnbotlink.addon.bot.commands.service;

import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import fr.benjimania74.dnbotlink.addon.utils.service.StopService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.LinkedHashMap;

public class StopCommand extends Command {

    public StopCommand(String name, String description, boolean permCommand, LinkedHashMap<String, ArgumentCompleter> argumentsCompleter) {
        super(name, description, permCommand, argumentsCompleter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String service = event.getOption("service").getAsString();
        StopService.States states = StopService.stop(service);

        event.deferReply().queue();

        MessageEmbed embed = null;
        switch (states){
            case STOPPED:
                embed = new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Service Stopped")
                        .setDescription("The Service **" + service + "** has been Stopped")
                        .build();
                break;
            case NOT_STARTED:
                embed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Service Not Started")
                        .setDescription("The Service **" + service + "** is not Started")
                        .build();
                break;
        }
        event.getHook().sendMessageEmbeds(embed).queue();
    }
}

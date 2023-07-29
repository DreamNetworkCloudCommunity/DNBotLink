package fr.benjimania74.dnbotlink.addon.bot.commands;

import fr.benjimania74.dnbotlink.addon.utils.service.StartService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class StartCommand extends Command {
    public StartCommand(String name, String description, boolean permCommand) {
        super(name, description, permCommand);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String service = event.getOption("service").getAsString();
        StartService.States states = StartService.start(service);

        MessageEmbed embed = null;
        switch (states){
            case STARTED:
                embed = new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Service Started")
                        .setDescription("The Service **" + service + "** has been Started")
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
        event.replyEmbeds(embed).queue();
    }
}

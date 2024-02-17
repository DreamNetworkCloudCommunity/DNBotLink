package fr.benjimania74.dnbotlink.addon.bot.commands.console;

import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.LinkedHashMap;

public class ConsoleUnlinkCommand extends Command {
    private boolean found = false;

    public ConsoleUnlinkCommand(String name, String description, boolean permCommand, LinkedHashMap<String, ArgumentCompleter> argumentsCompleter) {
        super(name, description, permCommand, argumentsCompleter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        MessageChannelUnion channel = event.getChannel();
        if(!channel.getType().isThread()){
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("Not a Console")
                            .setDescription("This Channel is not Linked with a Console")
                            .build()
            ).queue();
        }

        ThreadChannel tc = channel.asThreadChannel();

        AddonMain main = AddonMain.getInstance();
        main.getServiceScreenReaders().forEach((service, screenReader) -> {
            for(ThreadChannel c : screenReader.getChannels()){
                if(c.getId().equals(tc.getId())){
                    screenReader.removeChannel(c);
                    event.replyEmbeds(
                            new EmbedBuilder()
                                    .setColor(Color.GREEN)
                                    .setTitle("Console Unlinked")
                                    .setDescription("This Channel is now unlinked with the **" + service.getFullName() + "**'s Console")
                                    .build()
                    ).queue();
                    found = true;
                }
            }
        });

        if(found){return;}
        event.replyEmbeds(
                new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Not a Console")
                        .setDescription("This Channel is not Linked with a Console")
                        .build()
        ).queue();
    }
}

package fr.benjimania74.dnbotlink.addon.bot.commands;

import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsoleLinkCommand extends Command {
    public ConsoleLinkCommand(String name, String description, boolean permCommand) {
        super(name, description, permCommand);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String server = event.getOption("server").getAsString();
        AddonMain main = AddonMain.getInstance();
        IJVMExecutor jvmExecutor = main.getCoreAPI().getContainer().tryToGetJVMExecutor(server);
        if(jvmExecutor == null || jvmExecutor.isProxy()){
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("Invalid Server")
                            .setDescription("**" + server + "** is not a server")
                            .build()
            ).queue();
            return;
        }
        HashMap<String, List<String>> consoleLinks = main.getConfigManager().getLinkConfig().getConsoleLinks();
        String channelID = event.getChannel().getId();
        List<String> channels = consoleLinks.getOrDefault(jvmExecutor.getFullName(), new ArrayList<>());
        if(!channels.contains(channelID)) {
            channels.add(channelID);
            consoleLinks.put(jvmExecutor.getFullName(), channels);
            main.getConfigManager().saveLinkConfig();
        }
        event.replyEmbeds(
                new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Console Linked")
                        .setDescription("The **" + server + "**" + (server.endsWith("s") ? "'" : "'s") + " Console has been linked with this Channel")
                        .build()
        ).queue();
    }
}

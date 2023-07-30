package fr.benjimania74.dnbotlink.addon.bot.commands.console;

import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.List;

public class DisableConsoleCommand extends Command {
    public DisableConsoleCommand(String name, String description, boolean permCommand) {
        super(name, description, permCommand);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String server = event.getOption("server").getAsString();
        AddonMain main = AddonMain.getInstance();

        List<String> linkedServer = main.getConfigManager().getLinkConfig().getConsoleLinks();
        if(!linkedServer.contains(server)){
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("Not Enabled")
                            .setDescription("**" + server + "**" + (server.endsWith("s") ? "'" :"'s") + " Console is not Enabled on Discord")
                            .build()
            ).queue();
            return;
        }
        linkedServer.remove(server);
        main.getConfigManager().saveLinkConfig();
        event.replyEmbeds(
                new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Console Disabled")
                        .setDescription("**" + server + "**" + (server.endsWith("s") ? "'" : "'s") + " Console is now Disabled on Discord")
                        .build()
        ).queue();
    }
}

package fr.benjimania74.dnbotlink.addon.bot.commands.console;

import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;

public class EnableConsoleCommand extends Command {
    public EnableConsoleCommand(String name, String description, boolean permCommand, LinkedHashMap<String, ArgumentCompleter> argumentsCompleter) {
        super(name, description, permCommand, argumentsCompleter);
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
        List<String> linkedServer = main.getConfigManager().getLinkConfig().getConsoleLinks();
        if(!linkedServer.contains(server)){
            linkedServer.add(server);
            main.getConfigManager().saveLinkConfig();
        }
        event.replyEmbeds(
                new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Console Enabled")
                        .setDescription("**" + server + "**" + (server.endsWith("s") ? "'" : "'s") + " Console is now Enabled on Discord")
                        .build()
        ).queue();
    }
}

package fr.benjimania74.dnbotlink.addon.bot.commands.console;

import be.alexandre01.dreamnetwork.api.service.IExecutor;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class EnableConsoleCommand extends Command {
    public EnableConsoleCommand(String name, String description, boolean permCommand, LinkedHashMap<String, ArgumentCompleter> argumentsCompleter) {
        super(name, description, permCommand, argumentsCompleter);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String server = Objects.requireNonNull(event.getOption("server")).getAsString();
        AddonMain main = AddonMain.getInstance();

        IExecutor executor;

        if(server.contains("/")){
            String serverName = server.substring(server.lastIndexOf("/") + 1);
            String bundleName = server.substring(0, server.lastIndexOf("/"));
            executor = main.getCoreAPI().getContainer().getExecutor(serverName, bundleName);
            if(executor == null || executor.isProxy()){
                event.replyEmbeds(
                        new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("Invalid Server")
                                .setDescription("**" + server + "** is not a server")
                                .build()
                ).queue();
                return;
            }
        }else{
            IExecutor[] executors = main.getCoreAPI().getContainer().getExecutorsFromName(server);
            if(executors == null || executors.length == 0){
                event.replyEmbeds(
                        new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("Invalid Server")
                                .setDescription("**" + server + "** is not a server")
                                .build()
                ).queue();
                return;
            }
            if(executors.length > 1){
                event.replyEmbeds(
                        new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("Server must be more specific")
                                .setDescription("**" + server + "** has multiple server's possibilities")
                                .build()
                ).queue();
                return;
            }
            executor = executors[0];
        }


        List<String> linkedServer = main.getConfigManager().getLinkConfig().getConsoleLinks();
        if(!linkedServer.contains(executor.getFullName())){
            linkedServer.add(executor.getFullName());
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

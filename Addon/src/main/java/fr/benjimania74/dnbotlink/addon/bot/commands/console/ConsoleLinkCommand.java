package fr.benjimania74.dnbotlink.addon.bot.commands.console;

import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.ThreadChannelAction;

import java.awt.*;

public class ConsoleLinkCommand extends Command {
    public ConsoleLinkCommand(String name, String description, boolean permCommand) {
        super(name, description, permCommand);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String runningServer = event.getOption("server").getAsString();
        AddonMain main = AddonMain.getInstance();
        IService service = main.getServiceScreenReaders().keySet().stream().filter(s -> runningServer.equals(s.getFullName())).findFirst().orElse(null);

        if(service == null){
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("Invalid Server")
                            .setDescription("**" + runningServer + "** is not running")
                            .build()
            ).queue();
            return;
        }

        event.replyEmbeds(
                new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setTitle("Console Linked")
                        .setDescription("The **" + runningServer + "**" + (runningServer.endsWith("s") ? "'" : "'s") + " Console has been temporary linked")
                        .build()
        ).queue(interactionHook -> {
            interactionHook.editOriginal("").queue(message -> {
                ThreadChannelAction tca = message.createThreadChannel(runningServer + (runningServer.endsWith("s") ? "'" : "'s") + " Console");
                tca.queue(threadChannel -> {
                    main.getServiceScreenReaders().get(service).getChannels().add(threadChannel);
                });
            });
        });
    }
}

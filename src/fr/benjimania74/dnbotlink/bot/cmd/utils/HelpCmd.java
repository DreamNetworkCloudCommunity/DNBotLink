package fr.benjimania74.dnbotlink.bot.cmd.utils;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import fr.benjimania74.dnbotlink.bot.BotMain;
import fr.benjimania74.dnbotlink.bot.cmd.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class HelpCmd extends Command {
    public HelpCmd(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(TextChannel channel, DNCoreAPI coreAPI, Message message) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Help Command")
                .setDescription("Here's the differents disponibles commands")
                .setFooter(getAddonName() + " by benjimania74", BotMain.instance.jda.getSelfUser().getAvatarUrl());

        BotMain.instance.getCommands().forEach((name, cmd) -> eb.addField(name, cmd.getDescription(), true));

        channel.sendMessageEmbeds(eb.build()).queue();
    }
}

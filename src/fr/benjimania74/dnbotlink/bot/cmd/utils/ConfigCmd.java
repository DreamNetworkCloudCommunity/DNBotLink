package fr.benjimania74.dnbotlink.bot.cmd.utils;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import fr.benjimania74.dnbotlink.bot.cmd.Command;
import fr.benjimania74.dnbotlink.bot.cmd.utils.config.ConfigCommandRegister;
import fr.benjimania74.dnbotlink.bot.cmd.utils.config.ConfigCommands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.HashMap;

public class ConfigCmd extends Command {
    public static HashMap<String, ConfigCommands> cmd = new HashMap<>();
    public ConfigCmd(String name, String description) {super(name, description);}

    @Override
    public void execute(TextChannel channel, DNCoreAPI coreAPI, Message message) {
        new ConfigCommandRegister();
        EmbedBuilder help = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(getName() + " help");
        cmd.values().forEach(configCommands -> help.addField(configCommands.getName(), configCommands.getDescription(), true));

        String content = message.getContentRaw();
        String[] args;
        try{
            args = content.substring(getPrefix().length() + getName().length() + 1).split(" ");
        }catch (Exception e){channel.sendMessageEmbeds(help.build()).queue();return;}

        if(cmd.containsKey(args[0])){cmd.get(args[0]).execute(channel, args);return;}
        channel.sendMessageEmbeds(help.build()).queue();
    }
}

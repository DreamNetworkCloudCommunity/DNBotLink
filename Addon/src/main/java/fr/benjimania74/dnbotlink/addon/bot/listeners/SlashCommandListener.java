package fr.benjimania74.dnbotlink.addon.bot.listeners;

import fr.benjimania74.dnbotlink.addon.bot.CommandManager;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String cmd = event.getName();

        Command command = CommandManager.getInstance().getCommand(cmd);
        if(command == null){return;}

        if(!isExecuteCommand(event, command)) {
            event.replyEmbeds(
                    new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("Permission Denied")
                            .setDescription("You don't have the permission to use this command")
                            .build()
            ).queue();
            return;
        }

        command.execute(event);
    }

    private boolean isExecuteCommand(SlashCommandInteractionEvent event, Command command) {
        boolean executeCommand = true;

        if(command.isPermCommand() && !AddonMain.getInstance().getConfigManager().getBotConfig().getPermRole().equalsIgnoreCase("everyone")){
            Guild guild = event.getGuild();
            Member sender = event.getMember();
            Role permRole = null;
            assert guild != null;
            for(Role r : guild.getRoles()){
                if(r.getId().equals(AddonMain.getInstance().getConfigManager().getBotConfig().getPermRole())){
                    permRole = r;
                    break;
                }
            }
            executeCommand = (permRole != null && sender.getRoles().contains(permRole));
        }
        return executeCommand;
    }
}

package fr.benjimania74.dnbotlink.addon.bot.listeners;

import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.CommandManager;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandAutoCompleteListener extends ListenerAdapter {
    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        AddonMain main = AddonMain.getInstance();
        String command = event.getName();
        String option = event.getFocusedOption().getName();

        event.replyChoices(CommandManager.getInstance().getCommand(command).getArgumentsCompleter().get(option).getCompleter(event)).queue();
    }
}

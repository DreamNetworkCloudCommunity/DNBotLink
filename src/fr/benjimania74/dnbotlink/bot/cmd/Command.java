package fr.benjimania74.dnbotlink.bot.cmd;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import fr.benjimania74.dnbotlink.Main;
import fr.benjimania74.dnbotlink.bot.BotConfig;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public abstract class Command {
    String name;
    String description;

    public Command(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getPrefix(){
        return BotConfig.getInstance().getPrefix();
    }
    public String getAddonName(){
        return Main.addonName;
    }

    public abstract void execute(TextChannel channel, DNCoreAPI coreAPI, Message message);
}

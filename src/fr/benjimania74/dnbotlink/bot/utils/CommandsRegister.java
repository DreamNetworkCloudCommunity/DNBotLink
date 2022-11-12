package fr.benjimania74.dnbotlink.bot.utils;

import fr.benjimania74.dnbotlink.bot.BotMain;
import fr.benjimania74.dnbotlink.bot.cmd.utils.*;
import fr.benjimania74.dnbotlink.bot.cmd.services.StartCmd;
import fr.benjimania74.dnbotlink.bot.cmd.services.StopCmd;

public class CommandsRegister {
    public void register(){
        BotMain.instance.registerCommand(new StartCmd("start", "Start services command"),
                new StopCmd("stop", "Stop services command"),
                new HelpCmd("help", "See all commands"),
                new LinkCmd("link", "Link a console to Discord"),
                new ListCmd("list", "See all services"),
                new ConfigCmd("config", "Configure the bot")
        );
    }
}

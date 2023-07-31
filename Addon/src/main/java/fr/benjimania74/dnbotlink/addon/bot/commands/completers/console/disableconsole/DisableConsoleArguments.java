package fr.benjimania74.dnbotlink.addon.bot.commands.completers.console.disableconsole;

import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.LinkedHashMap;

public class DisableConsoleArguments {
    public static LinkedHashMap<String, ArgumentCompleter> getArguments(){
        LinkedHashMap<String, ArgumentCompleter> arguments = new LinkedHashMap<>();
        arguments.put("server", new Server("Server's Name or Bundle to Disable Console on Discord", OptionType.STRING));
        return arguments;
    }
}

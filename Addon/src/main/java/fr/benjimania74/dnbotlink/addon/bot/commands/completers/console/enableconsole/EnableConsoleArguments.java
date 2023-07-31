package fr.benjimania74.dnbotlink.addon.bot.commands.completers.console.enableconsole;

import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.LinkedHashMap;

public class EnableConsoleArguments {
    public static LinkedHashMap<String, ArgumentCompleter> getArguments(){
        LinkedHashMap<String, ArgumentCompleter> arguments = new LinkedHashMap<>();
        arguments.put("server", new Server("Server's Name or Bundle to Enable Console on Discord", OptionType.STRING));
        return arguments;
    }
}

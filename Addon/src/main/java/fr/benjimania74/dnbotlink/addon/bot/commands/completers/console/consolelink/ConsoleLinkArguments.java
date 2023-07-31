package fr.benjimania74.dnbotlink.addon.bot.commands.completers.console.consolelink;

import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.LinkedHashMap;

public class ConsoleLinkArguments {
    public static LinkedHashMap<String, ArgumentCompleter> getArguments(){
        LinkedHashMap<String, ArgumentCompleter> arguments = new LinkedHashMap<>();
        arguments.put("server", new Server("Servers Name or Bundle to link the Console with", OptionType.STRING));
        return arguments;
    }
}

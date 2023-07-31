package fr.benjimania74.dnbotlink.addon.bot.commands.completers.service.start;

import fr.benjimania74.dnbotlink.addon.bot.commands.completers.ArgumentCompleter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.LinkedHashMap;

public class StartArguments {
    public static LinkedHashMap<String, ArgumentCompleter> getArguments(){
        LinkedHashMap<String, ArgumentCompleter> arguments = new LinkedHashMap<>();
        arguments.put("service", new Service("Service's Name or Bundle", OptionType.STRING));
        return arguments;
    }
}

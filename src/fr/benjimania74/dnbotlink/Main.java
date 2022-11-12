package fr.benjimania74.dnbotlink;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import be.alexandre01.dreamnetwork.api.addons.Addon;
import be.alexandre01.dreamnetwork.api.addons.DreamExtension;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.configmanager.Config;
import fr.benjimania74.dnbotlink.bot.BotConfig;
import fr.benjimania74.dnbotlink.bot.BotMain;
import fr.benjimania74.dnbotlink.utils.CustomResponse;
import fr.benjimania74.dnbotlink.registers.CommandsRegister;
import fr.benjimania74.dnbotlink.registers.ListenerRegister;
import fr.benjimania74.dnbotlink.utils.ServiceAutoStarter;

public class Main extends DreamExtension {
    public static DNCoreAPI coreAPI;
    public static String addonName;
    public static String addonVersion;

    @Override
    public void onLoad() {
        super.onLoad();

        registerPluginToServers(this);

        addonName = getAddon().getDreamyName();
        addonVersion = getAddon().getVersion();
        new Config(addonName);
        new BotMain();
        Console.print(Colors.YELLOW + "[" + Colors.GREEN + addonName + Colors.YELLOW + "] " + Colors.CYAN + "The Plugin is Loaded");
    }

    @Override
    public void start() {
        super.start();
        coreAPI = getDnCoreAPI();
        new CommandsRegister(coreAPI);
        new ListenerRegister(coreAPI);

        coreAPI.getGlobalResponses().add(new CustomResponse());
        Console.print(Colors.YELLOW + "[" + Colors.GREEN + addonName + Colors.YELLOW + "] " + Colors.CYAN + "The Plugin is Started");
        new ServiceAutoStarter();
    }

    @Override
    public void stop() {
        super.stop();
        BotConfig.getInstance().save();
        Console.print(Colors.YELLOW + "[" + Colors.GREEN + addonName + Colors.YELLOW + "] " + Colors.CYAN + "The Plugin is Stopped");
    }

    public Main(Addon addon){super(addon);}
}

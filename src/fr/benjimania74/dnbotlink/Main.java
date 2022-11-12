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
import fr.benjimania74.dnbotlink.utils.FilesManager;
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
        new FilesManager();
        if(!new BotMain().create()){Console.print("The Bot can't be Started, you will be able to use only the Console's Commands");}
        Console.print(Colors.YELLOW + "[" + Colors.GREEN + addonName + Colors.YELLOW + "] " + Colors.CYAN + "The Plugin is Loaded");
    }

    @Override
    public void start() {
        super.start();
        coreAPI = getDnCoreAPI();
        if(!CommandsRegister.register(coreAPI)){Console.print(Colors.RED + "Can't register the Commands, the Addon will not be able to be used");}
        if(!ListenerRegister.register(coreAPI)){Console.print(Colors.RED + "Can't register the listeners, the Addon will not be able to be used correctly");}

        Console.print(Colors.YELLOW + "[" + Colors.GREEN + addonName + Colors.YELLOW + "] " + Colors.CYAN + "The Plugin is Started");
        new ServiceAutoStarter();

        // InstallFile.installPaper("./paper-1.8.8.jar", "1.8.8"); <- INSTALL PAPER SPIGOT 1.8.8

        coreAPI.getGlobalResponses().add(new CustomResponse());
    }

    @Override
    public void stop() {
        super.stop();
        BotConfig.getInstance().save();
        Console.print(Colors.YELLOW + "[" + Colors.GREEN + addonName + Colors.YELLOW + "] " + Colors.CYAN + "The Plugin is Stopped");
    }

    public Main(Addon addon){super(addon);}
}

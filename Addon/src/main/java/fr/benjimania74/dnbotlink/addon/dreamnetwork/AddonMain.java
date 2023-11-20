package fr.benjimania74.dnbotlink.addon.dreamnetwork;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import be.alexandre01.dreamnetwork.api.addons.Addon;
import be.alexandre01.dreamnetwork.api.addons.DreamExtension;
import be.alexandre01.dreamnetwork.api.console.Console;
import be.alexandre01.dreamnetwork.api.console.IConsoleReader;
import be.alexandre01.dreamnetwork.api.console.colors.Colors;
import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.bot.BotMain;
import fr.benjimania74.dnbotlink.addon.bot.utils.ScreenReader;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.commands.CommandsRegister;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.listeners.EventsRegister;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.utils.config.ConfigManager;
import lombok.Getter;

import java.util.HashMap;

public class AddonMain extends DreamExtension {
    @Getter private static AddonMain instance;

    @Getter private DNCoreAPI coreAPI;
    @Getter private ConfigManager configManager;

    @Getter private HashMap<IService, ScreenReader> serviceScreenReaders = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
        //registerPluginToServers(this);
    }

    @Override
    public void start() {
        configManager = new ConfigManager();
        coreAPI = getDnCoreAPI();
        new BotMain();

        CommandsRegister.register(coreAPI.getCommandReader().getCommands());
        EventsRegister.register(coreAPI.getEventsFactory());

        IConsoleReader.reloadCompleters();
    }

    @Override
    public void stop() {
        BotMain.getInstance().stop();
    }

    public void print(String message){
        Console.print(Colors.CYAN + "[" + Colors.YELLOW + "DNBotLink" + Colors.CYAN + "] " + Colors.RESET + message);
    }

    public AddonMain(Addon addon){super(addon);}
}

package fr.benjimania74.dnbotlink.addon;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import be.alexandre01.dreamnetwork.api.addons.Addon;
import be.alexandre01.dreamnetwork.api.addons.DreamExtension;
import be.alexandre01.dreamnetwork.api.events.EventsFactory;
import be.alexandre01.dreamnetwork.api.service.IService;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.ConsoleReader;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.addon.bot.BotMain;
import fr.benjimania74.dnbotlink.addon.bot.utils.ScreenReader;
import fr.benjimania74.dnbotlink.addon.commands.Config;
import fr.benjimania74.dnbotlink.addon.listeners.ServiceStartListener;
import fr.benjimania74.dnbotlink.addon.listeners.ServiceStopListener;
import fr.benjimania74.dnbotlink.addon.utils.config.ConfigManager;
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
        coreAPI.getCommandReader().getCommands().addCommands(new Config("config"));

        EventsFactory ef = coreAPI.getEventsFactory();
        ef.registerListener(new ServiceStartListener());
        ef.registerListener(new ServiceStopListener());
        ConsoleReader.reloadCompleter();
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

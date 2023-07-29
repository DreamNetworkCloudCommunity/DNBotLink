package fr.benjimania74.dnbotlink.addon.listeners;

import be.alexandre01.dreamnetwork.api.events.EventCatcher;
import be.alexandre01.dreamnetwork.api.events.Listener;
import be.alexandre01.dreamnetwork.api.events.list.screens.CoreScreenCreateEvent;
import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.utils.ScreenReader;

import java.util.HashMap;
import java.util.List;

public class ServiceStartListener implements Listener {
    @EventCatcher
    public void onStart(CoreScreenCreateEvent event){
        IService service = event.getScreen().getService();
        AddonMain main = AddonMain.getInstance();
        String name = service.getFullName().substring(0, service.getFullName().length()-2);
        HashMap<String, List<String>> consoleLinks = main.getConfigManager().getLinkConfig().getConsoleLinks();
        if(!consoleLinks.containsKey(name)){return;}
        main.getServiceScreenReaders().put(service, new ScreenReader(service, consoleLinks.get(name)));
    }
}
